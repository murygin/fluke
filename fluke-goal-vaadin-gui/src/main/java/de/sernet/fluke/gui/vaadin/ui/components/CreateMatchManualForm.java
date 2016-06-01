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

import java.util.Collection;

import com.vaadin.ui.*;

import de.sernet.fluke.model.Player;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

/**
 * This class creates a form to create a game and optionally a game result.
 * 
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public class CreateMatchManualForm extends Panel {
    
    private static final long serialVersionUID = 20160407105117L;

    private ComboBox redOffensiveTeamPlayer;
    private ComboBox redDefensiveTeamPlayer;
    private ComboBox blueOffensiveTeamPlayer;
    private ComboBox blueDefensiveTeamPlayer;
    
    private ComboBox redOffensiveTeamGoalsCombo;
    private ComboBox redDefensiveTeamGoalsCombo;
    private ComboBox blueOffensiveTeamGoalsCombo;
    private ComboBox blueDefensiveTeamGoalsCombo;
    
    private Button submitMatchButton;
    
    
    private Collection<Player> comboContent;

    public CreateMatchManualForm(Collection<Player> content) {      
        final GridLayout layout = new GridLayout(2, 5);
        layout.setMargin(true);
        layout.setSpacing(true);
        this.comboContent = content;
        createContent(layout);
    }

    private void createContent(final GridLayout form) {
        
        Label description = new Label("Please select 4 Players. Optionally, you can set the number of goals for each player. If you dont set the goals a game but no game result is created.");
        form.addComponent(description, 0, 0, 1, 0);
        
        redOffensiveTeamPlayer = GuiElementFactory.getPlayerComboBox("Red Team Offensive Player", comboContent);
        form.addComponent(redOffensiveTeamPlayer);
        
        redOffensiveTeamGoalsCombo = GuiElementFactory.getGoalComboBox("Goals", true);
        form.addComponent(redOffensiveTeamGoalsCombo);
        
        redDefensiveTeamPlayer = GuiElementFactory.getPlayerComboBox("Red Team Defensive Player", comboContent);
        form.addComponent(redDefensiveTeamPlayer);
        
        redDefensiveTeamGoalsCombo = GuiElementFactory.getGoalComboBox("Goals", true);
        form.addComponent(redDefensiveTeamGoalsCombo);

        blueOffensiveTeamPlayer = GuiElementFactory.getPlayerComboBox("Blue Team Offensive Player", comboContent);
        form.addComponent(blueOffensiveTeamPlayer);
        
        blueOffensiveTeamGoalsCombo = GuiElementFactory.getGoalComboBox("Goals", true);
        form.addComponent(blueOffensiveTeamGoalsCombo);
        
        blueDefensiveTeamPlayer = GuiElementFactory.getPlayerComboBox("Blue Team Defensive Player", comboContent);
        form.addComponent(blueDefensiveTeamPlayer);
        
        blueDefensiveTeamGoalsCombo = GuiElementFactory.getGoalComboBox("Goals", true);
        form.addComponent(blueDefensiveTeamGoalsCombo);
        
        submitMatchButton = new Button("Create Game");
        form.addComponent(submitMatchButton);
        
        setContent(form);
    }
    
    public Button getSubmitButton() {
        return submitMatchButton;
    }

    /**
     * long[0] = redDefensiveTeamPlayer,
     * long[1] = redOffensiveTeamPlayer, 
     * long[2] = blueDefensiveTeamPlayer, 
     * long[3] = blueOffensiveTeamPlayer
     * 
     * @return The ids of the players which were selected for a new game
     */
    public long[] getPlayerIds() {
        long[] ids = new long[4];
        ids[0] = ((Player) redDefensiveTeamPlayer.getValue()).getId();
        ids[1] = ((Player) redOffensiveTeamPlayer.getValue()).getId();
        ids[2] = ((Player) blueDefensiveTeamPlayer.getValue()).getId();
        ids[3] = ((Player) blueOffensiveTeamPlayer.getValue()).getId();
        return ids;
    }

    public GoalsOfAGameCollection getGoals() {
        GoalsOfAGameCollection goals = new GoalsOfAGameCollection();
        goals.setBlueScoredDefensiveGoals(convertObjectToShort(blueDefensiveTeamGoalsCombo.getValue()));
        goals.setBlueScoredOffensiveGoals(convertObjectToShort(blueOffensiveTeamGoalsCombo.getValue()));
        goals.setRedScoredDefensiveGoals(convertObjectToShort(redDefensiveTeamGoalsCombo.getValue()));
        goals.setRedScoredOffensiveGoals(convertObjectToShort(redOffensiveTeamGoalsCombo.getValue()));
        return goals;
    }

    /**
     * Returns null if value is null.
     * 
     * @throws NumberFormatException If values is not a number
     * @return The value converted to a short
     */
    private Short convertObjectToShort(Object value) throws NumberFormatException {
        Short result = null;
        if(value!=null) {
            result = Short.valueOf((String) value);
        }
        return result;
    }
}
