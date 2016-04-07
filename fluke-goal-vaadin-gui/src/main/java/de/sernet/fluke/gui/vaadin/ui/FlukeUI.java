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

import java.util.stream.Stream;

import com.vaadin.annotations.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import de.sernet.fluke.gui.vaadin.ui.components.LoginForm;
import de.sernet.fluke.gui.vaadin.ui.components.RegisterForm;
import de.sernet.fluke.gui.vaadin.ui.views.*;
import de.sernet.fluke.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

@Title("Fluke")
@SpringUI
@Theme("fluke")
@PreserveOnRefresh
public class FlukeUI extends UI {

    private static final long serialVersionUID = 1L;
    private FlukeMenuBar menu;
    private Navigator navigator = null;
    private VerticalLayout mainLayout;

    @Autowired
    IAccountService accountService;

    @Override
    protected void init(VaadinRequest request) {

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("400px");

        tabSheet.addComponent(new LoginForm(accountService, () -> {
            createUI();
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

    public void createUI() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);
        Panel viewDisplay = new Panel();
        viewDisplay.setSizeFull();
        mainLayout.addComponent(viewDisplay);
        mainLayout.setExpandRatio(viewDisplay, 1.0f);
        navigator = new Navigator(this, viewDisplay);

        menu = new FlukeMenuBar(navigator);
        mainLayout.addComponentAsFirst(menu);
        navigator.addViewChangeListener(menu);

        addView(new ManagePlayersView());
        addView(new CreateMatchView());
        navigator.navigateTo(ManagePlayersView.TYPE_ID);
    }

    private void addView(AbstractPlayerView newView) {

        navigator.addView(newView.getTypeID(), newView);
        menu.addView(newView.getTypeID(), newView.getLabel(), null);
    }

}
