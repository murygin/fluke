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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class GameResultRestClient extends AbstractRestClient implements IGameResultService {

    private static final Logger LOG = LoggerFactory.getLogger(GameResultRestClient.class);

    public static final String SERVER_URL_DEFAULT = "http://localhost:8080/";
    public static final String PATH_DEFAULT = "service/gameResult";

    private static final String NOT_IMPLEMENTED_MSG = "Method not implemented by this client. Use trackGameResult(GoalsOfAGameCollection goals) instead";

    private String path;

    public GameResultRestClient() {
        path = PATH_DEFAULT;
    }

    public GameResultRestClient(String username, String password) {
        this(username, password, SERVER_URL_DEFAULT, PATH_DEFAULT);
    }

    public GameResultRestClient(String username, String password, String serverUrl, String path) {
        super(username, password);
        setServerUrl(serverUrl);
        setPath(path);
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResultService#trackGameResult(de.sernet.fluke.interfaces.IGame, short, short)
     */
    @Override
    public IGameResult trackGameResult(IGame game, short goalsRedTeam, short goalsBlueTeam) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_MSG);
    }

    @Override
    public IGameResult trackGameResult(GoalsOfAGameCollection goals) {
        HttpEntity<GoalsOfAGameCollection> request = new HttpEntity<>(goals);
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append("trackGameResult");
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("Tracking Result, URL: " + url);
        }
        ResponseEntity<? extends IGameResult> responseEntity = getRestHandler().postForEntity(url, request, GameResult.class);
        return responseEntity.getBody();

    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResultService#save(de.sernet.fluke.interfaces.IGameResult)
     */
    @Override
    public IGameResult save(IGameResult gameResult) {
        HttpEntity<IGameResult> request = new HttpEntity<>(gameResult);
        String url = getBaseUrl();
        if (LOG.isInfoEnabled()) {
            LOG.info("Save, URL: " + url);
        }
        ResponseEntity<? extends IGameResult> responseEntity = getRestHandler().postForEntity(url, request, gameResult.getClass());
        return responseEntity.getBody();
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.client.rest.AbstractRestClient#getPath()
     */
    @Override
    public String getPath() {
        return this.path;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.client.rest.AbstractRestClient#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public IGameResult trackGameResult(long gameId, short goalsRedTeam, short goalsBlueTeam) {
        return trackGameResult(new GoalsOfAGameCollection(gameId, goalsRedTeam, goalsBlueTeam));
    }

    @Override
    public IGameResult trackGameResult(long gameId, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        return trackGameResult(new GoalsOfAGameCollection(gameId, blueOffensiveGoals, blueDefensiveGoals, redOffensiveGoals, redDefensiveGoals));
    }

    @Override
    public IGameResult trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED_MSG);
    }

}
