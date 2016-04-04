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

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.sernet.fluke.interfaces.ICommunity;
import de.sernet.fluke.interfaces.IPlayer;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class Community implements ICommunity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private List<IPlayer> registeredPlayers;
    private String communityName;
    
    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public List<IPlayer> getRegisteredPlayers() {
        return registeredPlayers;
    }

    @Override
    public void registerPlayer(IPlayer newPlayer) {
        registeredPlayers.add(newPlayer);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((communityName == null) ? 0 : communityName.hashCode());
        result = prime * result + ((registeredPlayers == null) ? 0 : registeredPlayers.hashCode());
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
        Community other = (Community) obj;
        if (id != other.id) {
            return false;
        }
        if (communityName == null) {
            if (other.communityName != null) {
                return false;
            }
        } else if (!communityName.equals(other.communityName)) {
            return false;
        }
        if (registeredPlayers == null) {
            if (other.registeredPlayers != null) {
                return false;
            }
        } else if (!registeredPlayers.equals(other.registeredPlayers)) {
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return communityName; 
    }
    
    @Override
    public void setName(String name) {
        this.communityName = name;
    }
}
