package de.sernet.fluke.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.sernet.fluke.interfaces.IPlayer;

@Entity
public class Player implements IPlayer  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
    
    @Column ( name = "firstName", nullable=false)
    private String firstName;
    
    @Column ( name = "lastName")
    private String lastName;
    
    /* how many games the player won over all */
    @Column( name = "wonGames", columnDefinition="bigint default 0")
    private long wonGames;
    
    /* how many games the player lost over all */
    @Column( name = "lostGames", columnDefinition="bigint default 0")
    private long lostGames;
    
    /* how many offensive goals does the player have scored over all */
    @Column ( name = "scoredOffensiveGoals", columnDefinition="bigint default 0")
    private long scoredOffensiveGoals;
    
    /* how many defensive goals does the player have scored over all */
    @Column ( name = "scoredDefensiveGoals", columnDefinition="bigint default 0")
    private long scoredDefensiveGoals;
    
    /* how many goals (sum of defensive and offensive) does the 
     * player have scored over all */
    @Column ( name = "scoredTotalGoals", columnDefinition="bigint default 0")
    private long scoredTotalGoals;
    
    /* how often was the player not able to save an attempt to score a goal
     * in his role as the goalkeeper */
    @Column ( name = "concededGoals",  columnDefinition="bigint default 0")
    private long concededGoals;
    
	/* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getId()
     */
	@Override
    public long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setId(long)
     */
    @Override
    public void setId(long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getFirstName()
     */
    @Override
    public String getFirstName() {
		return firstName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setFirstName(java.lang.String)
     */
    @Override
    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#getLastName()
     */
    @Override
    public String getLastName() {
		return lastName;
	}

    /* (non-Javadoc)
     * @see de.sernet.fluke.persistence.IPlayer#setLastName(java.lang.String)
     */
    @Override
    public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
    /**
     * @return the wonGames
     */
    public long getWonGames() {
        return wonGames;
    }

    /**
     * @param wonGames the wonGames to set
     */
    public void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }
    
    public void increaseWonGames(){
        this.wonGames += 1;
    }
    
    public void increaseLostGames(){
        this.lostGames += 1;
    }

    /**
     * @return the lostGames
     */
    public long getLostGames() {
        return lostGames;
    }

    /**
     * @param lostGames the lostGames to set
     */
    public void setLostGames(long lostGames) {
        this.lostGames = lostGames;
    }

    /**
     * @return the scoredOffensiveGoals
     */
    public long getScoredOffensiveGoals() {
        return scoredOffensiveGoals;
    }

    /**
     * @param scoredOffensiveGoals the scoredOffensiveGoals to set
     */
    public void setScoredOffensiveGoals(long scoredOffensiveGoals) {
        this.scoredOffensiveGoals = scoredOffensiveGoals;
    }
    
    public void increaseScoredOffensiveGoals(long scoredOffensiveGoals){
        this.scoredOffensiveGoals += scoredOffensiveGoals;
    }

    /**
     * @return the scoredDefensiveGoals
     */
    public long getScoredDefensiveGoals() {
        return scoredDefensiveGoals;
    }

    /**
     * @param scoredDefensiveGoals the scoredDefensiveGoals to set
     */
    public void setScoredDefensiveGoals(long scoredDefensiveGoals) {
        this.scoredDefensiveGoals = scoredDefensiveGoals;
    }
    
    public void increaseScoredDefensiveGoals(long scoredDefensiveGoals){
        this.scoredDefensiveGoals = scoredDefensiveGoals;
    }

    /**
     * @return the scoredTotalGoals
     */
    public long getScoredTotalGoals() {
        return scoredTotalGoals;
    }

    /**
     * @param scoredTotalGoals the scoredTotalGoals to set
     */
    public void setScoredTotalGoals(long scoredTotalGoals) {
        this.scoredTotalGoals = scoredTotalGoals;
    }

    /**
     * @return the concededGoals
     */
    public long getConcededGoals() {
        return concededGoals;
    }

    /**
     * @param concededGoals the concededGoals to set
     */
    public void setConcededGoals(long concededGoals) {
        this.concededGoals = concededGoals;
    }
    
    public void increaseConcededGoals(long concededGoals){
        this.concededGoals += concededGoals;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (concededGoals ^ (concededGoals >>> 32));
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + (int) (lostGames ^ (lostGames >>> 32));
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
        Player other = (Player) obj;
        if (concededGoals != other.concededGoals) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (lostGames != other.lostGames) {
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
