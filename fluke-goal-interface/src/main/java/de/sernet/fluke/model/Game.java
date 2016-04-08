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

import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResult;
import de.sernet.fluke.interfaces.ITeam;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Entity
public class Game implements IGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="blueTeamId", nullable=false)
    private Team blueTeam;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="redTeamId", nullable=false)
    private Team redTeam;
    
    @Column( name = "gameDate", nullable=false)
    private LocalDateTime gameDate;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="gameResultId")
    private GameResult result;
    
    public Game(){}
    
    public Game(ITeam redTeam, ITeam blueTeam){
        this.redTeam = (Team)redTeam;
        this.blueTeam = (Team)blueTeam;
        this.gameDate = LocalDateTime.now();
    }
    
    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public ITeam getBlueTeam() {
        return blueTeam;
    }

    @Override
    public ITeam getRedTeam() {
        return redTeam;
    }

    @Override
    public void setBlueTeam(ITeam blueTeam) {
        this.blueTeam = (Team)blueTeam;
    }

    @Override
    public void setRedTeam(ITeam redTeam) {
        this.redTeam = (Team)redTeam;
    }

    @Override
    public LocalDateTime getGameDate() {
        return this.gameDate;
    }

    @Override
    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }

    @Override
    public void setResult(IGameResult gameResult) {
        this.result = (GameResult)gameResult;
    }

    @Override
    public IGameResult getResult() {
        return this.result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blueTeam == null) ? 0 : blueTeam.hashCode());
        result = prime * result + ((gameDate == null) ? 0 : gameDate.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((redTeam == null) ? 0 : redTeam.hashCode());
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
        Game other = (Game) obj;
        if (blueTeam == null) {
            if (other.blueTeam != null) {
                return false;
            }
        } else if (!blueTeam.equals(other.blueTeam)) {
            return false;
        }
        if (gameDate == null) {
            if (other.gameDate != null) {
                return false;
            }
        } else if (!gameDate.equals(other.gameDate)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (redTeam == null) {
            if (other.redTeam != null) {
                return false;
            }
        } else if (!redTeam.equals(other.redTeam)) {
            return false;
        }
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        return true;
    }

}
