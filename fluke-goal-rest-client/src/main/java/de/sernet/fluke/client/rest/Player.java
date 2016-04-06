package de.sernet.fluke.client.rest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.sernet.fluke.interfaces.IPlayer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements IPlayer  {

	private long id;
    
    private String firstName;
    private String lastName;

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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setWonGames(long wonGames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseWonGames(short wonGames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseLostGames(short lostGames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getLostGames() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLostGames(long lostGames) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getScoredOffensiveGoals() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setScoredOffensiveGoals(long scoredOffensiveGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseScoredOffensiveGoals(short scoredOffensiveGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getScoredDefensiveGoals() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setScoredDefensiveGoals(long scoredDefensiveGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseScoredDefensiveGoals(short scoredDefensiveGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getScoredTotalGoals() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setScoredTotalGoals(long scoredTotalGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseScoredTotalGoals(short scoredTotalGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long getConcededGoals() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setConcededGoals(long concededGoals) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void increaseConcededGoals(short concededGoals) {
        // TODO Auto-generated method stub
        
    }
}
