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

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.rest.PlayerSelection;

/**
 *
 * @author Daniel Murygin
 */
public class GameRestClient extends AbstractRestClient implements IGameService {

    private static final Logger LOG = LoggerFactory.getLogger(GameRestClient.class);

    public static final String PATH_DEFAULT = "service/game";

    private String path;

    public GameRestClient() {
        path = PATH_DEFAULT;
    }

    public GameRestClient(String username, String password) {
        this(username, password, SERVER_URL_DEFAULT, PATH_DEFAULT);
    }

    public GameRestClient(String username, String password, String serverUrl, String path) {
        super(username, password);
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

    @Override
    public IGame create(IPlayer redOffensive, IPlayer redDefensive, IPlayer blueOffensive, IPlayer blueDefensive) {
        return create(redOffensive.getId(), redDefensive.getId(), blueOffensive.getId(),
                blueDefensive.getId());
    }

    // unsupported operations
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

    @Override
    public Game[] findAllUntrackedGames() {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append("untrackedGames");
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("findById, URL: " + url);
        }
        Game[] games = getRestHandler().getForObject(url, Game[].class);
        ResponseEntity<Game[]> responseEntity = getRestHandler().getForEntity(url,
                Game[].class);
        return responseEntity.getBody();
    }

}
