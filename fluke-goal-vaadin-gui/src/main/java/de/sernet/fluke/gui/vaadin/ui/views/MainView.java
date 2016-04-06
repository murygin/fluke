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
package de.sernet.fluke.gui.vaadin.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public class MainView extends VerticalLayout implements View {

    public final static String VIEW_NAME = "Start view";

    private final Button logout;

    public MainView() {

        this.logout = new Button("Logout ", this::logout);

        setSizeFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthUndefined();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponent(logout);

        horizontalLayout.setComponentAlignment(logout, Alignment.TOP_RIGHT);
        addComponent(logout);
    }

    private void logout(Button.ClickEvent event) {
        VaadinSession.getCurrent().close();
        Page.getCurrent().setLocation(
                VaadinServlet
                .getCurrent()
                .getServletContext()
                .getContextPath() + "");
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
