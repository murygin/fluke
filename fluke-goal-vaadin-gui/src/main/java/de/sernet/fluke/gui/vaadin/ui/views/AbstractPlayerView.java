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

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import de.sernet.fluke.client.rest.PlayerRestClient;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public abstract class AbstractPlayerView extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    // TODO rmotza change to account properties
    protected static final PlayerRestClient playerService = new PlayerRestClient("fluke", "fluke");

    public AbstractPlayerView() {
        initContent();
        addComponent(getMainComponent());
        setSizeFull();
        setComponentAlignment(getMainComponent(),
                Alignment.MIDDLE_CENTER);
    }

    protected abstract Component getMainComponent();
    protected abstract void initContent();
    public abstract String getTypeID();
    public abstract String getLabel();

    protected void updateList() {

        VaadinSession session = getUI().getSession();
        IAccount account = session.getAttribute(IAccount.class);

        List<IPlayer> players = new ArrayList<>();
        Iterable<IPlayer> findAll = playerService.findAll();
        if(findAll == null){
            Note.info("No players found");
        }else {
            findAll.forEach(players::add);
        }
        getGrid().setContainerDataSource(
                new BeanItemContainer<>(IPlayer.class, players));
    }

    protected abstract Grid getGrid();

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vaadin.navigator.View#enter(com.vaadin.navigator.ViewChangeListener.
     * ViewChangeEvent)
     */
    @Override
    public void enter(ViewChangeEvent event) {

        Note.info(getLabel() + " opened");
        updateList();
        doEnter();

    }

    protected abstract void doEnter();
}
