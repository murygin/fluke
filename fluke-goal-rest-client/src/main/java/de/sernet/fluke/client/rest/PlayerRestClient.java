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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@Service
public class PlayerRestClient implements IPlayerService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerRestClient.class);
    
    public static final String SERVER_URL_DEFAULT = "http://localhost:8080/";
    public static final String PATH_DEFAULT = "element";
    
    private String serverUrl = SERVER_URL_DEFAULT;
    private String path = PATH_DEFAULT;
    
    RestTemplate restTemplate = new RestTemplate();

    
    public PlayerRestClient() {
        super();
    }

    public PlayerRestClient(String serverUrl, String path) {
        super();
        this.serverUrl = serverUrl;
        this.path = path;
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
        ResponseEntity<? extends IPlayer> responseEntity = restTemplate.postForEntity(url, request, entity.getClass());
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
        return restTemplate.getForObject(url, Player.class);
    }
    
    private String getBaseUrl() {
        StringBuilder sb = new StringBuilder(getServerUrl());
        if(!getServerUrl().endsWith("/") &&  !getPath().startsWith("/")) {
            sb.append("/");
        }
        sb.append(getPath());
        if(!getPath().endsWith("/")) {
            sb.append("/");
        }
        return sb.toString();
    }
    
    private String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
