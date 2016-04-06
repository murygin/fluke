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
package de.sernet.fluke.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.persistence.Player;
import java.util.List;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RestController
@RequestMapping("/service/player")
public class PlayerRestService {

    @Autowired
    IPlayerService playerService;
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.rest.IPlayerService#save(de.sernet.fluke.persistence.Player)
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IPlayer> save(@RequestBody Player player) {
        IPlayer savedPlayer = playerService.save(player);
        ResponseEntity<IPlayer> response = new ResponseEntity<IPlayer>(savedPlayer, HttpStatus.CREATED);
        return response;
    }
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.rest.IPlayerService#findOne(java.lang.Long)
     */
    @RequestMapping( path="/{playerId}", method = RequestMethod.GET)
    public ResponseEntity<IPlayer> findOne(@PathVariable Long playerId) {
        IPlayer player = playerService.findOne(playerId);
        HttpStatus status = (player!=null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<IPlayer> response = new ResponseEntity<IPlayer>(player, status);
        return response;
    }

    @RequestMapping(path = "/{playerId}", method = RequestMethod.DELETE)
    public HttpStatus delete(@PathVariable Long playerId) {

        if (playerService.findOne(playerId) != null) {
            playerService.delete(playerId);
            return HttpStatus.OK;
        }

        return HttpStatus.NOT_FOUND;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity findAll() {
        List<IPlayer> players = (List<IPlayer>) playerService.findAll();
        HttpStatus status = (players != null && !players.isEmpty())
                ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity response = new ResponseEntity(status);
        return response;
    }
}
