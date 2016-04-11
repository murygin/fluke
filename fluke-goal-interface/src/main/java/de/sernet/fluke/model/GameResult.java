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
package de.sernet.fluke.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Entity
public class GameResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column( name = "blueTeamGoals", nullable=false)
    private short blueTeamGoals;
    
    @Column( name = "redTeamGoals", nullable=false)
    private short redTeamGoals;

    /* how many offensive goals does the player have scored over all */
    @Column ( name = "redScoredOffensiveGoals", columnDefinition="tinyint default 0")
    private short redScoredOffensiveGoals;
    
    /* how many defensive goals does the player have scored over all */
    @Column ( name = "redScoredDefensiveGoals", columnDefinition="tinyint default 0")
    private short redScoredDefensiveGoals;
    
    /* how many offensive goals does the player have scored over all */
    @Column ( name = "blueScoredOffensiveGoals", columnDefinition="tinyint default 0")
    private short blueScoredOffensiveGoals;
    
    /* how many defensive goals does the player have scored over all */
    @Column ( name = "blueScoredDefensiveGoals", columnDefinition="tinyint default 0")
    private short blueScoredDefensiveGoals;
    
    public GameResult(short redTeamGoals, short blueTeamGoals){
        this.redTeamGoals = redTeamGoals;
        this.blueTeamGoals = blueTeamGoals;
    }
    
    public GameResult(){}
    
    public GameResult(short redTeamOffensiveGoals, short redTeamDefensiveGoals, short blueTeamOffensiveGoals, short blueTeamDefensiveGoals){
        this((short) (redTeamDefensiveGoals+redTeamOffensiveGoals), (short)(blueTeamDefensiveGoals+blueTeamOffensiveGoals) );
        this.redScoredDefensiveGoals = redTeamDefensiveGoals;
        this.redScoredOffensiveGoals = redTeamOffensiveGoals;
        this.blueScoredDefensiveGoals = blueTeamDefensiveGoals;
        this.blueScoredOffensiveGoals = blueTeamOffensiveGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setId(long)
     */
    public void setId(long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getId()
     */
    public long getId() {
        return this.id;
    }
    

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getBlueTeamGoals()
     */
    public short getBlueTeamGoals() {
        return blueTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#getRedTeamGoals()
     */
    public short getRedTeamGoals() {
        return redTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setBlueTeamGoals()
     */
    public void setBlueTeamGoals(short blueTeamGoals) {
        this.blueTeamGoals = blueTeamGoals;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IGameResult#setRedTeamGoals()
     */
    public void setRedTeamGoals(short redTeamGoals) {
        this.redTeamGoals = redTeamGoals;
    }

    /**
     * @return the redScoredOffensiveGoals
     */
    public short getRedScoredOffensiveGoals() {
        return redScoredOffensiveGoals;
    }

    /**
     * @param redScoredOffensiveGoals the redScoredOffensiveGoals to set
     */
    public void setRedScoredOffensiveGoals(short redScoredOffensiveGoals) {
        this.redScoredOffensiveGoals = redScoredOffensiveGoals;
    }

    /**
     * @return the redScoredDefensiveGoals
     */
    public short getRedScoredDefensiveGoals() {
        return redScoredDefensiveGoals;
    }

    /**
     * @param redScoredDefensiveGoals the redScoredDefensiveGoals to set
     */
    public void setRedScoredDefensiveGoals(short redScoredDefensiveGoals) {
        this.redScoredDefensiveGoals = redScoredDefensiveGoals;
    }

    /**
     * @return the blueScoredOffensiveGoals
     */
    public short getBlueScoredOffensiveGoals() {
        return blueScoredOffensiveGoals;
    }

    /**
     * @param blueScoredOffensiveGoals the blueScoredOffensiveGoals to set
     */
    public void setBlueScoredOffensiveGoals(short blueScoredOffensiveGoals) {
        this.blueScoredOffensiveGoals = blueScoredOffensiveGoals;
    }

    /**
     * @return the blueScoredDefensiveGoals
     */
    public short getBlueScoredDefensiveGoals() {
        return blueScoredDefensiveGoals;
    }

    /**
     * @param blueScoredDefensiveGoals the blueScoredDefensiveGoals to set
     */
    public void setBlueScoredDefensiveGoals(short blueScoredDefensiveGoals) {
        this.blueScoredDefensiveGoals = blueScoredDefensiveGoals;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blueScoredDefensiveGoals;
        result = prime * result + blueScoredOffensiveGoals;
        result = prime * result + blueTeamGoals;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + redScoredDefensiveGoals;
        result = prime * result + redScoredOffensiveGoals;
        result = prime * result + redTeamGoals;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GameResult other = (GameResult) obj;
        if (blueScoredDefensiveGoals != other.blueScoredDefensiveGoals) {
            return false;
        }
        if (blueScoredOffensiveGoals != other.blueScoredOffensiveGoals) {
            return false;
        }
        if (blueTeamGoals != other.blueTeamGoals) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (redScoredDefensiveGoals != other.redScoredDefensiveGoals) {
            return false;
        }
        if (redScoredOffensiveGoals != other.redScoredOffensiveGoals) {
            return false;
        }
        if (redTeamGoals != other.redTeamGoals) {
            return false;
        }
        return true;
    }


}
