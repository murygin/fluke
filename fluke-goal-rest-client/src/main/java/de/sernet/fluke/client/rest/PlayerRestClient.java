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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mysql.fabric.Response;

import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.PlayerRepository;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@Service
public class PlayerRestClient implements PlayerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerRestClient.class);
    
    public static String SERVER_URL_DEFAULT = "http://localhost:8080/";
    public static String PATH_DEFAULT = "element";
    
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
    public Player save(Player entity) {
        HttpEntity<Player> request = new HttpEntity<>(entity);
        String url = getBaseUrl();
        if (LOG.isInfoEnabled()) {
            LOG.info("Save, URL: " + url);
        }
        ResponseEntity<? extends Player> responseEntity = restTemplate.postForEntity(url, request, entity.getClass());
        return responseEntity.getBody();
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    @Override
    public <S extends Player> Iterable<S> save(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findOne(java.io.Serializable)
     */
    @Override
    public Player findOne(Long id) {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(id);
        String url = sb.toString();
        if (LOG.isInfoEnabled()) {
            LOG.info("findOne, URL: " + url);
        }
        return restTemplate.getForObject(url, Player.class);
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#exists(java.io.Serializable)
     */
    @Override
    public boolean exists(Long id) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    @Override
    public Iterable<Player> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
     */
    @Override
    public Iterable<Player> findAll(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#count()
     */
    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.io.Serializable)
     */
    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Object)
     */
    @Override
    public void delete(Player entity) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#delete(java.lang.Iterable)
     */
    @Override
    public void delete(Iterable<? extends Player> entities) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#deleteAll()
     */
    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.PlayerRepository#findByLastName(java.lang.String)
     */
    @Override
    public List<Player> findByLastName(String name) {
        // TODO Auto-generated method stub
        return null;
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
