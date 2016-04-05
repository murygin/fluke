package de.sernet.fluke.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameResult;
import de.sernet.fluke.interfaces.IGameResultService;

@RestController
@RequestMapping("/service/gameResult")
public class GameResultRestService {
    
    @Autowired
    IGameResultService gameResultService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IGameResult> save(@RequestBody IGameResult iGameResult) {
        IGameResult savedGameResult = gameResultService.save(iGameResult);
        ResponseEntity<IGameResult> response = new ResponseEntity<>(savedGameResult, HttpStatus.CREATED);
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IGame> trackGameResult(@RequestBody IGame game, short goalsRedTeam, short goalsBlueTeam) {
        gameResultService.trackGameResult(game, goalsRedTeam, goalsBlueTeam);
        ResponseEntity<IGame> response = new ResponseEntity<>(game, HttpStatus.OK);
        return response;
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IGame> trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals, short blueOffensiveGoals, short blueDefensiveGoals) {
        gameResultService.trackGameResult(game, redOffensiveGoals, redDefensiveGoals, blueOffensiveGoals, blueDefensiveGoals);
        ResponseEntity<IGame> response = new ResponseEntity<>(game, HttpStatus.OK);
        return response;
    }
    

}
