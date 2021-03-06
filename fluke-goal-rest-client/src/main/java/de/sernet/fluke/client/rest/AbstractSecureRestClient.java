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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Daniel Murygin
 */
public abstract class AbstractSecureRestClient {

    @Value("${rest-server.url:http://localhost:8080}")
    private String serverUrl;

    private RestOperations restOperations;

    public AbstractSecureRestClient() {
        super();
        restOperations = new RestTemplate();
    }

    private AbstractSecureRestClient(String serverUrl) {
        super();
        this.serverUrl = serverUrl;
        restOperations = new RestTemplate();
    }

    public AbstractSecureRestClient(String username, String password) {
        super();
        restOperations = new RestClient(username, password);
    }

    /**
     * Inits the rest client with user credentials.
     *
     * If the server is secured, this is the only way for clients to talk with
     * the rest backend.
     *
     * @param username The login name of the user.
     * @param password The password of the user.
     *
     */
    public void initRestOperations(String username, String password) {
        restOperations = new RestClient(username, password);
    }

    protected String getBaseUrl() {
        StringBuilder sb = new StringBuilder(getServerUrl());
        if (!getServerUrl().endsWith("/") && !getPath().startsWith("/")) {
            sb.append("/");
        }
        sb.append(getPath());
        if (!getPath().endsWith("/")) {
            sb.append("/");
        }
        return sb.toString();
    }

    protected String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
    
    protected RestOperations getRestHandler() {
        return restOperations;
    }
    
    public void setRestHandler(RestOperations restHandler) {
        this.restOperations = restHandler;
    }

    public abstract String getPath();

    public abstract void setPath(String path);
    
}
