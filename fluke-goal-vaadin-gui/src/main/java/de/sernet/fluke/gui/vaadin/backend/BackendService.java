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
package de.sernet.fluke.gui.vaadin.backend;

import java.util.*;

import de.sernet.fluke.client.rest.Player;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Ruth Motza <rm[at]sernet[dot]de>
 */
public class BackendService {

    private static BackendService instance = null;
    private List<IPlayer> players;
    private long lastID = 0L;

    private BackendService() {
        this.players = new ArrayList<>();
        players.add(new Player(++lastID, "r", "m"));
        players.add(new Player(++lastID, "d", "m"));
        players.add(new Player(++lastID, "m", "r"));
        players.add(new Player(++lastID, "b", "w"));
        players.add(new Player(++lastID, "s", "h"));
        players.add(new Player(++lastID, "m", "f"));
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(String firstname, String lastname) {
        players.add(new Player(
                ++lastID, firstname, lastname));
        Note.info("player added");
    }

    public String createMatch(List<IPlayer> players) {

        if (players.size() < 4) {
            Note.error("no match possible,<br>there have to be at least 4 players!");
            return "";
        }
        StringBuilder match = new StringBuilder();
        Collections.shuffle(players);
        if(players.size() % 2 != 0){
            match.append("not playing: " + players.remove(0) + "<br>");
        }

        int num = 1;
        while (!players.isEmpty()) {
            match.append("Team: "+num++ +", ");
            match.append(players.remove(0).toString()+" & ");
            match.append(players.remove(0).toString() + ";<br>");
        }
        Note.info("match created");
        return match.toString();

    }

    public void remove(List<IPlayer> playerToDelete) {
        boolean removed = players.removeAll(playerToDelete);
        Note.info("Players removed: " + removed);

    }

    public static BackendService getInstance(){
        if(instance == null){
            instance = new BackendService();
        }
        return instance;
    }

}
