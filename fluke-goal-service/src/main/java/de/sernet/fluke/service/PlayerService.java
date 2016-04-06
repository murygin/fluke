/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
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
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.PlayerRepository;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@Service
public class PlayerService implements IPlayerService {

    @Autowired
    PlayerRepository playerRepository;
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IPlayerService#save(de.sernet.fluke.interfaces.IPlayer)
     */
    @Override
    public IPlayer save(IPlayer player) {
        return playerRepository.save((Player)player);
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IPlayerService#findOne(java.lang.Long)
     */
    @Override
    public IPlayer findOne(Long playerId) {
        return playerRepository.findOne(playerId);
    }

    /**
     * Deletes the player with the given {@code id}.
     *
     * @param id the {@code id} of the player to be deleted.
     */
    @Override
    public void delete(long id) {

        if (findOne(id) != null) {
            playerRepository.delete(id);
        }
    }

    @Override
    public List<IPlayer> findAll() {
        Iterable<Player> players = playerRepository.findAll();
        List<IPlayer> castedPlayers = new ArrayList<>();

        for (Player player : players) {
            castedPlayers.add((Player) player);
        }

        return castedPlayers;
    }
}
