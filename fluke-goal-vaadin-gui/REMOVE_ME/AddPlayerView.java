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
package de.sernet.fluke.gui.vaadin.ui.views;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.sernet.fluke.gui.vaadin.ui.Note;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class AddPlayerView extends AbstractPlayerView {

    public static final String TYPE_ID = "addPlayerView";

    private static final long serialVersionUID = 1L;

    private Panel panel;

    public AddPlayerView() {
        super();

    }

    /* (non-Javadoc)
     * @see com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.ViewChangeEvent)
     */
    @Override
    public void enter(ViewChangeEvent event) {
        Note.info("Add Player opened");

    }

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.AbstractView#initContent()
     */
    @Override
    protected void initContent() {

        panel = new Panel("new Player");
        panel.setWidthUndefined();
        final FormLayout form = new FormLayout();
        form.setMargin(true);
        TextField vorname = new TextField("Vorname");
        form.addComponent(vorname);
        TextField nachname = new TextField("Nachname");
        form.addComponent(nachname);

        Button submit = new Button("submit");
        form.addComponent(submit);

        submit.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 3311159330860405923L;

            @Override
            public void buttonClick(ClickEvent event) {
                playerService.addPlayer(vorname.getValue(), nachname.getValue());
                vorname.setValue("");
                nachname.setValue("");

            }
        });
        panel.setContent(form);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.sernet.fluke.gui.vaadin.ui.views.AbstractPlayerView#getMainComponent()
     */
    @Override
    protected Component getMainComponent() {
        return panel;
    }

	@Override
	public String getTypeID() {

		return TYPE_ID;
	}

}
