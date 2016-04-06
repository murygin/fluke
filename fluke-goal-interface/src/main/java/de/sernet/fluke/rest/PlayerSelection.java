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
 * POJO to save a selection of players to create a new game.
 * This class is used as a POST parameter for the REST service.
 * 
 * @author Daniel Murygin
 */
public class PlayerSelection {
    
    private Long redOffensiveId;
    private Long redDefensiveId;
    private Long blueOffensiveId;
    private Long blueDefensiveId;

    public PlayerSelection() {
        super();
    }
    
    public PlayerSelection(Long redOffensiveId, Long redDefensiveId, Long blueOffensiveId, Long blueDefensiveId) {
        super();
        this.redOffensiveId = redOffensiveId;
        this.redDefensiveId = redDefensiveId;
        this.blueOffensiveId = blueOffensiveId;
        this.blueDefensiveId = blueDefensiveId;
    }

    public Long getRedOffensiveId() {
        return redOffensiveId;
    }

    public void setRedOffensiveId(Long redOffensiveId) {
        this.redOffensiveId = redOffensiveId;
    }

    public Long getRedDefensiveId() {
        return redDefensiveId;
    }

    public void setRedDefensiveId(Long redDefensiveId) {
        this.redDefensiveId = redDefensiveId;
    }

    public Long getBlueOffensiveId() {
        return blueOffensiveId;
    }

    public void setBlueOffensiveId(Long blueOffensiveId) {
        this.blueOffensiveId = blueOffensiveId;
    }

    public Long getBlueDefensiveId() {
        return blueDefensiveId;
    }

    public void setBlueDefensiveId(Long blueDefensiveId) {
        this.blueDefensiveId = blueDefensiveId;
    }
    
    
}
