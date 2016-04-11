package de.sernet.fluke.interfaces;

import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.GameResult;
import de.sernet.fluke.rest.GoalsOfAGameCollection;

public interface IGameResultService {
    
    GameResult trackGameResult(Game game, short goalsRedTeam, short goalsBlueTeam);
   
    GameResult trackGameResult(Game game, short redOffensiveGoals, short redDefensiveGoals,
            short blueOffensiveGoals, short blueDefensiveGoals);
    
    GameResult save(GameResult gameResult);

    GameResult trackGameResult(long gameId, short goalsRedTeam, short goalsBlueTeam);
    
    GameResult trackGameResult(long gameId, short redOffensiveGoals, short redDefensiveGoals,
            short blueOffensiveGoals, short blueDefensiveGoals);

    GameResult trackGameResult(GoalsOfAGameCollection goals);

}
