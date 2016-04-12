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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.themes.ValoTheme;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.gui.vaadin.ui.components.LoginForm;
import de.sernet.fluke.gui.vaadin.ui.components.RegisterForm;
import de.sernet.fluke.gui.vaadin.ui.tabs.*;

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

    @Autowired
    private TeamRestClient teamRestClient;

    private static final String ROOT_WIDTH = "900px";

    @Override
    protected void init(VaadinRequest request) {

        loginToApplication = new TabSheet();
        loginToApplication.setWidth(ROOT_WIDTH);

        loginToApplication.addComponent(new LoginForm(this::createUI));
        loginToApplication.addComponent(new RegisterForm(getAccountService()));

        root = new VerticalLayout();
//        root.setSizeFull();
        root.setMargin(true);

        Component header = buildHeader();
        root.addComponent(header);

        mainContent = new HorizontalLayout();
        mainContent.setWidth(ROOT_WIDTH);
        mainContent.setHeightUndefined();
        mainContent.setSpacing(true);

        mainContent.addComponent(loginToApplication);

        root.addComponent(mainContent);
        root.setComponentAlignment(mainContent, Alignment.TOP_CENTER);
        root.setComponentAlignment(header, Alignment.TOP_CENTER);

        setContent(root);
    }
    private HorizontalLayout mainContent;
    private TabSheet loginToApplication;
    private VerticalLayout root;

    private Component buildHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidth(ROOT_WIDTH);
        header.setHeight("60px");

        Label title = new Label("fluke");
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        title.setWidth("100%");

        header.addComponent(title);

        Component logout = buildLogoutButton();

        header.addComponent(logout);
        header.setComponentAlignment(logout, Alignment.TOP_RIGHT);
        return header;
    }

    private Component buildLogoutButton() {
        Button logout = new Button();
        logout.setIcon(FontAwesome.SIGN_OUT);
        logout.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
        logout.setDescription("Logout");
        logout.addClickListener(event -> {
            VaadinSession.getCurrent().close();
            Page.getCurrent().reload();
        });

        return logout;
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

        TabSheet applicationMenu = new TabSheet();

        applicationMenu.addComponent(new ManagePlayersTab());
        applicationMenu.addComponent(new CreateMatchTab());
        applicationMenu.addComponent(new TrackMatchResultsTab(gameRestClient, gameResultRestClient));
        applicationMenu.addComponent(new StatisticsTab());

        applicationMenu.setSizeFull();
        applicationMenu.addSelectedTabChangeListener(new SelectedTabChangeListener() {

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


        mainContent.removeComponent(loginToApplication);
        mainContent.addComponent(applicationMenu);
    }

    /**
     * @return the gameRestClient
     */
    public GameRestClient getGameRestClient() {
        return gameRestClient;
    }

    public TeamRestClient getTeamRestClient() {
        return teamRestClient;
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
