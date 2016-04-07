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

import java.util.*;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;

import de.sernet.fluke.client.rest.GameRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class CreateMatchTab extends AbstractPlayerTab {

    public static final String TYPE_ID = "createMatchView";
    public static final String LABEL = "Create Match";

    private static final long serialVersionUID = 1L;

    private GameRestClient gameService;
    private Grid grid;
    private HorizontalLayout mainLayout;
    protected Label result;

    public CreateMatchTab() {
        super();
        gameService = ((FlukeUI) UI.getCurrent()).getGameRestClient();
        setCaption("Create Match");
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

        result = new Label();

        mainLayout = new HorizontalLayout();
        mainLayout.setWidthUndefined();
        mainLayout.setSpacing(true);
        mainLayout.addComponent(grid);

        Button createButton = new Button("create Match", this::createMatch);

        result.setContentMode(ContentMode.HTML);

        VerticalLayout layout = new VerticalLayout(createButton, result);
        mainLayout.addComponent(layout);
    }

    private void createMatch(ClickEvent event) {

        ArrayList<Object> selectedObjects = new ArrayList<>(grid.getSelectedRows());
        ArrayList<IPlayer> selectedPlayers = new ArrayList<>();
        for (Object object : selectedObjects) {
            if (object instanceof IPlayer) {
                selectedPlayers.add((IPlayer) object);
            }
        }

        result.setValue(createMatch(selectedPlayers));

    }

    public String createMatch(List<IPlayer> players) {

        ArrayList<IPlayer> playersToCreateMatch = new ArrayList<>(players);
        if (playersToCreateMatch.size() < 4) {
            Note.warning("no match possible,<br>there have to be at least 4 players!");
            return "";
        }
        Collections.shuffle(playersToCreateMatch);
        if (playersToCreateMatch.size() > 4) {
            Note.warning("only 4 players allowed, rest will be !");
            playersToCreateMatch = new ArrayList<>(playersToCreateMatch.subList(0, 4));
        }
        gameService.create(playersToCreateMatch.get(0), playersToCreateMatch.get(1),
                playersToCreateMatch.get(2), playersToCreateMatch.get(3));

        StringBuilder teams = new StringBuilder();
        teams.append("Team red:<br>");
        teams.append("offensive ");
        teams.append(players.get(0).toString());
        teams.append(",<br>");
        teams.append("defensive ");
        teams.append(players.get(1).toString());
        teams.append("<br><br>");
        teams.append("Team blue:<br>");
        teams.append("offensive ");
        teams.append(players.get(2).toString());
        teams.append(",<br>");
        teams.append("defensive ");
        teams.append(players.get(3).toString());


        Note.info("match created");
        return teams.toString();
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
        result.setValue("");
    }

}
