package de.sernet.fluke.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.ITeamService;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.GameResult;
import de.sernet.fluke.persistence.GameResultRepository;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

@Service
public class GameResultService implements IGameResultService {

    @Autowired
    PlayerService playerService;
    
    @Autowired
    ITeamService teamService;
    
    @Autowired
    IGameService gameService;
    
    @Autowired
    GameResultRepository gameResultRepository;
    
    @Override
    public GameResult trackGameResult(Game game, short goalsRedTeam, short goalsBlueTeam) {
        GameResult gameResult = null;
        if(game.getResult() == null){
            // method called directly from client
            gameResult = new GameResult(goalsRedTeam, goalsBlueTeam);
            gameResultRepository.save((GameResult)gameResult);
            game.setResult(gameResult);
            // update scored player goals
            game.getBlueTeam().getDefensivePlayer().increaseConcededGoals(goalsRedTeam);
            game.getRedTeam().getDefensivePlayer().increaseConcededGoals(goalsBlueTeam);
        } else {
            // method calles indirectly (via trackGameResult(GoalsOfAGameCollection goals) )
            gameResult = game.getResult();
            gameResult.setBlueTeamGoals(goalsBlueTeam);
            gameResult.setRedTeamGoals(goalsRedTeam);
            gameResultRepository.save((GameResult)gameResult);
            game.setResult(gameResult);
        }
        
        // update blue team
        game.getBlueTeam().increaseScoredTotalGoals(goalsBlueTeam);
        game.getBlueTeam().increaseConcededGoals(goalsRedTeam);

        // update red team
        game.getRedTeam().increaseScoredTotalGoals(goalsRedTeam);
        game.getRedTeam().increaseConcededGoals(goalsBlueTeam);
        
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
        
        // update game won/lost stats
        if(goalsBlueTeam > goalsRedTeam){ // blue wins
            game.getBlueTeam().increaseWonGames((short)1);
            game.getRedTeam().increaseLostGames((short)1);
            game.getBlueTeam().getOffensivePlayer().increaseWonGames((short)1);
            game.getBlueTeam().getDefensivePlayer().increaseWonGames((short)1);
            game.getRedTeam().getOffensivePlayer().increaseLostGames((short)1);
            game.getRedTeam().getDefensivePlayer().increaseLostGames((short)1);            
        } else { // red wins
            game.getRedTeam().increaseWonGames((short)1);
            game.getBlueTeam().increaseLostGames((short)1);
            game.getRedTeam().getOffensivePlayer().increaseWonGames((short)1);
            game.getRedTeam().getDefensivePlayer().increaseWonGames((short)1);
            game.getBlueTeam().getOffensivePlayer().increaseLostGames((short)1);
            game.getBlueTeam().getDefensivePlayer().increaseLostGames((short)1);

        }
        
        // store everything in db (save() equals update())
        gameService.save(game);
        teamService.save(game.getBlueTeam());
        teamService.save(game.getRedTeam());

        playerService.save(game.getBlueTeam().getDefensivePlayer());
        playerService.save(game.getBlueTeam().getOffensivePlayer());
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
        
        return gameResult;
    }

    @Override
    public GameResult trackGameResult(Game game, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        GameResult gameResult = new GameResult((short)redOffensiveGoals, (short)redDefensiveGoals, (short)blueOffensiveGoals, (short)blueDefensiveGoals);
        gameResult = gameResultRepository.save((GameResult)gameResult);
        game.setResult(gameResult);
        
        // handle results for team and player objects
        trackGameResult(game, game.getResult().getRedTeamGoals(), game.getResult().getBlueTeamGoals());
        
        
        // handle (detailed) results for player objects
        game.getBlueTeam().getOffensivePlayer().increaseScoredOffensiveGoals(blueOffensiveGoals);
        game.getBlueTeam().getOffensivePlayer().increaseScoredTotalGoals(blueOffensiveGoals);
        
        game.getBlueTeam().getDefensivePlayer().increaseScoredDefensiveGoals(blueDefensiveGoals);
        game.getBlueTeam().getDefensivePlayer().increaseScoredTotalGoals(blueDefensiveGoals);
        
        game.getBlueTeam().getDefensivePlayer().increaseConcededGoals((short) (redOffensiveGoals+redDefensiveGoals));
        
        game.getRedTeam().getOffensivePlayer().increaseScoredOffensiveGoals(redOffensiveGoals);
        game.getRedTeam().getOffensivePlayer().increaseScoredTotalGoals(redOffensiveGoals);
        
        game.getRedTeam().getDefensivePlayer().increaseScoredDefensiveGoals(redDefensiveGoals);
        game.getRedTeam().getDefensivePlayer().increaseScoredTotalGoals(redDefensiveGoals);
        
        game.getRedTeam().getDefensivePlayer().increaseConcededGoals((short) (blueOffensiveGoals+blueDefensiveGoals));
        
        gameService.save(game);
        
        playerService.save(game.getBlueTeam().getDefensivePlayer());
        playerService.save(game.getBlueTeam().getOffensivePlayer());
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
        
        return gameResult;
    }

    @Override
    public GameResult save(GameResult gameResult) {
        return gameResultRepository.save((GameResult)gameResult);
    }

    @Override
    public GameResult trackGameResult(long gameId, short goalsRedTeam, short goalsBlueTeam) {
        Game game = gameService.findById(gameId);
        return trackGameResult(game, goalsRedTeam, goalsBlueTeam);
    }

    @Override
    public GameResult trackGameResult(long gameId, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        Game game = gameService.findById(gameId);
        return trackGameResult(game, redOffensiveGoals, redDefensiveGoals, blueOffensiveGoals, blueDefensiveGoals);
    }

    @Override
    public GameResult trackGameResult(GoalsOfAGameCollection goals) {
        GameResult gameResult = null;
        if(goals.getBlueScoredDefensiveGoals() != null && goals.getBlueScoredOffensiveGoals() != null 
                && goals.getRedScoredDefensiveGoals() != null && goals.getRedScoredOffensiveGoals() != null){
            gameResult = trackGameResult(goals.getGameId(), goals.getRedScoredOffensiveGoals(), goals.getRedScoredDefensiveGoals(), goals.getBlueScoredOffensiveGoals(), goals.getBlueScoredDefensiveGoals());
        } else if(goals.getBlueTeamGoals() != null && goals.getRedTeamGoals() != null){
            gameResult = trackGameResult(goals.getGameId(), goals.getRedTeamGoals(), goals.getRedTeamGoals());
        }
        return gameResult;
    }



}
