/*
 * Copyright 2016 SerNet Service Network GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.sernet.fluke.client.rest;

import static de.sernet.fluke.client.rest.AbstractRestClient.SERVER_URL_DEFAULT;
import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.rest.PlayerSelection;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Daniel Murygin
 */
@Service
public class GameRestClient extends AbstractRestClient  implements IGameService {

    private static final Logger LOG = LoggerFactory.getLogger(GameRestClient.class);
    
    public static final String PATH_DEFAULT = "service/game";
    
    private String path;
    
    public GameRestClient() {
        this(SERVER_URL_DEFAULT, PATH_DEFAULT);
    }

    public GameRestClient(String serverUrl, String path) {
        super();
        setServerUrl(serverUrl);
        setPath(path); 
    }

    @Override
    public Game create(long redOffensiveId, long redDefensiveId, long blueOffensiveId, long blueDefensiveId) {
        PlayerSelection playerSelection = new PlayerSelection(redOffensiveId, redDefensiveId, blueOffensiveId, blueDefensiveId);
        HttpEntity<PlayerSelection> request = new HttpEntity<>(playerSelection);
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append("create");
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("create, URL: " + url);
        }
        ResponseEntity<Game> responseEntity = getRestHandler().postForEntity(url, request, Game.class);
        return responseEntity.getBody();
    }

    @Override
    public IGame save(IGame game) {
        HttpEntity<IGame> request = new HttpEntity<>(game);
        String url = getBaseUrl();
        if (LOG.isInfoEnabled()) {
            LOG.info("Save, URL: " + url);
        }
        ResponseEntity<? extends IGame> responseEntity = getRestHandler().postForEntity(url, request, game.getClass());
        return responseEntity.getBody();
    }

    @Override
    public Game findById(Long gameId) {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(gameId);
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("findById, URL: " + url);
        }
        return getRestHandler().getForObject(url, Game.class);
    }
    
    // unsupported operations
    
    @Override
    public IGame create(IPlayer redOffensive, IPlayer redDefensive, IPlayer blueOffensive, IPlayer blueDefensive) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<IGame> findByDate(LocalDateTime time) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }
    
}
