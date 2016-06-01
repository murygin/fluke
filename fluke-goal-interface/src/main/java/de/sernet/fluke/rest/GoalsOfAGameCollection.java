/*
 * Copyright 2016 SerNet Service Network GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.sernet.fluke.rest;

/**
 * wraps information needed to construct an instance of GameResult
 * This class is used as a POST parameter for the REST service.
 * 
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
public class GoalsOfAGameCollection {
    
    public static final short MAX_GOALS = 6;
    
    private Long gameId;
    
    private Short redTeamGoals;
    private Short blueTeamGoals;
    
    private Short redScoredOffensiveGoals;
    private Short redScoredDefensiveGoals;
    
    private Short blueScoredOffensiveGoals;
    private Short blueScoredDefensiveGoals;
    
    public GoalsOfAGameCollection(){
        super();
    }
    
    public GoalsOfAGameCollection(long gameId, short redTeamGoals, short blueTeamGoals){
        this.gameId = Long.valueOf(gameId);
        this.redTeamGoals = Short.valueOf(redTeamGoals);
        this.blueTeamGoals = Short.valueOf(blueTeamGoals);
    }
    
    public GoalsOfAGameCollection(long gameId, short blueScoredOffensiveGoals, short blueScoredDefensiveGoals,
            short redScoredOffensiveGoals, short redScoredDefensiveGoals){
        this.gameId = Long.valueOf(gameId);
        this.redScoredDefensiveGoals = Short.valueOf(redScoredDefensiveGoals);
        this.redScoredOffensiveGoals = Short.valueOf(redScoredOffensiveGoals);
        this.blueScoredDefensiveGoals = Short.valueOf(blueScoredDefensiveGoals);
        this.blueScoredOffensiveGoals = Short.valueOf(blueScoredOffensiveGoals);
        
    }
    
    public boolean isValidPlayerCollection() {
        boolean allNotNull = 
                redScoredOffensiveGoals!=null &&
                redScoredDefensiveGoals!=null &&
                blueScoredOffensiveGoals!=null && 
                blueScoredDefensiveGoals!=null;
        boolean allNumbersValid =
                isNumberOfGoalsValid(redScoredOffensiveGoals) &&
                isNumberOfGoalsValid(redScoredDefensiveGoals) &&
                isNumberOfGoalsValid(blueScoredOffensiveGoals) &&
                isNumberOfGoalsValid(blueScoredDefensiveGoals);
         return allNotNull && allNumbersValid;
    }
    
    

    private boolean isNumberOfGoalsValid(Short numberOfGoals) {
        if(numberOfGoals==null) {
            return false;
        }
        return numberOfGoals >= 0 && numberOfGoals <= MAX_GOALS;
    }

    /**
     * @return the gameId
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     * @param gameId the gameId to set
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * @return the redTeamGoals
     */
    public Short getRedTeamGoals() {
        return redTeamGoals;
    }

    /**
     * @param redTeamGoals the redTeamGoals to set
     */
    public void setRedTeamGoals(Short redTeamGoals) {
        this.redTeamGoals = redTeamGoals;
    }

    /**
     * @return the blueTeamGoals
     */
    public Short getBlueTeamGoals() {
        return blueTeamGoals;
    }

    /**
     * @param blueTeamGoals the blueTeamGoals to set
     */
    public void setBlueTeamGoals(Short blueTeamGoals) {
        this.blueTeamGoals = blueTeamGoals;
    }

    /**
     * @return the redScoredOffensiveGoals
     */
    public Short getRedScoredOffensiveGoals() {
        return redScoredOffensiveGoals;
    }

    /**
     * @param redScoredOffensiveGoals the redScoredOffensiveGoals to set
     */
    public void setRedScoredOffensiveGoals(Short redScoredOffensiveGoals) {
        this.redScoredOffensiveGoals = redScoredOffensiveGoals;
    }

    /**
     * @return the redScoredDefensiveGoals
     */
    public Short getRedScoredDefensiveGoals() {
        return redScoredDefensiveGoals;
    }

    /**
     * @param redScoredDefensiveGoals the redScoredDefensiveGoals to set
     */
    public void setRedScoredDefensiveGoals(Short redScoredDefensiveGoals) {
        this.redScoredDefensiveGoals = redScoredDefensiveGoals;
    }

    /**
     * @return the blueScoredOffensiveGoals
     */
    public Short getBlueScoredOffensiveGoals() {
        return blueScoredOffensiveGoals;
    }

    /**
     * @param blueScoredOffensiveGoals the blueScoredOffensiveGoals to set
     */
    public void setBlueScoredOffensiveGoals(Short blueScoredOffensiveGoals) {
        this.blueScoredOffensiveGoals = blueScoredOffensiveGoals;
    }

    /**
     * @return the blueScoredDefensiveGoals
     */
    public Short getBlueScoredDefensiveGoals() {
        return blueScoredDefensiveGoals;
    }

    /**
     * @param blueScoredDefensiveGoals the blueScoredDefensiveGoals to set
     */
    public void setBlueScoredDefensiveGoals(Short blueScoredDefensiveGoals) {
        this.blueScoredDefensiveGoals = blueScoredDefensiveGoals;
    }
}
