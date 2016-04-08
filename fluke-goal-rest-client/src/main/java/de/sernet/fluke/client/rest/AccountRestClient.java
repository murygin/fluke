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

import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class AccountRestClient extends AbstractSecureRestClient implements IAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountRestClient.class);
    public static final String PATH_DEFAULT = "service/account";

    private String path;
    
    private RestOperations unsecureRestHandler;

    public AccountRestClient(){
       path = PATH_DEFAULT;
    }

    public AccountRestClient(String username, String password) {
        this(username, password, PATH_DEFAULT);
    }

    public AccountRestClient(String username, String password, String path) {
        super(username, password);
        this.path = path;
    }

    @Override
    public IAccount createAccount(IAccount rawAccount) {
        HttpEntity<IAccount> request = new HttpEntity<>(rawAccount);
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append("create");
        String url = sb.toString();
        ResponseEntity<? extends IAccount> responseEntity = getUnsecureRestHandler().postForEntity(url,
                request,
                rawAccount.getClass());
        return responseEntity.getBody();
    }

    @Override
    public IAccount save(IAccount account) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(IAccount account) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IAccount findOne(Long accountId) {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(accountId);
        String url = sb.toString();
        return getRestHandler().getForObject(url, Account.class);
    }

    @Override
    public IAccount findByLogin(String login) {
        StringBuilder sb = new StringBuilder(getBaseUrl());
        sb.append(login);
        String url = sb.toString();
        return getRestHandler().getForObject(url, Account.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see de.sernet.fluke.client.rest.AbstractSecureRestClient#getPath()
     */
    @Override
    public String getPath() {
        return path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * de.sernet.fluke.client.rest.AbstractSecureRestClient#setPath(java.lang.String)
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }
    
    public RestOperations getUnsecureRestHandler() {
        if(unsecureRestHandler==null) {
            unsecureRestHandler = new RestTemplate();
        }
        return unsecureRestHandler;
    }

}
