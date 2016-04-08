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
package de.sernet.fluke.gui.vaadin.ui.tabs;

import java.util.*;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import de.sernet.fluke.client.rest.PlayerRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.model.Player;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public abstract class AbstractPlayerTab extends FormLayout implements IFlukeUITab {

    private static final long serialVersionUID = 1L;

    protected final PlayerRestClient playerService;

    public AbstractPlayerTab() {
        playerService = ((FlukeUI) UI.getCurrent()).getPlayerRestClient();
        initContent();
        setSpacing(true);
        addComponent(getMainComponent());
        setSizeFull();
        setComponentAlignment(getMainComponent(),
                Alignment.MIDDLE_CENTER);
    }

    protected abstract Component getMainComponent();
    protected abstract void initContent();
    public abstract String getTypeID();
    public abstract String getLabel();

    protected void updatePlayerList() {

        List<Player> players = new ArrayList<>();
        Player[] findAll = playerService.findAll();
        if(findAll == null){

            Note.info("No players found");
        }else {
            players.addAll(Arrays.asList(findAll));
        }
        getGrid().setContainerDataSource(
                new BeanItemContainer<>(Player.class, players));
    }

    protected abstract Grid getGrid();

    protected abstract void doEnter();

    /*
     * (non-Javadoc)
     *
     * @see de.sernet.fluke.gui.vaadin.ui.views.IFlukeUITab#doOnEnter()
     */
    @Override
    public void doOnEnter() {

        updatePlayerList();
        doEnter();
    }
}
