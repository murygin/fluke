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
package de.sernet.fluke.interfaces;

import java.time.LocalDateTime;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 * @author Benjamin Weißenfels <bw@sernet.de>
 */
public interface IGame {
    
    void setId(long id);
    
    long getId();
    
    ITeam getBlueTeam();
    
    ITeam getRedTeam();
    
    void setBlueTeam(ITeam blueTeam);
    
    void setRedTeam(ITeam redTeam);
    
    LocalDateTime getGameDate();
    
    void setGameDate(LocalDateTime gameDate);
    
    void setResult(IGameResult gameResult);
    
    IGameResult getResult();

}
