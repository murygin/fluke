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

import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.PlayerRepository;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RestController
@RequestMapping("/service/player")
public class PlayerRestService {

    @Autowired
    PlayerRepository playerRepository;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Player> save(@RequestBody Player player) {
        Player savedPlayer = playerRepository.save(player);
        ResponseEntity<Player> response = new ResponseEntity<Player>(savedPlayer, HttpStatus.CREATED);
        return response;
    }
    
    @RequestMapping( path="/{playerId}", method = RequestMethod.GET)
    public ResponseEntity<Player> findOne(@PathVariable Long playerId) {
        Player player = playerRepository.findOne(playerId);
        HttpStatus status = (player!=null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<Player> response = new ResponseEntity<Player>(player, status);
        return response;
    }
}
