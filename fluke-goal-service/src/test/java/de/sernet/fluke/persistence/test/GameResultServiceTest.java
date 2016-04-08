package de.sernet.fluke.persistence.test;

import de.sernet.fluke.model.Team;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.Player;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.persistence.*;
import de.sernet.fluke.service.*;

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
    
    private ITeam teamRed;
    private ITeam teamBlue;
    
    private IPlayer teamRedOffense;
    private IPlayer teamRedDefense;
    
    private IPlayer teamBlueOffense;
    private IPlayer teamBlueDefense;
    private Game game;
    
    @Before
    public void init() {
        teamRed = new Team();
        teamBlue = new Team();
        
        teamRedOffense = new Player();
        teamRedOffense.setFirstName("Homer");
        teamRedOffense.setLastName("Simpson");
        playerService.save(teamRedOffense);
        
        teamRedDefense = new Player();
        teamRedDefense.setFirstName("Bart");
        teamRedDefense.setLastName("Simpson");
        playerService.save(teamRedDefense);

        teamBlueOffense = new Player();
        teamBlueOffense.setFirstName("Marge");
        teamBlueOffense.setLastName("Simpson");
        playerService.save(teamBlueOffense);
        
        teamBlueDefense = new Player();
        teamBlueDefense.setFirstName("Lisa");
        teamBlueDefense.setLastName("Simpson");
        playerService.save(teamBlueDefense);
        
        teamRed.setOffensivePlayer(teamRedOffense);
        teamRed.setDefensivePlayer(teamRedDefense);
        teamService.save(teamRed);
        
        teamBlue.setOffensivePlayer(teamBlueOffense);
        teamBlue.setDefensivePlayer(teamBlueDefense);
        teamService.save(teamBlue);
       
    }
    
    @Test 
    public void testTrackGame(){
        game = new Game(teamBlue, teamRed);
        gameService.save(game);
        
        long id  = game.getId();
        
        gameResultService.trackGameResult(game, (short)4, (short)6);
        
        IGame persistantGame = gameService.findById(id);
        Assert.assertEquals(persistantGame.getGameDate(), game.getGameDate());
        Assert.assertEquals(persistantGame.getResult().getBlueTeamGoals(), game.getResult().getBlueTeamGoals());
        Assert.assertEquals(persistantGame.getResult().getRedTeamGoals(), game.getResult().getRedTeamGoals());
        Assert.assertEquals(6, game.getResult().getBlueTeamGoals());
        Assert.assertEquals(4, game.getResult().getRedTeamGoals());
    }
    
    @Test
    public void testTrackGameDetailed() {
        game = new Game(teamRed, teamBlue);
        gameService.save(game);
        long id = game.getId();
        
        gameResultService.trackGameResult(game, (short)4, (short)2, (short)2, (short)2);
        
        IGame persistantGame = gameService.findById(id);
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
