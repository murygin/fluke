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
package de.sernet.fluke.gui.vaadin.ui.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import de.sernet.fluke.client.rest.AccountRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class LoginForm extends FormLayout {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(LoginForm.class);

    private final TextField username = new TextField("Username");

    private final PasswordField password = new PasswordField("Password");

    private final Label invalidPassword = new Label("Invalid username or password");

    private IAccountService realAccountService;

    // private final Runnable callback;

    private final Button loginBtn;

    public LoginForm(IAccountService accountService, Runnable callback) {

        loginBtn = new Button("Login", event -> {

            try {
                this.realAccountService = new AccountRestClient(username.getValue(),
                        password.getValue());
                    IAccount account = realAccountService.findByLogin(username.getValue());

                    VaadinSession session = getUI().getSession();
                    session.setAttribute(IAccount.class, account);
                callback.run();
                } catch (HttpClientErrorException e) {
                    if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        invalidPassword.setVisible(true);
                        Note.error("Invalid username or password");
                    } else {
                        throw e;
                    }
                } catch (Exception e){
                        username.setValue("");
                         password.setValue("");
                         invalidPassword.setVisible(true);

                         LOG.error("authentication failed: {}", e.getLocalizedMessage(),
                         e);
                         Notification.show("Error", FlukeUI.printStackTrace(e),
                         com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
                    }
        });

        setCaption("login");
        setSpacing(true);
        username.focus();
        addComponent(username);
        addComponent(password);
        addComponent(loginBtn);
        addComponent(invalidPassword);
        invalidPassword.setVisible(false);
        this.realAccountService = accountService;
        // this.callback = callback;
    }
}
