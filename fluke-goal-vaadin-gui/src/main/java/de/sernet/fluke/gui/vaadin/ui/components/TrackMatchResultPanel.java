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
package de.sernet.fluke.gui.vaadin.ui.components;

import java.util.Arrays;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import de.sernet.fluke.gui.vaadin.ui.views.IFlukeUITab;
import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;


/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class TrackMatchResultPanel extends Panel {
    
    private static final long serialVersionUID = 20160407105117L;

    private Label gameLabel;
    
    private ComboBox redTeamGoalsCombo;
    private ComboBox blueTeamGoalsCombo;
    
    private ComboBox redOffensiveTeamGoalsCombo;
    private ComboBox redDefensiveTeamGoalsCombo;
    private ComboBox blueOffensiveTeamGoalsCombo;
    private ComboBox blueDefensiveTeamGoalsCombo;
    
    private Button showDetailedTrackingButton;
    private Button submitGameResultButton;
    
    private IGameResultService gameResultService;
    
    private static int[] goalCountArray = new int[]{0, 1, 2, 3, 4, 5, 6};
    
    private IGame game;
    
    public TrackMatchResultPanel(IGame game, IGameResultService service){
        
        this.gameResultService = service;
        this.game = game;
        
        final FormLayout layout = new FormLayout();
        layout.setMargin(true);
        
        createContent(game, layout);
        
    }

    private void createContent(IGame game, final FormLayout form) {
        showDetailedTrackingButton = new Button("Track Detailed Results", this::switchCombos);
        form.addComponent(showDetailedTrackingButton);

        gameLabel = new Label(getGameLabel(game));
        form.addComponent(gameLabel);
        
        redTeamGoalsCombo = new ComboBox("Red Team Goals", Arrays.asList(goalCountArray));
        form.addComponent(redTeamGoalsCombo);
        
        blueTeamGoalsCombo = new ComboBox("Blue Team Goals", Arrays.asList(goalCountArray));
        form.addComponent(blueTeamGoalsCombo);
        
        
        redOffensiveTeamGoalsCombo = new ComboBox("Red Team Offensive Goals", Arrays.asList(goalCountArray));
        redOffensiveTeamGoalsCombo.setVisible(false);
        form.addComponent(redOffensiveTeamGoalsCombo);
        
        redDefensiveTeamGoalsCombo = new ComboBox("Red Team Defensive Goals", Arrays.asList(goalCountArray));
        redDefensiveTeamGoalsCombo.setVisible(false);
        form.addComponent(redDefensiveTeamGoalsCombo);

        blueOffensiveTeamGoalsCombo = new ComboBox("Blue Team Offensive Goals", Arrays.asList(goalCountArray));
        blueOffensiveTeamGoalsCombo.setVisible(false);
        form.addComponent(blueOffensiveTeamGoalsCombo);
        
        blueDefensiveTeamGoalsCombo = new ComboBox("Blue Team Defensive Goals", Arrays.asList(goalCountArray));
        blueDefensiveTeamGoalsCombo.setVisible(false);
        form.addComponent(blueDefensiveTeamGoalsCombo);
        
        submitGameResultButton = new Button("Submit Results", this::trackResult);
        form.addComponent(submitGameResultButton);
        
        setContent(form);
    }
    
    private void trackResult(Button.ClickEvent event){
        if(event.getSource() == submitGameResultButton){
            boolean trackDetailed = !redTeamGoalsCombo.isVisible();
            if(trackDetailed){
                gameResultService.trackGameResult(game, 
                        (short)redOffensiveTeamGoalsCombo.getValue(), 
                        (short)redDefensiveTeamGoalsCombo.getValue(), 
                        (short)blueOffensiveTeamGoalsCombo.getValue(), 
                        (short)blueDefensiveTeamGoalsCombo.getValue());
            } else {
                gameResultService.trackGameResult(game,
                        (short)redTeamGoalsCombo.getValue(),
                        (short)blueTeamGoalsCombo.getValue());
            }
        }
    }
        
    private void switchCombos(Button.ClickEvent event){
        if(event.getSource() == showDetailedTrackingButton){
            blueTeamGoalsCombo.setVisible(!blueTeamGoalsCombo.isVisible());
            redTeamGoalsCombo.setVisible(!redTeamGoalsCombo.isVisible());

            blueDefensiveTeamGoalsCombo.setVisible(!blueDefensiveTeamGoalsCombo.isVisible());
            blueOffensiveTeamGoalsCombo.setVisible(!blueOffensiveTeamGoalsCombo.isVisible());
            redDefensiveTeamGoalsCombo.setVisible(!redDefensiveTeamGoalsCombo.isVisible());
            redOffensiveTeamGoalsCombo.setVisible(!redOffensiveTeamGoalsCombo.isVisible());
        }
    }

    private String getGameLabel(IGame game){
        StringBuilder sb = new StringBuilder(String.valueOf(game.getId()));
        sb.append(":\tRed ").append(getTeamLabel(game.getRedTeam()));
        sb.append(" vs. ");
        sb.append("Blue ").append(getTeamLabel(game.getBlueTeam()));
        return sb.toString();
    }
    
    private String getTeamLabel(ITeam team){
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(getPlayerAbbr(team.getOffensivePlayer())).append(", ");
        sb.append(getPlayerAbbr(team.getDefensivePlayer())).append(">");
        return sb.toString();
    }
    
    private String getPlayerAbbr(IPlayer player){
        StringBuilder sb = new StringBuilder();
        sb.append(player.getFirstName().charAt(0));
        if(player.getLastName() != null){
            sb.append(player.getLastName().charAt(0));
        }
        return sb.toString();
    }


}
