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

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"offensivePlayerId" , "defensicePlayerId"})})
public class Team implements ITeam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="offensivePlayerId", nullable=false)
    private Player offensivePlayer;
    
    @Access(AccessType.PROPERTY)
    @OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="defensicePlayerId", nullable=false)
    private Player defensivePlayer;
    
    /* how many games the player won over all */
    @Column( name = ITeam.WONGAMES, columnDefinition="bigint default 0")
    private long wonGames;
    
    /* how many games the player lost over all */
    @Column( name = ITeam.LOSTGAMES, columnDefinition="bigint default 0")
    private long lostGames;
    
    /* how many offensive goals does the player have scored over all */
    @Column ( name = ITeam.SCOREDOFFENSIVEGOALS, columnDefinition="bigint default 0")
    private long scoredOffensiveGoals;
    
    /* how many defensive goals does the player have scored over all */
    @Column ( name = ITeam.SCOREDDEFENSIVEGOALS, columnDefinition="bigint default 0")
    private long scoredDefensiveGoals;
    
    /* how many goals (sum of defensive and offensive) does the 
     * player have scored over all */
    @Column ( name = ITeam.SCOREDTOTALGOALS, columnDefinition="bigint default 0")
    private long scoredTotalGoals;
    
    /* how often was the player not able to save an attempt to score a goal
     * in his role as the goalkeeper */
    @Column ( name = ITeam.CONCEDEDGOALS,  columnDefinition="bigint default 0")
    private long concededGoals;

    public Team() {}
    
    public Team(Player defensivePlayer, Player offensivePlayer) {
        setDefensivePlayer(defensivePlayer);
        setOffensivePlayer(offensivePlayer);
    }
    
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

    /**
     * @return the wonGames
     */
    @Override
    public long getWonGames() {
        return wonGames;
    }

    /**
     * @param wonGames the wonGames to set
     */
    @Override
    public void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }
    
    @Override
    public void increaseWonGames(short wonGames){
        this.wonGames += wonGames;
    }

    /**
     * @return the lostGames
     */
    @Override
    public long getLostGames() {
        return lostGames;
    }

    /**
     * @param lostGames the lostGames to set
     */
    @Override
    public void setLostGames(long lostGames) {
        this.lostGames = lostGames;
    }
    
    @Override
    public void increaseLostGames(short lostGames){
        this.lostGames += lostGames;
    }

    /**
     * @return the scoredOffensiveGoals
     */
    @Override
    public long getScoredOffensiveGoals() {
        return scoredOffensiveGoals;
    }

    /**
     * @param scoredOffensiveGoals the scoredOffensiveGoals to set
     */
    @Override
    public void setScoredOffensiveGoals(long scoredOffensiveGoals) {
        this.scoredOffensiveGoals = scoredOffensiveGoals;
    }
    
    @Override
    public void increaseScoredOffensiveGoals(short scoredOffensiveGoals){
        this.scoredOffensiveGoals += scoredOffensiveGoals;
    }

    /**
     * @return the scoredDefensiveGoals
     */
    @Override
    public long getScoredDefensiveGoals() {
        return scoredDefensiveGoals;
    }

    /**
     * @param scoredDefensiveGoals the scoredDefensiveGoals to set
     */
    @Override
    public void setScoredDefensiveGoals(long scoredDefensiveGoals) {
        this.scoredDefensiveGoals = scoredDefensiveGoals;
    }
    
    @Override
    public void increaseScoredDefensiveGoals(short scoredDefensiveGoals){
        this.scoredDefensiveGoals += scoredDefensiveGoals;
    }

    /**
     * @return the scoredTotalGoals
     */
    @Override
    public long getScoredTotalGoals() {
        return scoredTotalGoals;
    }

    /**
     * @param scoredTotalGoals the scoredTotalGoals to set
     */
    @Override
    public void setScoredTotalGoals(long scoredTotalGoals) {
        this.scoredTotalGoals = scoredTotalGoals;
    }
    
    @Override
    public void increaseScoredTotalGoals(short scoredTotalGoals){
        this.scoredTotalGoals += scoredTotalGoals;
    }

    /**
     * @return the concededGoals
     */
    @Override
    public long getConcededGoals() {
        return concededGoals;
    }

    /**
     * @param concededGoals the concededGoals to set
     */
    @Override
    public void setConcededGoals(long concededGoals) {
        this.concededGoals = concededGoals;
    }
    
    @Override
    public void increaseConcededGoals(short concededGoals){
        this.concededGoals += concededGoals;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (concededGoals ^ (concededGoals >>> 32));
        result = prime * result + ((defensivePlayer == null) ? 0 : defensivePlayer.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + (int) (lostGames ^ (lostGames >>> 32));
        result = prime * result + ((offensivePlayer == null) ? 0 : offensivePlayer.hashCode());
        result = prime * result + (int) (scoredDefensiveGoals ^ (scoredDefensiveGoals >>> 32));
        result = prime * result + (int) (scoredOffensiveGoals ^ (scoredOffensiveGoals >>> 32));
        result = prime * result + (int) (scoredTotalGoals ^ (scoredTotalGoals >>> 32));
        result = prime * result + (int) (wonGames ^ (wonGames >>> 32));
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
        if (concededGoals != other.concededGoals) {
            return false;
        }
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
        if (lostGames != other.lostGames) {
            return false;
        }
        if (offensivePlayer == null) {
            if (other.offensivePlayer != null) {
                return false;
            }
        } else if (!offensivePlayer.equals(other.offensivePlayer)) {
            return false;
        }
        if (scoredDefensiveGoals != other.scoredDefensiveGoals) {
            return false;
        }
        if (scoredOffensiveGoals != other.scoredOffensiveGoals) {
            return false;
        }
        if (scoredTotalGoals != other.scoredTotalGoals) {
            return false;
        }
        if (wonGames != other.wonGames) {
            return false;
        }
        return true;
    }


}
