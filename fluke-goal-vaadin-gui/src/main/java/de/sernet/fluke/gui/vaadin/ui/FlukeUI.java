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

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

import de.sernet.fluke.gui.vaadin.ui.views.*;

@Title("Fluke")
@SpringUI
@Theme("fluke")
public class FlukeUI extends UI {

    private static final long serialVersionUID = 1L;
    private FlukeMenuBar menu;
    private Navigator navigator = null;
    private VerticalLayout mainLayout;

    @Override
    protected void init(VaadinRequest request) {

        createMainLayout();

        menu = new FlukeMenuBar(getNavigator());
        mainLayout.addComponentAsFirst(menu);
        getNavigator().addViewChangeListener(menu);

        addView(new ManagePlayersView());
        addView(new CreateMatchView());
        getNavigator().navigateTo(ManagePlayersView.TYPE_ID);
    }

    public Navigator getNavigator() {

        if (navigator == null) {
            Panel viewDisplay = new Panel();
            viewDisplay.setSizeFull();
            mainLayout.addComponent(viewDisplay);
            mainLayout.setExpandRatio(viewDisplay, 1.0f);
            navigator = new Navigator(this, viewDisplay);
        }

        return navigator;
    }

    public void createMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        setContent(mainLayout);
    }

    private void addView(AbstractPlayerView newView) {

        getNavigator().addView(newView.getTypeID(), newView);
        menu.addView(newView.getTypeID(), newView.getLabel(), null);
    }

}
