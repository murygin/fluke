/*******************************************************************************
 * Copyright (c) 2016 Sebastian Hagedorn.
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
 *     Sebastian Hagedorn <sh[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.interfaces;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public interface IGameResult {
    
    void setId(long id);
    
    long getId();
    
    short getBlueTeamGoals();
    
    short getRedTeamGoals();
    
    void setBlueTeamGoals(short blueTeamGoals);
    
    void setRedTeamGoals(short redTeamGoals);
    
    public short getRedScoredOffensiveGoals();
    public void setRedScoredOffensiveGoals(short redScoredOffensiveGoals);
    public short getRedScoredDefensiveGoals();
    public void setRedScoredDefensiveGoals(short redScoredDefensiveGoals);
    public short getBlueScoredOffensiveGoals();
    public void setBlueScoredOffensiveGoals(short blueScoredOffensiveGoals);
    public short getBlueScoredDefensiveGoals();
    public void setBlueScoredDefensiveGoals(short blueScoredDefensiveGoals);

}
