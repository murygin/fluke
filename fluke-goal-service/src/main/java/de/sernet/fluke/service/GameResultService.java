package de.sernet.fluke.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.persistence.GameResult;
import de.sernet.fluke.persistence.GameResultRepository;

@Service
public class GameResultService implements IGameResultService {

    @Autowired
    PlayerService playerService;
    
    @Autowired
    TeamService teamService;
    
    @Autowired
    GameService gameService;
    
    @Autowired
    GameResultRepository gameResultRepository;
    
    @Override
    public void trackGameResult(IGame game, short goalsRedTeam, short goalsBlueTeam) {
        if(game.getResult() == null){
            game.setResult(new GameResult(goalsRedTeam, goalsBlueTeam));
        }
        
        gameResultRepository.save((GameResult)game.getResult());
        
        // update blue team
        game.getBlueTeam().increaseScoredTotalGoals(goalsBlueTeam);
        game.getBlueTeam().increaseConcededGoals(goalsRedTeam);

        // update red team
        game.getRedTeam().increaseScoredTotalGoals(goalsRedTeam);
        game.getRedTeam().increaseConcededGoals(goalsBlueTeam);
        
        // update game won/lost stats
        if(goalsBlueTeam > goalsRedTeam){ // blue wins
            game.getBlueTeam().increaseWonGames((short)1);
            game.getRedTeam().increaseLostGames((short)1);
        } else { // red wins
            game.getRedTeam().increaseWonGames((short)1);
            game.getBlueTeam().increaseLostGames((short)1);
        }
        
        // store everything in db (save() equals update())
        gameService.save(game);
        teamService.save(game.getBlueTeam());
        teamService.save(game.getRedTeam());
        
    }

    @Override
    public void trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        game.setResult(new GameResult((short)redOffensiveGoals, (short)redDefensiveGoals, (short)blueOffensiveGoals, (short)blueDefensiveGoals));
        
        // handle results for team objects
        trackGameResult(game, game.getResult().getRedTeamGoals(), game.getResult().getBlueTeamGoals());
        
        
        // handle results for player objects
        game.getBlueTeam().getOffensivePlayer().increaseScoredOffensiveGoals(blueOffensiveGoals);
        game.getBlueTeam().getDefensivePlayer().increaseScoredDefensiveGoals(blueDefensiveGoals);
        game.getBlueTeam().getDefensivePlayer().increaseConcededGoals((short) (redOffensiveGoals+redDefensiveGoals));
        
        game.getRedTeam().getOffensivePlayer().increaseScoredOffensiveGoals(redOffensiveGoals);
        game.getRedTeam().getDefensivePlayer().increaseScoredDefensiveGoals(redDefensiveGoals);
        game.getRedTeam().getDefensivePlayer().increaseConcededGoals((short) (blueOffensiveGoals+blueDefensiveGoals));
        
        if(game.getResult().getBlueTeamGoals() > game.getResult().getRedTeamGoals()){ // blue wins
            game.getBlueTeam().getOffensivePlayer().increaseWonGames((short)1);
            game.getBlueTeam().getDefensivePlayer().increaseWonGames((short)1);
            game.getRedTeam().getOffensivePlayer().increaseLostGames((short)1);
            game.getRedTeam().getDefensivePlayer().increaseLostGames((short)1);            
        } else { // red wins
            game.getRedTeam().getOffensivePlayer().increaseWonGames((short)1);
            game.getRedTeam().getDefensivePlayer().increaseWonGames((short)1);
            game.getBlueTeam().getOffensivePlayer().increaseLostGames((short)1);
            game.getBlueTeam().getDefensivePlayer().increaseLostGames((short)1);
        }
        
        gameService.save(game);
        
        playerService.save(game.getBlueTeam().getDefensivePlayer());
        playerService.save(game.getBlueTeam().getOffensivePlayer());
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
    }



}
