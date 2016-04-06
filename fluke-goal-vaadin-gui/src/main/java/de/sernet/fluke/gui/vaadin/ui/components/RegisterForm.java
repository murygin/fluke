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

import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import de.sernet.fluke.client.rest.AccountBuilder;
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class RegisterForm extends FormLayout {

    private final Logger LOG = LoggerFactory.getLogger(RegisterForm.class);

    private final TextField login = new TextField("login name");

    private final TextField getFirstName = new TextField("first name");

    private final TextField getLastName = new TextField("last name");

    private final PasswordField password = new PasswordField("password");

    private final PasswordField retypedPassword = new PasswordField("retype password");

    private final TextField email = new TextField("email");

    private final Button register = new Button("register", this::register);

    private final IAccountService accountService;

    public RegisterForm(IAccountService accountService) {

        this.accountService = accountService;

        setCaption("register");

        addComponent(login);
        addComponent(getFirstName);
        addComponent(getLastName);
        addComponent(password);
        addComponent(retypedPassword);
        addComponent(email);
        addComponent(register);

    }


    public void register(Button.ClickEvent event) {
        IAccount account = new AccountBuilder()
                .setLogin(login.getValue())
                .setPassword(password.getValue())
                .setEmail(email.getValue())
                .createAccount();
        try {
            accountService.createAccount(account);
            Notification.show("Successull registration!",
                    "Please login with your credentials",
                    Notification.Type.TRAY_NOTIFICATION);
            redirectLogout();
        } catch (Exception ex) {
            LOG.error("registering failed: {}", ex.getLocalizedMessage(), ex);
            Notification.show("register failed", ex.getLocalizedMessage(),
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    private void redirectLogout() throws InterruptedException {
        Page.getCurrent().setLocation(
                VaadinServlet
                .getCurrent()
                .getServletContext()
                .getContextPath() + "");
    }
}
