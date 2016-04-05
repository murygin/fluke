/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.client.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.interfaces.IPlayerService.IPlayerService;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@Service
public class PlayerRestClient extends AbstractRestClient implements IPlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerRestClient.class);
    
    public static final String SERVER_URL_DEFAULT = "http://localhost:8080/";
    public static final String PATH_DEFAULT = "service/player";
    
    private String path;
    
    public PlayerRestClient() {
        this(SERVER_URL_DEFAULT, PATH_DEFAULT);
    }

    public PlayerRestClient(String serverUrl, String path) {
        super();
        setServerUrl(serverUrl);
        setPath(path);
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Object)
     */
    @Override
    public IPlayer save(IPlayer entity) {
        HttpEntity<IPlayer> request = new HttpEntity<>(entity);
        String url = getBaseUrl();
        if (LOG.isInfoEnabled()) {
            LOG.info("Save, URL: " + url);
        }
        ResponseEntity<? extends IPlayer> responseEntity = getRestHandler().postForEntity(url, request, entity.getClass());
        return responseEntity.getBody();
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    public IPlayer findOne(Long id) {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(id);
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("findOne, URL: " + url);
        }
        return getRestHandler().getForObject(url, Player.class);
    }
    
    @Override
    public boolean delete(long playerId) {
        StringBuilder stringBuilder = new StringBuilder(getBaseUrl());
        stringBuilder.append(playerId);
        String uri = stringBuilder.toString();
        getRestHandler().delete(getBaseUrl());
        return true;
    }

    @Override
    public Iterable<IPlayer> findAll() {
        String uri = getBaseUrl();
        Iterable players = getRestHandler().getForObject(uri, Iterable.class);
        ResponseEntity<Iterable> responseEntity = getRestHandler().getForEntity(uri,
                Iterable.class);
        return responseEntity.getBody();
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