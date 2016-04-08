package de.sernet.fluke.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResult;
import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.persistence.GameResult;
import de.sernet.fluke.persistence.GameResultRepository;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

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
    public IGameResult trackGameResult(IGame game, short goalsRedTeam, short goalsBlueTeam) {
        IGameResult gameResult = null;
        if(game.getResult() == null){
            gameResult = new GameResult(goalsRedTeam, goalsBlueTeam);
            gameResultRepository.save((GameResult)gameResult);
            game.setResult(gameResult);
        } else {
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
        
        // update scored player goals
        game.getBlueTeam().getDefensivePlayer().increaseConcededGoals(goalsRedTeam);
        game.getRedTeam().getDefensivePlayer().increaseConcededGoals(goalsBlueTeam);
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
        
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

        playerService.save(game.getBlueTeam().getDefensivePlayer());
        playerService.save(game.getBlueTeam().getOffensivePlayer());
        
        playerService.save(game.getRedTeam().getDefensivePlayer());
        playerService.save(game.getRedTeam().getOffensivePlayer());
        
        return gameResult;
    }

    @Override
    public IGameResult trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        IGameResult gameResult = new GameResult((short)redOffensiveGoals, (short)redDefensiveGoals, (short)blueOffensiveGoals, (short)blueDefensiveGoals);
        gameResult = gameResultRepository.save((GameResult)gameResult);
        game.setResult(gameResult);
        
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
        
        return gameResult;
    }

    @Override
    public IGameResult save(IGameResult gameResult) {
        return gameResultRepository.save((GameResult)gameResult);
    }

    @Override
    public IGameResult trackGameResult(long gameId, short goalsRedTeam, short goalsBlueTeam) {
        IGame game = gameService.findById(gameId);
        return trackGameResult(game, goalsRedTeam, goalsBlueTeam);
    }

    @Override
    public IGameResult trackGameResult(long gameId, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        IGame game = gameService.findById(gameId);
        return trackGameResult(game, redOffensiveGoals, redDefensiveGoals, blueOffensiveGoals, blueDefensiveGoals);
    }

    @Override
    public IGameResult trackGameResult(GoalsOfAGameCollection goals) {
        IGameResult gameResult = null;
        if(goals.getBlueScoredDefensiveGoals() != null && goals.getBlueScoredOffensiveGoals() != null 
                && goals.getRedScoredDefensiveGoals() != null && goals.getRedScoredOffensiveGoals() != null){
            gameResult = trackGameResult(goals.getGameId(), goals.getRedScoredOffensiveGoals(), goals.getRedScoredDefensiveGoals(), goals.getBlueScoredOffensiveGoals(), goals.getBlueScoredDefensiveGoals());
        } else if(goals.getBlueTeamGoals() != null && goals.getRedTeamGoals() != null){
            gameResult = trackGameResult(goals.getGameId(), goals.getRedTeamGoals(), goals.getRedTeamGoals());
        }
        return gameResult;
    }



}
