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

import de.sernet.fluke.model.Player;

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 * @author Sebastian Hagedorn <sh[at]sernet.de>
 */
public interface ITeam {
    
    static final String WONGAMES = "wonGames";
    static final String LOSTGAMES = "lostGames";
    static final String SCOREDOFFENSIVEGOALS = "scoredOffensiveGoals";
    static final String SCOREDDEFENSIVEGOALS = "scoredDefensiveGoals";
    static final String SCOREDTOTALGOALS = "scoredTotalGoals";
    static final String CONCEDEDGOALS = "concededGoals";

    long getId();
    
    void setId(long id);
    
    Player getOffensivePlayer();
    
    void setOffensivePlayer(Player offensivePlayer);
    
    Player getDefensivePlayer();
    
    void setDefensivePlayer(Player defensivePlayer);
    
    long getWonGames();

    void setWonGames(long wonGames);
    
    void increaseWonGames(short wonGames);

    long getLostGames();

    void setLostGames(long lostGames);
    
    void increaseLostGames(short lostGames);

    long getScoredOffensiveGoals();

    void setScoredOffensiveGoals(long scoredOffensiveGoals);
    
    void increaseScoredOffensiveGoals(short scoredOffensiveGoals);

    long getScoredDefensiveGoals();

    void setScoredDefensiveGoals(long scoredDefensiveGoals);
    
    void increaseScoredDefensiveGoals(short scoredDefensiveGoals);

    long getScoredTotalGoals();

    void setScoredTotalGoals(long scoredTotalGoals);
    
    void increaseScoredTotalGoals(short scoredTotalGoals);

    long getConcededGoals();

    void setConcededGoals(long concededGoals);
    
    void increaseConcededGoals(short concededGoals);
    
}
