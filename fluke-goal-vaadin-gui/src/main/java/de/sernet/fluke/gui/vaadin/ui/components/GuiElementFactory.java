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

import com.vaadin.ui.ComboBox;
import de.sernet.fluke.model.Player;
import java.util.Arrays;
import java.util.Collection;

/**
 * Do not instanciate this class. Use public static methods.
 * 
 * @author Daniel Murygin
 */
public abstract class GuiElementFactory {

    private static String[] goalCountArray = new String[]{"0", "1", "2", "3", "4", "5", "6"};
    
    private GuiElementFactory() {
    }
    
    public static ComboBox getGoalComboBox(String caption, boolean visible){
        ComboBox combo = new ComboBox(caption, Arrays.asList(goalCountArray));
        combo.setVisible(visible);
        combo.setMultiSelect(false);
        combo.setNullSelectionAllowed(false);
        combo.setNullSelectionItemId(0);
        return combo;
    }
    
    public static ComboBox getPlayerComboBox(String caption, Collection<Player> comboContent) {
        ComboBox combo = new ComboBox(caption, comboContent);
        combo.setVisible(true);
        combo.setMultiSelect(false);
        combo.setNullSelectionAllowed(false);
        combo.setNullSelectionItemId(0);
        return combo;
    }
    
}
