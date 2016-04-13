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

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;
import de.sernet.fluke.client.rest.GameRestClient;

import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.gui.vaadin.ui.components.TrackMatchResultPanel;
import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.model.Game;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class TrackMatchResultsTab extends AbstractFlukeTab {

    public static final String TYPE_ID = "trackResultsView";
    public static final String LABEL = "Track Results";

    private static final long serialVersionUID = 1L;

    private final Set<Game> untrackedGames;
    private final IGameResultService gameResultService;
    private final GameRestClient gameService;

    private Grid grid;
    private Window resultWindow;

    public TrackMatchResultsTab() {

        setCaption(LABEL);

        this.gameService = ((FlukeUI) UI.getCurrent()).getGameRestClient();
        this.gameResultService = ((FlukeUI) UI.getCurrent()).getGameResultRestClient();

        this.untrackedGames = new HashSet<>();
        this.untrackedGames.addAll(Arrays.asList(gameService.findAllUntrackedGames()));

        createContent();
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#initContent()
     */
    protected void createContent() {
        grid.setContainerDataSource(
                new BeanItemContainer<>(Game.class, untrackedGames));
    }

    private void editResult(Event event) {

        Game game = null;
        if (event instanceof ItemClickEvent) {
            ItemClickEvent itemEvent = (ItemClickEvent) event;
            Property<Long> item = itemEvent.getItem().getItemProperty("id");
            game = gameService.findById(item.getValue());
        } else {
            game = (Game) grid.getSelectedRow();
        }

        if(game == null) {
            Note.warning("No game selected");
            return;
        }

        TrackMatchResultPanel matchPanel = new TrackMatchResultPanel(game, gameResultService, this);

        resultWindow = new Window("Edit result");
        VerticalLayout windowLayout = new VerticalLayout(matchPanel);
        windowLayout.setMargin(true);
        windowLayout.setSpacing(true);
        resultWindow.setContent(windowLayout);
        resultWindow.center();
        getUI().addWindow(resultWindow);
    }

    @Override
    public void doOnEnter() {
        doEnter();
    }

    public void afterResultSave() {
        closeResultWindow();
        refreshContent();
    }

    public void refreshContent() {
        doOnEnter();
    }

    public void closeResultWindow() {
        if (resultWindow != null && resultWindow.isClosable()) {
            resultWindow.close();
        }
    }

    @Override
    protected Component getMainComponent() {
        return grid;
    }

    @Override
    protected void initContent() {
        grid = new Grid();
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setColumns("gameDate", "redTeam", "blueTeam");
        grid.setSelectionMode(SelectionMode.SINGLE);

        grid.addItemClickListener(new ItemClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemClick(ItemClickEvent event) {
                if (!event.isDoubleClick()) {
                    return;
                }
                editResult(event);
            }
        });

        Button editButton = new Button("Edit Result", this::editResult);
        editButton.setIcon(FontAwesome.EDIT);
        editButton.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        editButton.setDescription("edit");

        addCrudButton(editButton);
    }

    @Override
    public String getTypeID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLabel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Grid getGrid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doEnter() {
        this.untrackedGames.clear();
        this.untrackedGames.addAll(Arrays.asList(gameService.findAllUntrackedGames()));
        createContent();
    }

}
