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
package de.sernet.fluke.interfaces;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
public interface IPlayer {
    
    static final String FIRSTNAME = "firstName";
    static final String LASTNAME = "lastName";
    static final String WONGAMES = "wonGames";
    static final String LOSTGAMES = "lostGames";
    static final String SCOREDOFFENSIVEGOALS = "scoredOffensiveGoals";
    static final String SCOREDDEFENSIVEGOALS = "scoredDefensiveGoals";
    static final String SCOREDTOTALGOALS ="scoredTotalGoals";
    static final String CONCEDEDGOALS = "concededGoals";
    
    long getId();

    void setId(long id);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    public long getWonGames();

    public void setWonGames(long wonGames);
    
    public void increaseWonGames(short wonGames);
    
    public void increaseLostGames(short lostGames);

    public long getLostGames();

    public void setLostGames(long lostGames);

    public long getScoredOffensiveGoals();

    public void setScoredOffensiveGoals(long scoredOffensiveGoals);
    
    public void increaseScoredOffensiveGoals(short scoredOffensiveGoals);

    public long getScoredDefensiveGoals();

    public void setScoredDefensiveGoals(long scoredDefensiveGoals);
    
    public void increaseScoredDefensiveGoals(short scoredDefensiveGoals);

    public long getScoredTotalGoals();

    public void setScoredTotalGoals(long scoredTotalGoals);
    
    public void increaseScoredTotalGoals(short scoredTotalGoals);

    public long getConcededGoals();

    public void setConcededGoals(long concededGoals);
    
    public void increaseConcededGoals(short concededGoals);

}