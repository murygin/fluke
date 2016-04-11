package de.sernet.fluke.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.sernet.fluke.interfaces.IGameResultService;
import de.sernet.fluke.model.GameResult;

@RestController
@RequestMapping("/service/gameResult")
public class GameResultRestService {
    
    @Autowired
    IGameResultService gameResultService;
    
    @RequestMapping(path = "/{gameResultId}", method = RequestMethod.POST)
    public ResponseEntity<GameResult> save(@RequestBody GameResult iGameResult) {
        GameResult savedGameResult = gameResultService.save(iGameResult);
        ResponseEntity<GameResult> response = new ResponseEntity<>(savedGameResult, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(path = "/trackGameResult", method = RequestMethod.POST)
    public ResponseEntity<GameResult> trackGameResult(@RequestBody GoalsOfAGameCollection goals){
        GameResult gameResult = null;
        if(goals.getBlueScoredDefensiveGoals() != null && goals.getBlueScoredOffensiveGoals() != null 
                && goals.getRedScoredDefensiveGoals() != null && goals.getRedScoredOffensiveGoals() != null){
            gameResult = gameResultService.trackGameResult(goals.getGameId(), goals.getRedScoredOffensiveGoals(), goals.getRedScoredDefensiveGoals(), goals.getBlueScoredOffensiveGoals(), goals.getBlueScoredDefensiveGoals());
        } else if(goals.getBlueTeamGoals() != null && goals.getRedTeamGoals() != null){
            gameResult = gameResultService.trackGameResult(goals.getGameId(), goals.getRedTeamGoals(), goals.getBlueTeamGoals());
        }
        ResponseEntity<GameResult> response = new ResponseEntity<>(gameResult, HttpStatus.OK);
        return response;        
    }

}
