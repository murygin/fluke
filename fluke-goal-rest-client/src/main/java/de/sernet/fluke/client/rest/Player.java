package de.sernet.fluke.client.rest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.sernet.fluke.interfaces.IPlayer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements IPlayer  {

	private long id;
    
    private String firstName;
    private String lastName;
    
    private long wonGames;
    
    private long lostGames;
    
    private long scoredOffensiveGoals;
    
    private long scoredDefensiveGoals;
    
    private long scoredTotalGoals;
    
    private long concededGoals;
    
    
    public Player(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Player() {
    }

    /*
     * (non-Javadoc)
     *
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {    
        return getFirstName() + " " + getLastName();
    }

    @Override
    public long getWonGames() {
        return wonGames;
    }

    @Override
    public void setWonGames(long wonGames) {
        this.wonGames = wonGames;
    }

    @Override
    public void increaseWonGames(short wonGames) {
        this.wonGames += wonGames;
    }

    @Override
    public void increaseLostGames(short lostGames) {
        this.lostGames += lostGames;
    }

    @Override
    public long getLostGames() {
        return lostGames;
    }

    @Override
    public void setLostGames(long lostGames) {
        this.lostGames = lostGames;
    }

    @Override
    public long getScoredOffensiveGoals() {
        return scoredOffensiveGoals;
    }

    @Override
    public void setScoredOffensiveGoals(long scoredOffensiveGoals) {
        this.scoredOffensiveGoals = scoredOffensiveGoals;
    }

    @Override
    public void increaseScoredOffensiveGoals(short scoredOffensiveGoals) {
        this.scoredOffensiveGoals += scoredOffensiveGoals;
    }

    @Override
    public long getScoredDefensiveGoals() {
        return scoredDefensiveGoals;
    }

    @Override
    public void setScoredDefensiveGoals(long scoredDefensiveGoals) {
        this.scoredDefensiveGoals = scoredDefensiveGoals;
    }

    @Override
    public void increaseScoredDefensiveGoals(short scoredDefensiveGoals) {
        this.scoredDefensiveGoals += scoredOffensiveGoals;
    }

    @Override
    public long getScoredTotalGoals() {
        return this.scoredTotalGoals;
    }

    @Override
    public void setScoredTotalGoals(long scoredTotalGoals) {
        this.scoredTotalGoals = scoredTotalGoals;
    }

    @Override
    public void increaseScoredTotalGoals(short scoredTotalGoals) {
        this.scoredTotalGoals += scoredTotalGoals;
    }

    @Override
    public long getConcededGoals() {
        return this.concededGoals;
    }

    @Override
    public void setConcededGoals(long concededGoals) {
        this.concededGoals = concededGoals;
    }

    @Override
    public void increaseConcededGoals(short concededGoals) {
        this.concededGoals += concededGoals;
    }
}
