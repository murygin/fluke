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

package de.sernet.fluke.gui.vaadin.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.sernet.fluke.client.rest.AccountRestClient;
import de.sernet.fluke.gui.vaadin.ui.components.LoginForm;
import de.sernet.fluke.gui.vaadin.ui.components.RegisterForm;
import de.sernet.fluke.gui.vaadin.ui.views.MainView;
import de.sernet.fluke.interfaces.IAccountService;
import java.util.stream.Stream;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
@SpringUI
@Theme("valo")
@PreserveOnRefresh
public class VaadinUI extends UI {


    IAccountService accountService = new AccountRestClient();

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("400px");

        tabSheet.addComponent(new LoginForm(accountService, () -> {
            setContent(new MainView());
        }));

        tabSheet.addComponent(new RegisterForm(accountService));

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthUndefined();
        horizontalLayout.setSpacing(true);

        horizontalLayout.addComponent(tabSheet);

        layout.addComponent(horizontalLayout);
        layout.setComponentAlignment(horizontalLayout, Alignment.TOP_CENTER);

        setContent(layout);
    }

    public static String printStackTrace(Exception ex) {
        return Stream.of(ex.getStackTrace()).collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append)
                .toString();
    }

    public IAccountService getAccountService() {
        return accountService;
    }
}