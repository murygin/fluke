/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.gui.vaadin.ui;

import java.util.ArrayList;

import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.MenuBar.MenuItem;

import de.sernet.fluke.gui.vaadin.backend.PlayerService;
import de.sernet.fluke.interfaces.IPlayer;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@Title("Fluke")
//@SpringUI
public class VaadinUI extends UI {

    private static final long serialVersionUID = 1L;

    private Grid grid = new Grid();

    PlayerService playerService = new PlayerService();


    private HorizontalLayout mainLayout;

    protected Label result = new Label();

    private Label selection = new Label();


    /* (non-Javadoc)
     * @see com.vaadin.ui.UI#init(com.vaadin.server.VaadinRequest)
     */
    @Override
    protected void init(VaadinRequest vaadinRequest) {






        VerticalLayout layout = new VerticalLayout();
        MenuBar barmenu = createMenu();

        layout.addComponent(barmenu);
        layout.setSizeFull();

        mainLayout = new HorizontalLayout();
        mainLayout.setWidthUndefined();
        mainLayout.setSpacing(true);

        layout.addComponent(mainLayout);
        layout.setComponentAlignment(mainLayout,
                Alignment.MIDDLE_CENTER);

        final Panel panel = createAddPlayerPanel();
        mainLayout.addComponent(panel);



    }

    private MenuBar createMenu() {
        MenuBar.Command mycommand = new MenuBar.Command() {

            private static final long serialVersionUID = 1L;

            public void menuSelected(MenuItem selectedItem) {
                selection.setValue("Ordered a " +
                        selectedItem.getText() +
                        " from menu.");
            }
        };

        MenuBar barmenu = new MenuBar();
        // A top-level menu item that opens a submenu
        MenuItem players = barmenu.addItem("Players", null, null);

        // Submenu item with a sub-submenu
        MenuItem hots = players.addItem("Hot", null, null);
        hots.addItem("Tea",
                new ThemeResource("icons/tea-16px.png"), mycommand);
        hots.addItem("Coffee",
                new ThemeResource("icons/coffee-16px.png"), mycommand);

        // Another submenu item with a sub-submenu
        MenuItem colds = players.addItem("Cold", null, null);
        colds.addItem("Milk", null, mycommand);
        colds.addItem("Weissbier", null, mycommand);

        // Another top-level item
        MenuItem snacks = barmenu.addItem("Snacks", null, null);
        snacks.addItem("Weisswurst", null, mycommand);
        snacks.addItem("Bratwurst", null, mycommand);
        snacks.addItem("Currywurst", null, mycommand);

        // Yet another top-level item
        MenuItem servs = barmenu.addItem("Services", null, null);
        servs.addItem("Car Service", null, mycommand);
        return barmenu;
    }

    private Panel createAddPlayerPanel() {
        final Panel panel = new Panel("new Player");
        panel.setWidthUndefined();
        final FormLayout form = new FormLayout();
        form.setMargin(true);
        TextField vorname = new TextField("Vorname");
        form.addComponent(vorname);
        TextField nachname = new TextField("Nachname");
        form.addComponent(nachname);

        Button submit = new Button("submit");
        form.addComponent(submit);

        submit.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 3311159330860405923L;

            @Override
            public void buttonClick(ClickEvent event) {
                playerService.addPlayer(vorname.getValue(), nachname.getValue());
                updateList();

            }
        });
        panel.setContent(form);
        return panel;
    }

    private void updateList(){

        grid.setContainerDataSource(
                new BeanItemContainer<>(IPlayer.class, playerService.getPlayers()));
    }
}
