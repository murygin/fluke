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

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionMode;

import de.sernet.fluke.client.rest.Player;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.gui.vaadin.ui.components.FlukePlayerForm;
import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class ManagePlayersView extends AbstractPlayerView {

    public static final String TYPE_ID = "managePlayersView";
    public static final String LABEL = "Manage Players";

    private static final long serialVersionUID = 1L;

    private Grid grid;
    private HorizontalLayout mainLayout;
    private FlukePlayerForm playerForm;
    private Button submit;

    public ManagePlayersView() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractView#initContent()
     */
    @Override
    protected void initContent() {
        grid = new Grid();
        grid.setColumns("id", "firstName", "lastName");
        grid.setSelectionMode(SelectionMode.MULTI);
        mainLayout = new HorizontalLayout();
        mainLayout.setWidthUndefined();
        mainLayout.setSpacing(true);

        Button editButton = new Button("Edit player", this::editPlayer);
        
        

        Button createButton = new Button("Add player", this::createPlayer);

        Button deleteButton = new Button("Delete player");
        deleteButton.addClickListener(this::clickSubmit);
        

        VerticalLayout buttonLayout = new VerticalLayout(createButton, editButton, deleteButton,
                createInvisibleEditArea());

        mainLayout.addComponents(grid, buttonLayout);

        updateList();
    }

    public void editPlayer(ClickEvent event) {

        if (grid.getSelectedRows().size() < 1) {
            Note.error("Please select a player");
            return;
        }
        if (grid.getSelectedRows().size() > 1) {
            Note.warning("Only 1 selection is allowed, first item is used");
        }
        Object item = new ArrayList<>(grid.getSelectedRows()).get(0);
        if (item instanceof IPlayer) {
            final IPlayer player = (IPlayer) item;
            playerForm.setName(player.getFirstName(), player.getLastName());
            playerForm.setVisible(true);
            submit.addClickListener(new ClickListener() {

                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {

                    player.setFirstName(playerForm.getFirstName());
                    player.setLastName(playerForm.getLastName());
                    submit.removeClickListener(this);
                    updateList();
                    playerForm.setVisible(false);
                    Note.info("Player updated");
                }
            });

        } else {
            Note.error("something went wrong");
        }
        updateList();

    }

    private void createPlayer(ClickEvent event) {

        playerForm.setName("", "");
        playerForm.setVisible(true);
        playerForm.getSubmit().addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                Player player = new Player();
                player.setFirstName(playerForm.getFirstName());
                player.setLastName(playerForm.getLastName());
                playerService.save(player);
                Note.info("Player created");
                updateList();
                playerForm.setVisible(false);

            }
        });
        updateList();

    }

    private void clickSubmit(ClickEvent event) {

        Collection<Object> selectedRows = grid.getSelectedRows();
        ArrayList<IPlayer> selectedPlayers = new ArrayList<>();
        for (Object object : selectedRows) {
            if (object instanceof IPlayer) {
                selectedPlayers.add((IPlayer) object);
            }
        }
        selectedPlayers.forEach(player -> playerService.delete(player.getId()));
        updateList();
    }

    private Component createInvisibleEditArea() {

        playerForm = new FlukePlayerForm();
        playerForm.setVisible(false);
        return playerForm;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getMainComponent()
     */
    @Override
    protected Component getMainComponent() {
        return mainLayout;
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
