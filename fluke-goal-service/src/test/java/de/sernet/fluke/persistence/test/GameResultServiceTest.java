package de.sernet.fluke.persistence.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.persistence.Game;

import de.sernet.fluke.persistence.PersistenceApplication;
import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.Team;
import de.sernet.fluke.service.GameResultService;
import de.sernet.fluke.service.GameService;
import de.sernet.fluke.service.PlayerService;
import de.sernet.fluke.service.ServiceApplication;
import de.sernet.fluke.service.TeamService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ServiceApplication.class)
public class GameResultServiceTest {
    
    @Autowired
    GameResultService gameResultService;
    
    @Autowired
    GameService gameService;
    
    @Autowired
    PlayerService playerService;
    
    @Autowired
    TeamService teamService;
    
    @Before
    public void init() {
       
    }
    
    @Test
    public void test() {
        ITeam teamRed = new Team();
        ITeam teamBlue = new Team();
        
        IPlayer teamRedOffense = new Player();
        teamRedOffense.setFirstName("Homer");
        teamRedOffense.setLastName("Simpson");
        playerService.save(teamRedOffense);
        
        IPlayer teamRedDefense = new Player();
        teamRedDefense.setFirstName("Bart");
        teamRedDefense.setLastName("Simpson");
        playerService.save(teamRedDefense);

        
        IPlayer teamBlueOffense = new Player();
        teamBlueOffense.setFirstName("Marge");
        teamBlueOffense.setLastName("Simpson");
        playerService.save(teamBlueOffense);
        
        IPlayer teamBlueDefense = new Player();
        teamBlueDefense.setFirstName("Lisa");
        teamBlueDefense.setLastName("Simpson");
        playerService.save(teamBlueDefense);
        
        teamRed.setOffensivePlayer(teamRedOffense);
        teamRed.setDefensivePlayer(teamRedDefense);
        teamService.save(teamRed);
        
        teamBlue.setOffensivePlayer(teamBlueOffense);
        teamBlue.setDefensivePlayer(teamBlueDefense);
        teamService.save(teamBlue);

        Game game = new Game(teamRed, teamBlue);
        
        
        gameService.save(game);
        long id = game.getId();
        
        gameResultService.trackGameResult(game, (short)4, (short)2, (short)2, (short)2);
        
        IGame persistantGame = gameService.findGame(id);
        Assert.assertEquals(persistantGame.getGameDate(), game.getGameDate());
        Assert.assertEquals(persistantGame.getResult().getBlueTeamGoals(), game.getResult().getBlueTeamGoals());
        Assert.assertEquals(persistantGame.getResult().getRedTeamGoals(), game.getResult().getRedTeamGoals());
        Assert.assertEquals(4, persistantGame.getResult().getRedScoredOffensiveGoals());
        Assert.assertEquals(2, persistantGame.getResult().getRedScoredDefensiveGoals());
        Assert.assertEquals(2, persistantGame.getResult().getBlueScoredDefensiveGoals());
        Assert.assertEquals(2, persistantGame.getResult().getBlueScoredDefensiveGoals());
        Assert.assertEquals(persistantGame.getBlueTeam(), game.getBlueTeam());
        Assert.assertEquals(persistantGame.getRedTeam(), game.getRedTeam());
    }

}
