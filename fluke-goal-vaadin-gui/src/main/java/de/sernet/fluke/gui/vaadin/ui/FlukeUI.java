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

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.gui.vaadin.ui.components.LoginForm;
import de.sernet.fluke.gui.vaadin.ui.components.RegisterForm;
import de.sernet.fluke.gui.vaadin.ui.views.*;


@Title("Fluke")
@SpringUI
@Theme("valo")
// @PreserveOnRefresh
public class FlukeUI extends UI {

    private static final long serialVersionUID = 1L;

    @Autowired
    private AccountRestClient accountService;

    @Autowired
    private GameRestClient gameRestClient;

    @Autowired
    private GameResultRestClient gameResultRestClient;

    @Autowired
    private PlayerRestClient playerRestClient;

    @Override
    protected void init(VaadinRequest request) {

        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth("400px");

        tabSheet.addComponent(new LoginForm(this::createUI));
        tabSheet.addComponent(new RegisterForm(getAccountService()));


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

    public AccountRestClient getAccountService() {
        return accountService;
    }

    public void createUI() {

        TabSheet tabSheet = new TabSheet();

        tabSheet.addComponent(new ManagePlayersTab());
        tabSheet.addComponent(new CreateMatchTab());

        tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                Component component = event.getComponent();
                    TabSheet tabSheet = (TabSheet) component;
                component = tabSheet.getSelectedTab();
                if (component instanceof IFlukeUITab) {
                    IFlukeUITab currentTab = (IFlukeUITab) component;
                    currentTab.doOnEnter();
                }
            }

        });
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

    /**
     * @return the gameRestClient
     */
    public GameRestClient getGameRestClient() {
        return gameRestClient;
    }

    /**
     * @return the gameResultRestClient
     */
    public GameResultRestClient getGameResultRestClient() {
        return gameResultRestClient;
    }

    /**
     * @return the playerRestClient
     */
    public PlayerRestClient getPlayerRestClient() {
        return playerRestClient;
    }
}
