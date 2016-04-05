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

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Entity
public class Team implements ITeam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="offensivePlayerId")
    private Player offensivePlayer;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="defensicePlayerId")
    private Player defensivePlayer;
    

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;

    }

    @Override
    public IPlayer getOffensivePlayer() {
        return offensivePlayer;
    }
    
    @Override
    public void setOffensivePlayer(IPlayer offensivePlayer) {
        this.offensivePlayer  = (Player)offensivePlayer;
    }

    @Override
    public IPlayer getDefensivePlayer() {
        return defensivePlayer;
    }

    @Override
    public void setDefensivePlayer(IPlayer defensivePlayer) {
        this.defensivePlayer = (Player)defensivePlayer;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((defensivePlayer == null) ? 0 : defensivePlayer.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((offensivePlayer == null) ? 0 : offensivePlayer.hashCode());
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
        Team other = (Team) obj;
        if (defensivePlayer == null) {
            if (other.defensivePlayer != null) {
                return false;
            }
        } else if (!defensivePlayer.equals(other.defensivePlayer)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (offensivePlayer == null) {
            if (other.offensivePlayer != null) {
                return false;
            }
        } else if (!offensivePlayer.equals(other.offensivePlayer)) {
            return false;
        }
        return true;
    }

}
