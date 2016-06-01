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

import java.util.*;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

import de.sernet.fluke.client.rest.GameRestClient;
import de.sernet.fluke.client.rest.GameResultRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.gui.vaadin.ui.components.CreateMatchManualForm;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.Player;
import de.sernet.fluke.rest.GoalsOfAGameCollection;
import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
@Service
@VaadinSessionScope
public class CreateMatchTab extends AbstractFlukeTab {

    public static final String TYPE_ID = "createMatchView";
    public static final String LABEL = "Create Match";

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("vaadinGameRestClient")
    private GameRestClient gameService;
    
    @Autowired
    private IGameResultService gameResultService;

    private Grid grid;
    protected Label result;
    private CreateMatchManualForm matchPanel;
    private Window manualMatchWindow;
    private Window resultWindow;

    public CreateMatchTab() {
        super();
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
        grid.setWidth(100, Unit.PERCENTAGE);

        result = new Label();

        Button createManuallyButton = new Button("Create match manually",
                this::createMatchManually);
        createManuallyButton.setIcon(FontAwesome.PLUS);
        createManuallyButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        createManuallyButton.setDescription("Create match manually");

        Button createAutomaticallyButton = new Button("",
                this::createMatchAutomatically);
        createAutomaticallyButton.setIcon(FontAwesome.RANDOM);
        createAutomaticallyButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        createAutomaticallyButton.setDescription("Create match automatically");

        addCrudButton(createAutomaticallyButton);
        addCrudButton(createManuallyButton);

        result.setContentMode(ContentMode.HTML);
    }

    private void createMatchAutomatically(ClickEvent event) {

        ArrayList<Player> selectedPlayers = new ArrayList<>();
        ArrayList<Object> selectedObjects = new ArrayList<>(grid.getSelectedRows());
        for (Object object : selectedObjects) {
            if (object instanceof Player) {
                selectedPlayers.add((Player) object);
            }
        }

        result.setValue(createMatchAutomatically(selectedPlayers));

        if (resultWindow != null && resultWindow.isVisible()) {
            resultWindow.close();
        }
        resultWindow = new Window("Match created");
        VerticalLayout windowLayout = new VerticalLayout(result);
        windowLayout.setMargin(true);
        windowLayout.setSpacing(true);
        resultWindow.setContent(windowLayout);
        resultWindow.center();
        getUI().addWindow(resultWindow);

    }

    public String createMatchAutomatically(List<Player> players) {

        ArrayList<Player> playersToCreateMatch = new ArrayList<>(players);
        if (playersToCreateMatch.size() < 4) {
            Note.warning("No match possible,\nthere have to be at least 4 players!");
            return "";
        }

        Collections.shuffle(playersToCreateMatch, new SecureRandom());
        StringBuilder teams = new StringBuilder();
        if (playersToCreateMatch.size() > 4) {
            Note.warning("Only 4 players allowed, rest will be removed!");
            teams.append("Not Playing");
            teams.append(" [ ");
            while (playersToCreateMatch.size() > 4) {
                teams.append(playersToCreateMatch.remove(0));
                teams.append(", ");
            }
            teams.deleteCharAt(teams.length() - 1);
            teams.deleteCharAt(teams.length() - 1);
            teams.append(" ] <br><br>");
        }
        gameService.create(playersToCreateMatch.get(0), playersToCreateMatch.get(1),
                playersToCreateMatch.get(2), playersToCreateMatch.get(3));

        teams.append("Team red:<br>");
        teams.append("offensive ");
        teams.append(playersToCreateMatch.get(0).toString());
        teams.append(",<br>");
        teams.append("defensive ");
        teams.append(playersToCreateMatch.get(1).toString());
        teams.append("<br><br>");
        teams.append("Team blue:<br>");
        teams.append("offensive ");
        teams.append(playersToCreateMatch.get(2).toString());
        teams.append(",<br>");
        teams.append("defensive ");
        teams.append(playersToCreateMatch.get(3).toString());

        Note.info("match created");
        return teams.toString();
    }

    private void createMatchManually(ClickEvent event) {

        ArrayList<Player> selectedPlayers = new ArrayList<>();
        if (grid.getSelectedRows().size() < 1) {
            selectedPlayers.addAll(Arrays.asList(getPlayerService().findAll()));
        } else {
            ArrayList<Object> selectedObjects = new ArrayList<>(grid.getSelectedRows());
            for (Object object : selectedObjects) {
                if (object instanceof Player) {
                    selectedPlayers.add((Player) object);
                }
            }
        }
        matchPanel = new CreateMatchManualForm(selectedPlayers);
        manualMatchWindow = new Window("Create a match manually");

        matchPanel.getSubmitButton().addClickListener(this::saveMatch);

        VerticalLayout windowLayout = new VerticalLayout(matchPanel);
        windowLayout.setMargin(true);
        windowLayout.setSpacing(true);
        manualMatchWindow.setContent(windowLayout);
        manualMatchWindow.center();
        getUI().addWindow(manualMatchWindow);

    }

    private void saveMatch(ClickEvent event) {
        long[] playerIds = matchPanel.getPlayerIds();
        if (onlyUniqueIds(playerIds)) {
            Game game = gameService.create(playerIds[1], playerIds[0], playerIds[3], playerIds[2]);
            boolean resultSaved = saveResult(game);
            manualMatchWindow.close();
            if(resultSaved) {
                Note.info("A new match with a result was created.");
            } else {
                Note.info("A new match was created. Go to 'Track Results' to set the result.");
            }
            
        } else {
            Note.warning("The Players chosen are not unique");
        }
    }
    
    private boolean saveResult(Game game) {
        boolean resultCreated = false;
        GoalsOfAGameCollection goals = matchPanel.getGoals();
        if(goals.isValidPlayerCollection()) {
            goals.setGameId(game.getId());
            gameResultService.trackGameResult(goals);
            resultCreated = true;
        }
        return resultCreated;
    }

    /**
     * @param ids
     * @return
     */
    private boolean onlyUniqueIds(long[] ids) {
        Set<Long> set = new HashSet<>();
        for (long id : ids) {
            set.add(id);
        }
        return set.size() == ids.length;
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
        result.setValue("");
    }

}
