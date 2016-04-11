package de.sernet.fluke.security.test;

import de.sernet.fluke.model.Team;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.Player;
import java.time.LocalDateTime;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.service.ServiceApplication;

/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplication.class)
public class GameServiceTest {

    @Autowired
    IPlayerService playerService;
    
    @Autowired
    ITeamService teamService;
    
    @Autowired
    IGameService gameService;
    
    
    public GameServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createGame() {
        Player sh = new Player();
        sh.setFirstName("Sebastian");
        sh = playerService.save(sh);
        Player bw = new Player();
        bw.setFirstName("Benjamin");
        bw = playerService.save(bw);
        Player dm = new Player();
        dm.setFirstName("Daniel");
        dm = playerService.save(dm);
        Player mr = new Player();
        mr.setFirstName("Moritz");
        mr = playerService.save(mr);
        
        Team blue = new Team((Player)sh, (Player)bw);
        blue = teamService.save(blue);
        Team red = new Team((Player)dm, (Player)mr);
        red = teamService.save(red);
        
        Game game = new Game();
        game.setBlueTeam(blue);
        game.setRedTeam(red);
        game.setGameDate(LocalDateTime.now());
        game = gameService.save(game);       
    }
}
