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
package de.sernet.fluke.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.sernet.fluke.interfaces.IGameResult;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResult implements IGameResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private short blueTeamGoals;
    private short redTeamGoals;

    public GameResult(short redTeamGoals, short blueTeamGoals){
        this.redTeamGoals = redTeamGoals;
        this.blueTeamGoals = blueTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getId()
     */
    @Override
    public long getId() {
        return this.id;
    }
    

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getBlueTeamGoals()
     */
    @Override
    public short getBlueTeamGoals() {
        return blueTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getRedTeamGoals()
     */
    @Override
    public short getRedTeamGoals() {
        return redTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setBlueTeamGoals()
     */
    @Override
    public void setBlueTeamGoals(short blueTeamGoals) {
        this.blueTeamGoals = blueTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setRedTeamGoals()
     */
    @Override
    public void setRedTeamGoals(short redTeamGoals) {
        this.redTeamGoals = redTeamGoals;
    }

}
