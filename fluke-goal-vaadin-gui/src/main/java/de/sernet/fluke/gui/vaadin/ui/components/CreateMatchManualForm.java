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

import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class CreateMatchManualForm extends Panel {
    
    private static final long serialVersionUID = 20160407105117L;

    private ComboBox redOffensiveTeamPlayer;
    private ComboBox redDefensiveTeamPlayer;
    private ComboBox blueOffensiveTeamPlayer;
    private ComboBox blueDefensiveTeamPlayer;
    
    private Button submitMatchButton;
    
    
    private Collection<IPlayer> comboContent;

    public CreateMatchManualForm(Collection<IPlayer> content) {
        
        final FormLayout layout = new FormLayout();
        layout.setMargin(true);
        this.comboContent = content;
        createContent(layout);
        
    }

    private void createContent(final FormLayout form) {
        
        redOffensiveTeamPlayer = getPlayerComboBox("Red Team Offensive Player");
        form.addComponent(redOffensiveTeamPlayer);
        
        redDefensiveTeamPlayer = getPlayerComboBox("Red Team Defensive Player");
        form.addComponent(redDefensiveTeamPlayer);

        blueOffensiveTeamPlayer = getPlayerComboBox("Blue Team Offensive Player");
        form.addComponent(blueOffensiveTeamPlayer);
        
        blueDefensiveTeamPlayer = getPlayerComboBox("Blue Team Defensive Player");
        form.addComponent(blueDefensiveTeamPlayer);
        
        submitMatchButton = new Button("Submit Results");
        form.addComponent(submitMatchButton);
        
        setContent(form);
    }
    
    public Button getSubmitButton() {
        return submitMatchButton;
    }

    private ComboBox getPlayerComboBox(String caption) {
        ComboBox combo = new ComboBox(caption, comboContent);
        combo.setVisible(true);
        combo.setMultiSelect(false);
        combo.setNullSelectionAllowed(false);
        combo.setNullSelectionItemId(0);
        return combo;
    }


    /**
     * returns the ids of the combo boxes: ids[0] = redDefensiveTeamPlayer,
     * ids[1] = redOffensiveTeamPlayer, ids[2] = blueDefensiveTeamPlayer, ids[3]
     * = blueOffensiveTeamPlayer
     * 
     * @return
     */
    public long[] getGame() {
        long[] ids = new long[4];
        ids[0] = ((IPlayer) redDefensiveTeamPlayer.getValue()).getId();
        ids[1] = ((IPlayer) redOffensiveTeamPlayer.getValue()).getId();
        ids[2] = ((IPlayer) blueDefensiveTeamPlayer.getValue()).getId();
        ids[3] = ((IPlayer) blueOffensiveTeamPlayer.getValue()).getId();
        return ids;
    }
}
