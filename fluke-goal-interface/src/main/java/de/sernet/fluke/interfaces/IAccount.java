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
 * Represents an user account for fluke.
 * 
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public interface IAccount {
    
    String getEmail();

    String getFirstName();

    long getId();

    String getLastName();

    String getLogin();

    String getPassword();

    void setEmail(String email);

    void setFirstName(String firstName);

    void setId(long id);

    void setLastName(String lastName);

    void setLogin(String login);

    void setPassword(String password);
}
