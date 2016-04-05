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

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import de.sernet.fluke.gui.vaadin.ui.VaadinUI;
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class LoginForm extends FormLayout {

    private final TextField username = new TextField("Username");

    private final PasswordField password = new PasswordField("Password");

    private final Label invalidPassword = new Label("Invalid username or password");

    private final IAccountService accountService;

    private final Runnable callback;

    private final Button loginBtn;

    public LoginForm(IAccountService accountService, Runnable callback) {

        loginBtn = new Button("Login", (event) -> {
            try {

                IAccount account = accountService.findByLogin(username.getValue());

                if (account != null
                        && account.getPassword().equals(password.getValue())) {

                    VaadinSession session = getUI().getSession();
                    session.setAttribute(IAccount.class, account);
                    callback.run();
                } else {
                    invalidPassword.setVisible(true);
                }

            } catch (Exception e) {
                username.setValue("");
                password.setValue("");
                invalidPassword.setVisible(true);

                System.out.println(VaadinUI.printStackTrace(e));
                Notification.show("Error", VaadinUI.printStackTrace(e),
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
        this.accountService = accountService;
        this.callback = callback;
    }
}
