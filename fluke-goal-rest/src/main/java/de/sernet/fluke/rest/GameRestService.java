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
package de.sernet.fluke.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.persistence.Game;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
@RestController
@RequestMapping("/service/game")
public class GameRestService {

    @Autowired
    IGameService gameService;

    /* (non-Javadoc)
     * @see de.sernet.fluke.rest.IPlayerService#save(de.sernet.fluke.persistence.Player)
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IGame> save(@RequestBody IGame iGame) {
        IGame savedIGame = gameService.save(iGame);
        ResponseEntity<IGame> response = new ResponseEntity<>(savedIGame, HttpStatus.CREATED);
        return response;
    }
    
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<IGame> create(@RequestBody PlayerSelection playerSelection) {
        IGame savedIGame = gameService.create(
            playerSelection.getRedOffensiveId(), 
            playerSelection.getRedDefensiveId(), 
            playerSelection.getBlueOffensiveId(), 
            playerSelection.getBlueDefensiveId());
        ResponseEntity<IGame> response = new ResponseEntity<>(savedIGame, HttpStatus.CREATED);
        return response;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.rest.IPlayerService#findOne(java.lang.Long)
     */
    @RequestMapping(path = "/{gameId}", method = RequestMethod.GET)
    public ResponseEntity<IGame> findOne(@PathVariable Long gameId) {
        IGame iGame = gameService.findById(gameId);
        HttpStatus status = (iGame != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<IGame> response = new ResponseEntity<>(iGame, status);
        return response;
    }
    
    @RequestMapping(path = "/untrackedGames", method = RequestMethod.GET)
    public Game[] findAllUntrackedGames(){
        IGame[] untrackedGames = gameService.findAllUntrackedGames();
        Game[] castedGames = new Game[untrackedGames.length];
        for(int i = 0; i < untrackedGames.length; i++){
            castedGames[i] = (Game)untrackedGames[i];
        }
        HttpStatus status = (untrackedGames != null && untrackedGames.length > 0)
                ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<Game[]> response = new ResponseEntity<>(castedGames, status);
        return response.getBody();
    }
}
