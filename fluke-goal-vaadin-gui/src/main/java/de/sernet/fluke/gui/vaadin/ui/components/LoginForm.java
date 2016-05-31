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

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;

import de.sernet.fluke.client.rest.AccountRestClient;
import de.sernet.fluke.client.rest.GameRestClient;
import de.sernet.fluke.client.rest.GameResultRestClient;
import de.sernet.fluke.client.rest.PlayerRestClient;
import de.sernet.fluke.client.rest.TeamRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
@Service
@VaadinSessionScope
public class LoginForm extends FormLayout {

    @Autowired
    @Qualifier("vaadinGameRestClient")
    private GameRestClient gameRestClient;

    @Autowired
    private GameResultRestClient gameResultRestClient;

    @Autowired
    private PlayerRestClient playerRestClient;

    @Autowired
    private TeamRestClient teamRestClient;

    @Autowired
    private AccountRestClient accountRestClient;

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(LoginForm.class);

    private final TextField username = new TextField("Username");

    private final PasswordField password = new PasswordField("Password");

    private final Label invalidPassword = new Label("Invalid username or password");

    private Runnable callback;

    private final Button loginBtn;

    public LoginForm() {

//        setWidth("1200px");

        setCaption("Login");
        setSpacing(true);
        username.focus();
        addComponent(username);
        addComponent(password);

        loginBtn = new Button("Login", this::login);
        addComponent(loginBtn);

        addComponent(invalidPassword);

        password.addShortcutListener(
                new ShortcutListener("Shortcut Name", ShortcutAction.KeyCode.ENTER, null) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void handleAction(Object sender, Object target) {

                        login(null);
                    }
                });

        invalidPassword.setVisible(false);
    }

    private void login(Event event) {

        try {
            
            initRestClientsWithCredentials();
            
            Account account = accountRestClient.findByLogin(username.getValue());

            VaadinSession session = getUI().getSession();
            session.setAttribute(Account.class, account);
            getCallback().run();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                invalidPassword.setVisible(true);
                Note.warning("Invalid username or password");
            } else {
                throw e;
            }
        } catch (Exception e) {
            username.setValue("");
            password.setValue("");
            invalidPassword.setVisible(true);

            LOG.error("authentication failed: {}", e.getLocalizedMessage(),
                    e);
            Notification.show("Error", FlukeUI.printStackTrace(e),
                    com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
        }
    }

    private void initRestClientsWithCredentials() {
        
        accountRestClient.initRestOperations(username.getValue(),
                password.getValue());
        
        playerRestClient.initRestOperations(username.getValue(),
                password.getValue());
        
        gameRestClient.initRestOperations(username.getValue(),
                password.getValue());
        
        gameResultRestClient.initRestOperations(username.getValue(),
                password.getValue());
        
        teamRestClient.initRestOperations(username.getValue(),
                password.getValue());
        
        }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
