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

import com.vaadin.ui.*;

import de.sernet.fluke.client.rest.AccountBuilder;
import de.sernet.fluke.client.rest.AccountRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;


/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class RegisterForm extends FormLayout {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(RegisterForm.class);

    private final TextField login = new TextField("login name");

    private final TextField getFirstName = new TextField("first name");

    private final TextField getLastName = new TextField("last name");

    private final PasswordField password = new PasswordField("password");

    private final PasswordField retypedPassword = new PasswordField("retype password");

    private final TextField email = new TextField("email");

    private final Button register = new Button("register", this::register);

    private final IAccountService accountService;

    public RegisterForm(IAccountService accountService) {

        this.accountService = new AccountRestClient(FlukeUI.USER_NAME, FlukeUI.PASSWORD);

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
        login.setValue("");
        getFirstName.setValue("");
        getLastName.setValue("");
        password.setValue("");
        retypedPassword.setValue("");
        email.setValue("");
        // Page.getCurrent().setLocation(
        // VaadinServlet
        // .getCurrent()
        // .getServletContext()
        // .getContextPath() + "");
    }
}
