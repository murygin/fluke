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

import de.sernet.fluke.client.rest.Application;
import de.sernet.fluke.client.rest.Game;
import de.sernet.fluke.client.rest.GameRestClient;
import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.rest.PlayerSelection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class GameRestClientTest {
    
    @Autowired
    GameRestClient restClient;
    
    @Test
    public void test() {
        Game newGame = restClient.create(
                Long.valueOf(1), 
                Long.valueOf(2), 
                Long.valueOf(3), 
                Long.valueOf(4)
        );
        Assert.assertNotNull(newGame);
    }
    
}
