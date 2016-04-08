package de.sernet.fluke.security.test;

import java.util.UUID;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;
import de.sernet.fluke.persistence.Account;
import de.sernet.fluke.security.PasswordEncoderFactory;
import de.sernet.fluke.service.ServiceApplication;

/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplication.class)
public class AccountServiceTest {

    @Autowired
    IAccountService accountService;
    
    private final PasswordEncoder encoder;

    public AccountServiceTest() {
        this.encoder = PasswordEncoderFactory.getInstance();
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createAccount() {
        final String login = UUID.randomUUID().toString().substring(0,8);
        final String password = UUID.randomUUID().toString().substring(0,10);
        final String email = login + "@sernet.de";
        IAccount rawAccount = new Account(login, password, email);
        accountService.createAccount(rawAccount);

        IAccount foundAccount = accountService.findByLogin(login);
        Assert.assertNotNull("Account with login: " + login + " not found.", foundAccount);
        Assert.assertEquals(login, foundAccount.getLogin());
        Assert.assertEquals(email, foundAccount.getEmail());      
        Assert.assertTrue("Password does nmot match", encoder.matches(password, foundAccount.getPassword()));
        
        accountService.delete(foundAccount);
        foundAccount = accountService.findByLogin(login);
        Assert.assertNull("Account with login: " + login + " found after deletion.", foundAccount);
    }
}
