/*******************************************************************************
 * Copyright (c) 2016 Ruth Motza.
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Ruth Motza <rm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.gui.vaadin.ui.components;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.*;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class FlukePlayerForm extends Panel {

    private static final long serialVersionUID = 1L;
    private final TextField firstName;
    private final TextField lastName;
    private final Button submit;


    public FlukePlayerForm() {
        final FormLayout form = new FormLayout();
        form.setMargin(true);
        form.setSizeUndefined();
        
        firstName = new TextField("Vorname");
        form.addComponent(firstName);
        lastName = new TextField("Nachname");
        form.addComponent(lastName);

        submit = new Button("Submit");

        lastName.addShortcutListener(
                new ShortcutListener("Enter", ShortcutAction.KeyCode.ENTER, null) {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void handleAction(Object sender, Object target) {

                        submit.click();
                    }
                });
        
        form.addComponent(submit);
        setContent(form);
    }

    public Button getSubmit() {
        return submit;
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }

    public void setName(String firstName, String lastName){
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
    }

}
