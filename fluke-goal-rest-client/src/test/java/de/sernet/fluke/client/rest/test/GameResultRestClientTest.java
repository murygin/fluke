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
package de.sernet.fluke.client.rest.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

/**
 * @author Sebastian Hagedorn <sh[at]sernet[dot]de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Ignore
public class GameResultRestClientTest {
    
    @Autowired 
    GameResultRestClient gameResultRestClient;
    
    @Autowired
    GameRestClient gameRestClient;
    
    @Before
    public void init() {
        gameResultRestClient.setServerUrl("http://localhost:8080/service/");
        gameResultRestClient.setPath("gameResult");
    }
    
    @Test
    public void test() {
        
        long gameId = 1;
        
        Game game = gameRestClient.findById(gameId);
        
        gameResultRestClient.trackGameResult(new GoalsOfAGameCollection(gameId, (short)4, (short)6));
        
        Game updatedGame = gameRestClient.findById(gameId);
        
        Assert.assertEquals(game.getId(), updatedGame.getId());
        Assert.assertEquals(4, updatedGame.getResult().getRedTeamGoals());
        Assert.assertEquals(6, updatedGame.getResult().getBlueTeamGoals());
        
        
//        IGameResult gameResult = new GameResult((short)6, (short)6);
//        gameResult = gameResultRestClient.save(gameResult);
        
    }

}
