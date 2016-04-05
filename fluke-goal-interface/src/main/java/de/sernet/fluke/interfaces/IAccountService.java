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
package de.sernet.fluke.interfaces;

/**
 * Service to manage user accounts.
 * 
 * @author Benjamin Weißenfels <bw@sernet.de>
 * @author Daniel Murygin
 */
public interface IAccountService {
    
    /**
     * Creates an user account from raw account data.
     * In a raw account the password is saved in clear text.
     * This method hashes the password, inserts the 
     * account in the data store and returns it after inserting.
     * 
     * @param rawAccount An user account with clear text password
     * @return Account after inserted to database
     */
    IAccount createAccount(IAccount rawAccount);
    
    /**
     * Saves an acount in the data store.
     * This method saves the password as it is passed in the parameter.
     * 
     * @param account An user account
     * @return Account after saving 
     */
    IAccount save(IAccount account);
    
    /**
     * Deletes an user account
     * 
     * @param account An user account
     */
    void delete(IAccount account);

    /**
     * Finds an user account by data store id.
     * 
     * @param accountId The database id of an user account
     * @return A user account or null if no account with id exists
     */
    IAccount findOne(Long accountId);
    
    /**
     * Finds an user account by login name.
     * Login names are unique.
     * 
     * @param login The login of an user account
     * @return A user account or null if no account with login exists
     */
    IAccount findByLogin(String login);
}
