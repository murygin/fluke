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

import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
abstract public class AbstractRestService {

    public static final String SERVER_URL_DEFAULT = "http://localhost:8080/";
    public static final String PATH_DEFAULT = "element";
    protected String serverUrl = SERVER_URL_DEFAULT;
    protected String path = PATH_DEFAULT;
    RestTemplate restTemplate = new RestTemplate();

    public AbstractRestService() {
        super();
    }

    protected String getServerUrl() {
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

    protected String getBaseUrl() {
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
}
