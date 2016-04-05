package de.sernet.fluke.security.test;

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
import de.sernet.fluke.interfaces.IAccount;
import de.sernet.fluke.interfaces.IAccountService;
import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.interfaces.ITeamService;
import de.sernet.fluke.persistence.Account;
import de.sernet.fluke.persistence.Game;
import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.PlayerRepository;
import de.sernet.fluke.persistence.Team;
import de.sernet.fluke.security.PasswordEncoderFactory;
import de.sernet.fluke.service.ServiceApplication;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    
    
    private final PasswordEncoder encoder;

    public GameServiceTest() {
        this.encoder = PasswordEncoderFactory.getInstance();
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
        IPlayer sh = new Player();
        sh.setFirstName("Sebastian");
        sh = playerService.save(sh);
        IPlayer bw = new Player();
        bw.setFirstName("Benjamin");
        bw = playerService.save(bw);
        IPlayer dm = new Player();
        dm.setFirstName("Daniel");
        dm = playerService.save(dm);
        IPlayer mr = new Player();
        mr.setFirstName("Moritz");
        mr = playerService.save(mr);
        
        ITeam blue = new Team((Player)sh, (Player)bw);
        blue = teamService.save(blue);
        ITeam red = new Team((Player)dm, (Player)mr);
        red = teamService.save(red);
        
        IGame game = new Game();
        game.setBlueTeam(blue);
        game.setRedTeam(red);
        game.setGameDate(LocalDateTime.now());
        game = gameService.save(game);       
    }
}
