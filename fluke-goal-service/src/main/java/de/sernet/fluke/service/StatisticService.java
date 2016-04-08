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
package de.sernet.fluke.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.interfaces.IStatisticService;
import de.sernet.fluke.interfaces.ITeam;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@Service
public class StatisticService implements IStatisticService {

    @Autowired
    IPlayerService playerService;
    
    @Autowired
    IGameService gameService;
    
    @Autowired 
    IGameResultService gameResultService; 
    
    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IStatisticService#getPlayerRanking()
     */
    @Override
    public LinkedList<IPlayer> getPlayerRanking() {
        IPlayer[] players = playerService.findAll();
        return null;
    }

    /* (non-Javadoc)
     * @see de.sernet.fluke.interfaces.IStatisticService#getTeamRanking()
     */
    @Override
    public LinkedList<ITeam> getTeamRanking() {
        // TODO Auto-generated method stub
        return null;
    }

}
