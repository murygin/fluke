package de.sernet.fluke.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String WONGAMES = "wonGames";
    public static final String LOSTGAMES = "lostGames";
    public static final String SCOREDOFFENSIVEGOALS = "scoredOffensiveGoals";
    public static final String SCOREDDEFENSIVEGOALS = "scoredDefensiveGoals";
    public static final String SCOREDTOTALGOALS = "scoredTotalGoals";
    public static final String CONCEDEDGOALS = "concededGoals";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = Player.FIRSTNAME, nullable = false)
    private String firstName;

    @Column(name = Player.LASTNAME)
    private String lastName;

    /* how many games the player won over all */
    @Column(name = Player.WONGAMES, columnDefinition = "bigint default 0")
    private long wonGames;

    /* how many games the player lost over all */
    @Column(name = Player.LOSTGAMES, columnDefinition = "bigint default 0")
    private long lostGames;

    /* how many offensive goals does the player have scored over all */
    @Column(name = Player.SCOREDOFFENSIVEGOALS, columnDefinition = "bigint default 0")
    private long scoredOffensiveGoals;

    /* how many defensive goals does the player have scored over all */
    @Column(name = Player.SCOREDDEFENSIVEGOALS, columnDefinition = "bigint default 0")
    private long scoredDefensiveGoals;

    /* how many goals (sum of defensive and offensive) does the 
     * player have scored over all */
    @Column(name = Player.SCOREDTOTALGOALS, columnDefinition = "bigint default 0")
    private long scoredTotalGoals;

    /* how often was the player not able to save an attempt to score a goal
     * in his role as the goalkeeper */
    @Column(name = Player.CONCEDEDGOALS, columnDefinition = "bigint default 0")
    private long concededGoals;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getWonGames() {
        return wonGames;
    }

    public void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }

    public void increaseWonGames(short wonGames) {
        this.wonGames += wonGames;
    }

    public void increaseLostGames(short lostGames) {
        this.lostGames += lostGames;
    }

    public long getLostGames() {
        return lostGames;
    }

    public void setLostGames(long lostGames) {
        this.lostGames = lostGames;
    }

    public long getScoredOffensiveGoals() {
        return scoredOffensiveGoals;
    }

    public void setScoredOffensiveGoals(long scoredOffensiveGoals) {
        this.scoredOffensiveGoals = scoredOffensiveGoals;
    }

    public void increaseScoredOffensiveGoals(short scoredOffensiveGoals) {
        this.scoredOffensiveGoals += scoredOffensiveGoals;
    }

    public long getScoredDefensiveGoals() {
        return scoredDefensiveGoals;
    }

    public void setScoredDefensiveGoals(long scoredDefensiveGoals) {
        this.scoredDefensiveGoals = scoredDefensiveGoals;
    }

    public void increaseScoredDefensiveGoals(short scoredDefensiveGoals) {
        this.scoredDefensiveGoals += scoredDefensiveGoals;
    }

    public long getScoredTotalGoals() {
        return scoredTotalGoals;
    }

    public void setScoredTotalGoals(long scoredTotalGoals) {
        this.scoredTotalGoals = scoredTotalGoals;
    }

    public void increaseScoredTotalGoals(short scoredTotalGoals) {
        this.scoredTotalGoals += scoredTotalGoals;
    }

    public long getConcededGoals() {
        return concededGoals;
    }

    public void setConcededGoals(long concededGoals) {
        this.concededGoals = concededGoals;
    }

    public void increaseConcededGoals(short concededGoals) {
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
