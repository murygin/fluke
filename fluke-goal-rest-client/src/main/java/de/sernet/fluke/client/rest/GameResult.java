package de.sernet.fluke.client.rest;

import de.sernet.fluke.interfaces.IGameResult;

public class GameResult implements IGameResult {
    
    private long id;
    
    private short blueTeamGoals;
    private short redTeamGoals;
    
    private short redScoredOffensiveGoals;
    private short redScoredDefensiveGoals;
    
    private short blueScoredOffensiveGoals;
    private short blueScoredDefensiveGoals;
    
    public GameResult(short redTeamGoals, short blueTeamGoals){
        this.redTeamGoals = redTeamGoals;
        this.blueTeamGoals = blueTeamGoals;
    }
    
    public GameResult(){ 
        // default-constructor for hibernate
    }
    
    public GameResult(short redTeamOffensiveGoals, short redTeamDefensiveGoals, short blueTeamOffensiveGoals, short blueTeamDefensiveGoals){
        this((short) (redTeamDefensiveGoals+redTeamOffensiveGoals), (short)(blueTeamDefensiveGoals+blueTeamOffensiveGoals) );
        this.redScoredDefensiveGoals = redTeamDefensiveGoals;
        this.redScoredOffensiveGoals = redTeamOffensiveGoals;
        this.blueScoredDefensiveGoals = blueTeamDefensiveGoals;
        this.blueScoredOffensiveGoals = blueTeamOffensiveGoals;
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
    public short getBlueTeamGoals() {
        return this.blueTeamGoals;
    }

    @Override
    public short getRedTeamGoals() {
        return this.redTeamGoals;
    }

    @Override
    public void setBlueTeamGoals(short blueTeamGoals) {
        this.blueTeamGoals = blueTeamGoals;
    }

    @Override
    public void setRedTeamGoals(short redTeamGoals) {
        this.redTeamGoals = redTeamGoals;
    }

    @Override
    public short getRedScoredOffensiveGoals() {
        return this.redScoredOffensiveGoals;
    }

    @Override
    public void setRedScoredOffensiveGoals(short redScoredOffensiveGoals) {
        this.redScoredOffensiveGoals = redScoredOffensiveGoals;
    }

    @Override
    public short getRedScoredDefensiveGoals() {
        return this.redScoredDefensiveGoals;
    }

    @Override
    public void setRedScoredDefensiveGoals(short redScoredDefensiveGoals) {
        this.redScoredDefensiveGoals = redScoredDefensiveGoals;
    }

    @Override
    public short getBlueScoredOffensiveGoals() {
        return this.blueScoredOffensiveGoals;
    }

    @Override
    public void setBlueScoredOffensiveGoals(short blueScoredOffensiveGoals) {
        this.blueScoredOffensiveGoals = blueScoredOffensiveGoals;
    }

    @Override
    public short getBlueScoredDefensiveGoals() {
        return this.blueScoredDefensiveGoals;
    }

    @Override
    public void setBlueScoredDefensiveGoals(short blueScoredDefensiveGoals) {
        this.blueScoredDefensiveGoals = blueScoredDefensiveGoals;
    }

}
