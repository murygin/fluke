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

/**
 *
 * @author Benjamin Weißenfels <bw@sernet.de>
 * @author Sebastian Hagedorn <sh[at]sernet.de>
 */
public interface ITeam {

    long getId();
    
    void setId(long id);
    
    IPlayer getOffensivePlayer();
    
    void setOffensivePlayer(IPlayer offensivePlayer);
    
    IPlayer getDefensivePlayer();
    
    void setDefensivePlayer(IPlayer defensivePlayer);
    
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
