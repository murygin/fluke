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

import java.util.HashMap;
import java.util.Map.Entry;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.MenuBar;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class FlukeMenuBar extends MenuBar implements ViewChangeListener {

    private static final long serialVersionUID = 1L;
    private MenuItem previous = null;
    private MenuItem current = null;
    private Navigator navigator = null;

    /** Navigate to a view by menu selection */
    MenuBar.Command mycommand = new MenuBar.Command() {

        private static final long serialVersionUID = 1L;

        public void menuSelected(MenuItem selectedItem) {
            String viewName = selectItem(selectedItem);
            navigator.navigateTo(viewName);
        }
    };

    HashMap<String, MenuItem> menuItems = new HashMap<>();


    /** A menu bar that both controls and observes navigation */
    public FlukeMenuBar(Navigator navigator) {
        this.navigator = navigator;
    }

    public void addView(String viewName, String caption, Resource icon) {
        menuItems.put(viewName, addItem(caption, icon, mycommand));
    }

    /** Selects a new menu item */
    public String selectItem(MenuItem selectedItem) {
        current = selectedItem;

        // Do reverse lookup for the view ID
        for (Entry<String, MenuItem> entry : menuItems.entrySet())
            if (entry.getValue() == selectedItem)
                return entry.getKey();

        return null;
    }

    @Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return selectView(event.getViewName());
    }

    /** Select a menu item by its view ID **/
    protected boolean selectView(String viewName) {
        // Check that the menu item exists
        if (!menuItems.containsKey(viewName))
            return false;

        if (previous != null)
            previous.setStyleName(null);
        if (current == null)
            current = menuItems.get(viewName);
        current.setStyleName("highlight");
        previous = current;

        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {
        // nothing to do
    }
}
