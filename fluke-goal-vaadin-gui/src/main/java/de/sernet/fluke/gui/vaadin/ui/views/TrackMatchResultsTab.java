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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.SelectionMode;

import de.sernet.fluke.client.rest.GameRestClient;
import de.sernet.fluke.client.rest.GameResultRestClient;
import de.sernet.fluke.gui.vaadin.ui.components.TrackMatchResultPanel;
import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.interfaces.IGameService;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class TrackMatchResultsTab extends FormLayout implements IFlukeUITab {
    
    public static final String TYPE_ID = "trackResultsView";
    public static final String LABEL = "Track Results";

    private static final long serialVersionUID = 1L;

    private Grid grid;
    private VerticalLayout mainLayout;

    private Set<IGame> untrackedGames;
    
    private IGameResultService gameResultService;
    
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
        
        mainLayout = new VerticalLayout();
        
        for(IGame game : untrackedGames){
            TrackMatchResultPanel matchPanel = new TrackMatchResultPanel(game, gameResultService, this);
             mainLayout.addComponent(matchPanel);
        }
        addComponent(mainLayout);
        
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
