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

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.interfaces.ITeamService;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class TeamRestClient extends AbstractSecureRestClient implements ITeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamRestClient.class);

    public static final String PATH_DEFAULT = "service/team";

    private String path;

    public TeamRestClient() {
        path = PATH_DEFAULT;
    }
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#save(de.sernet.fluke.interfaces.ITeam)
     */
    @Override
    public ITeam save(ITeam team) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#findOne(long)
     */
    @Override
    public ITeam findOne(long teamId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#findById(long)
     */
    @Override
    public ITeam findById(long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#findByPlayers(de.sernet.fluke.interfaces.IPlayer, de.sernet.fluke.interfaces.IPlayer)
     */
    @Override
    public ITeam findByPlayers(IPlayer defensivePlayer, IPlayer offensivePlayer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#findOrCreate(de.sernet.fluke.interfaces.IPlayer, de.sernet.fluke.interfaces.IPlayer)
     */
    @Override
    public ITeam findOrCreate(IPlayer defensivePlayer, IPlayer offensivePlayer) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.ITeamService#findAll()
     */
    @Override
    public ITeam[] findAll() {
        String uri = getBaseUrl();
        ResponseEntity<Team[]> responseEntity = getRestHandler().getForEntity(uri,
                Team[].class);
        return responseEntity.getBody();
    }


    /* (non-Javadoc)
     * @see de.sernet.fluke.client.rest.AbstractSecureRestClient#getPath()
     */
    @Override
    public String getPath() {
        return path;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.client.rest.AbstractSecureRestClient#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

}
