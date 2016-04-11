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
package de.sernet.fluke.interfaces;

import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.Player;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service to manage game. 
 * Use service IGameResultService to manage game results.
 * 
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public interface IGameService {
    
    /**
     * Creates a new game.
     * 
     * @param redOffensive Offensive player in red team
     * @param redDefensive Defensive player in red team
     * @param blueOffensive Offensive player in blue team
     * @param blueDefensive Defensive player in blue team
     * @return The game after it was created in data store
     */
    Game create(Player redOffensive, Player redDefensive, Player blueOffensive, Player blueDefensive );
    
    /**
     * Creates a new game.
     * 
     * @param redOffensiveId Id of offensive player in red team
     * @param redDefensiveId Id of defensive player in red team
     * @param blueOffensiveId Id of offensive player in blue team
     * @param blueDefensiveId Id of defensive player in blue team
     * @return The game after it was created in data store
     */
    Game create(long redOffensiveId, long redDefensiveId, long blueOffensiveId, long blueDefensiveId);
    
    /**
     * Saves a new or existing game in data store.
     * 
     * @param game A game
     * @return The game after it was saved in data store
     */
    Game save(Game game);
    
    /**
     * @param gameId The data store id of a game
     * @return The game with the id
     */
    Game findById(Long gameId);

    /**
     * Returns game with a specific date.
     * 
     * @param time A date
     * @return Games with the date
     */
    List<Game> findByDate(LocalDateTime time);
    
    /**
     * returns all untracked Games
     * @return
     */
    Game[] findAllUntrackedGames();
    
}
