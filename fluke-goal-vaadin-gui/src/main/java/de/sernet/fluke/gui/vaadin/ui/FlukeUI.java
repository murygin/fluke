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
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.themes.ValoTheme;

import de.sernet.fluke.gui.vaadin.ui.components.LoginForm;
import de.sernet.fluke.gui.vaadin.ui.components.RegisterForm;
import de.sernet.fluke.gui.vaadin.ui.tabs.*;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;

@Title("Fluke")
@SpringUI
@Theme("valo")
@PreserveOnRefresh
public class FlukeUI extends UI {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ObjectFactory<LoginForm> loginFormObjectFactory;

    @Autowired
    private ObjectFactory<RegisterForm> registerFormObjectFactory;

    @Autowired
    private ObjectFactory<ManagePlayersTab> managePlayersTabFactory;

    @Autowired
    private ObjectFactory<CreateMatchTab> createMatchTabObjectFactory;

    @Autowired
    private ObjectFactory<TrackMatchResultsTab> trackResultsTabObjectFactory;

    @Autowired
    private ObjectFactory<StatisticsTab> statisticsTabObjectFactory;

    @Value("${git-sha1}")
    private String gitSha1;

    private static final String ROOT_WIDTH = "900px";
    private VerticalLayout mainContent;
    private TabSheet loginToApplication;
    private VerticalLayout root;

    @Override
    protected void init(VaadinRequest request) {

        loginToApplication = new TabSheet();
        loginToApplication.setWidth(ROOT_WIDTH);

        LoginForm loginForm = loginFormObjectFactory.getObject();
        loginForm.setCallback(this::createUI);
        loginToApplication.addComponent(loginForm);
        loginToApplication.addComponent(registerFormObjectFactory.getObject());

        root = new VerticalLayout();
//        root.setSizeFull();
        root.setMargin(true);

        Component header = buildHeader();
        root.addComponent(header);

        mainContent = new VerticalLayout();
        mainContent.setWidth(ROOT_WIDTH);
        mainContent.setHeightUndefined();
        mainContent.setSpacing(true);

        mainContent.addComponent(loginToApplication);

        HorizontalLayout footerLayout = new HorizontalLayout();
        footerLayout.setWidth(ROOT_WIDTH);
        footerLayout.setHeight(50, Unit.PIXELS);

        Label buildNumber = new Label("Git sha1: " + gitSha1.substring(0, 7));
        buildNumber.setSizeUndefined();
        buildNumber.setStyleName(ValoTheme.LABEL_TINY);
        footerLayout.addComponent(buildNumber);
        footerLayout.setComponentAlignment(buildNumber, Alignment.BOTTOM_RIGHT);
        footerLayout.setSpacing(true);
        footerLayout.setWidth(ROOT_WIDTH);

        root.addComponent(mainContent);
        root.addComponent(footerLayout);
        root.setComponentAlignment(header, Alignment.TOP_CENTER);
        root.setComponentAlignment(mainContent, Alignment.TOP_CENTER);
        root.addComponent(footerLayout);
        root.setComponentAlignment(footerLayout, Alignment.BOTTOM_CENTER);

        setContent(root);
    }

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
                (builder, s) -> {builder.append(s).append("\n");},
                StringBuilder::append)
                .toString();
    }

    public void createUI() {

        TabSheet applicationMenu = new TabSheet();

        ManagePlayersTab managePlayersTab = managePlayersTabFactory.getObject();
        applicationMenu.addComponent(managePlayersTab);
        managePlayersTab.do OnEnter();

        CreateMatchTab initComponent = createMatchTabObjectFactory.getObject();
        applicationMenu.addComponent(initComponent);

        TrackMatchResultsTab trackResultsTab =
                trackResultsTabObjectFactory.getObject();
        applicationMenu.addComponent(trackResultsTab);

        StatisticsTab statisticsTab = statisticsTabObjectFactory.getObject();
        applicationMenu.addComponent(statisticsTab);

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
}
