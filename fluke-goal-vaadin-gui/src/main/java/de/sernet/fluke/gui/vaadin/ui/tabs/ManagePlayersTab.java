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
package de.sernet.fluke.gui.vaadin.ui.tabs;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.gui.vaadin.ui.components.FlukePlayerForm;
import de.sernet.fluke.model.Player;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class ManagePlayersTab extends AbstractFlukeTab {

    public static final String TYPE_ID = "managePlayersView";
    public static final String LABEL = "Manage Players";

    private static final long serialVersionUID = 1L;

    private Grid grid;
    private FlukePlayerForm playerForm;
    private Window playerWindow;

    public ManagePlayersTab() {
        super();
        setCaption("Manage Players");
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractView#initContent()
     */
    @Override
    protected void initContent() {

        grid = new Grid();        
        grid.setSizeFull();
        
        grid.setColumns("id", Player.FIRSTNAME, Player.LASTNAME);
        grid.setSelectionMode(SelectionMode.MULTI);
        grid.addItemClickListener(new ItemClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemClick(ItemClickEvent event) {
                if (!event.isDoubleClick())
                    return;
                editPlayer(event);
            }
        });        


        Button createButton = new Button("Add player", this::createPlayer);
        createButton.setIcon(FontAwesome.PLUS);
        createButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        createButton.setDescription("add");

        Button editButton = new Button("Edit player", this::editPlayer);
        editButton.setIcon(FontAwesome.EDIT);
        editButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.setDescription("edit");
        
        Button deleteButton = new Button("Delete player");        
        deleteButton.setIcon(FontAwesome.REMOVE);
        deleteButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        deleteButton.setDescription("delete");
        
        deleteButton.addClickListener(this::clickSubmit);
        
        addCrudButton(createButton);
        addCrudButton(editButton);
        addCrudButton(deleteButton);
        
        

        updatePlayerList();
    }

    public void editPlayer(Event event) {


        Player player;
        if (event instanceof ItemClickEvent) {
            ItemClickEvent itemEvent = (ItemClickEvent) event;
            Property<Long> item = itemEvent.getItem().getItemProperty("id");
            player = playerService.findOne(item.getValue());
        } else {
        if (grid.getSelectedRows().size() < 1) {
            Note.warning("Please select a player");
            return;
        }
        if (grid.getSelectedRows().size() > 1) {
            Note.warning("Only 1 selection is allowed, first item is used");
        }

        Object item = new ArrayList<>(grid.getSelectedRows()).get(0);
        if (!(item instanceof Player)) {
            Note.error("Selected item is not a player");
            return;
        }
            player = (Player) item;
        }
        
        playerForm = new FlukePlayerForm();
        playerForm.setName(player.getFirstName(), player.getLastName());
        playerForm.getSubmit().addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                player.setFirstName(playerForm.getFirstName());
                player.setLastName(playerForm.getLastName());
                playerService.save(player);
                event.getButton().removeClickListener(this);
                updatePlayerList();
                playerWindow.close();
                Note.info("Player updated");
            }
        });
        openPlayerWindow("Edit Player");
        updatePlayerList();

    }

    public void openPlayerWindow(String caption) {

        playerWindow = new Window();
        playerWindow.setContent(playerForm);

        playerWindow.setContent(playerForm);
        playerWindow.setCaption(caption);
        playerWindow.center();
        getUI().addWindow(playerWindow);
    }

    private void createPlayer(ClickEvent event) {

        playerForm = new FlukePlayerForm();
        playerForm.setName("", "");
        playerForm.getSubmit().addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {

                Player player = new Player();
                player.setFirstName(playerForm.getFirstName());
                player.setLastName(playerForm.getLastName());
                playerService.save(player);
                Note.info("Player created");
                updatePlayerList();
                event.getButton().removeClickListener(this);
                playerWindow.close();

            }
        });

        openPlayerWindow("Create Player");
        updatePlayerList();

    }

    private void clickSubmit(ClickEvent event) {

        Collection<Object> selectedRows = grid.getSelectedRows();
        ArrayList<Player> selectedPlayers = new ArrayList<>();
        for (Object object : selectedRows) {
            if (object instanceof Player) {
                selectedPlayers.add((Player) object);
            }
        }
        selectedPlayers.forEach(player -> playerService.delete(player.getId()));
        updatePlayerList();
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getMainComponent()
     */
    @Override
    protected Component getMainComponent() {
        return grid;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getTypeID()
     */
    @Override
    public String getTypeID() {
        return TYPE_ID;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getLabel()
     */
    @Override
    public String getLabel() {
        return LABEL;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getGrid()
     */
    @Override
    protected Grid getGrid() {
        return grid;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#doEnter()
     */
    @Override
    protected void doEnter() {
        playerForm.setVisible(false);
    }

}
