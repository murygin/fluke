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
package de.sernet.fluke.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IAccountService;
import de.sernet.fluke.model.Account;
import de.sernet.fluke.persistence.AccountRepository;
import de.sernet.fluke.security.PasswordEncoderFactory;

/**
 *
 * @author Daniel Murygin
 */
@Service
public class AccountService implements IAccountService {

    private final Logger logger = LoggerFactory.getLogger(AccountService.class.getName());

    @Autowired
    AccountRepository accountRepository;
    
    private final PasswordEncoder encoder;

    public AccountService() {
        this.encoder = PasswordEncoderFactory.getInstance();
    }
    
    @Override
    public Account createAccount(Account rawAccount) {
        Account securedAccount = encodePassword(rawAccount);
        return save(securedAccount);
    }
    
    @Override
    public Account save(Account account) {
        return accountRepository.save((Account)account);
    }
    
    @Override
    public void delete(Account account) {
        accountRepository.delete((Account)account);
    }

    @Override
    public Account findOne(Long accountId) {
        return accountRepository.findOne(accountId);
    }

    @Override
    public Account findByLogin(String login) {
        return accountRepository.findByLogin(login);     
    }
    
     private Account encodePassword(Account account) {
        try {      
            Account securedAccount = cloneAccount(account);
            securedAccount.setPassword(encoder.encode(account.getPassword()));
            return securedAccount;
        } catch (Exception e) {
           logger.error("Error while creating password hash", e);
           throw new RuntimeException("Error while creating account.");
        }
    }

    private Account cloneAccount(Account account) {
        Account clone = new Account(account.getLogin(), null, account.getEmail());
        clone.setFirstName(account.getFirstName());
        clone.setLastName(account.getLastName());
        return clone;
    }
}
