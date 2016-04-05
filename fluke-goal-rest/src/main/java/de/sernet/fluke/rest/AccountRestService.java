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

package de.sernet.fluke.rest;

import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;
import de.sernet.fluke.interfaces.ITeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
@RestController
@RequestMapping("/service/account")
public class AccountRestService {
    
    @Autowired
    IAccountService accountService;
    
    public ResponseEntity<IAccount> save(IAccount account){
        IAccount savedAccount = accountService.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.rest.IPlayerService#findOne(java.lang.Long)
     */
    @RequestMapping(path = "/{teamId}", method = RequestMethod.GET)
    public ResponseEntity<IAccount> findOne(@PathVariable Long accountId) {
        IAccount account = accountService.findOne(accountId);
        HttpStatus status = (account != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(account, status);
    }
}
