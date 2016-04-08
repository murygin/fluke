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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.gui.vaadin.ui.components.TrackMatchResultPanel;
import de.sernet.fluke.interfaces.*;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class TrackMatchResultsTab extends FormLayout implements IFlukeUITab {

    public static final String TYPE_ID = "trackResultsView";
    public static final String LABEL = "Track Results";

    private static final long serialVersionUID = 1L;

    private HorizontalLayout mainLayout;
    private Set<IGame> untrackedGames;
    private IGameResultService gameResultService;
    private Grid grid;

    private IGameService gameService;

    public TrackMatchResultsTab(GameRestClient gameRestClient, GameResultRestClient gameResultService) {
        this.untrackedGames = new HashSet<>();
        this.gameService = gameRestClient;
        this.untrackedGames.addAll(Arrays.asList(gameService.findAllUntrackedGames()));
        this.gameResultService = gameResultService;
        createContent();
        this.setCaption(LABEL);
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#initContent()
     */
    protected void createContent() {

        mainLayout = new HorizontalLayout();

        mainLayout.setSizeFull();

        grid = new Grid();
        grid.setColumns("gameDate", "redTeam", "blueTeam");
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setContainerDataSource(
                new BeanItemContainer<>(IGame.class, untrackedGames));


        Button editButton = new Button("Edit Result", this::editResult);
        mainLayout.addComponents(grid, editButton);
        grid.addItemClickListener(new ItemClickListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void itemClick(ItemClickEvent event) {
                if (!event.isDoubleClick())
                    return;
                editResult(event);
            }
        });
        addComponent(mainLayout);

    }

    private void editResult(Event event) {

        Game game = (Game) grid.getSelectedRow();
        TrackMatchResultPanel matchPanel = new TrackMatchResultPanel(game, gameResultService, this);

        Window resultWindow = new Window("Edit result");
        VerticalLayout windowLayout = new VerticalLayout(matchPanel);
        windowLayout.setMargin(true);
        windowLayout.setSpacing(true);
        resultWindow.setContent(windowLayout);
        resultWindow.center();
        getUI().addWindow(resultWindow);

    }

    @Override
    public void doOnEnter() {
        mainLayout.removeAllComponents();
        this.untrackedGames.clear();
        this.untrackedGames.addAll(Arrays.asList(gameService.findAllUntrackedGames()));
        createContent();
    }

    public void refreshContent(){
        doOnEnter();
    }

}
