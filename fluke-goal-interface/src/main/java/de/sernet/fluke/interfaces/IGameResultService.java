package de.sernet.fluke.interfaces;

import de.sernet.fluke.rest.GoalsOfAGameCollection;

public interface IGameResultService {
    
    IGameResult trackGameResult(IGame game, short goalsRedTeam, short goalsBlueTeam);
    
    IGameResult trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals,
            short blueOffensiveGoals, short blueDefensiveGoals);
    
    IGameResult save(IGameResult gameResult);

    IGameResult trackGameResult(long gameId, short goalsRedTeam, short goalsBlueTeam);
    
    IGameResult trackGameResult(long gameId, short redOffensiveGoals, short redDefensiveGoals,
            short blueOffensiveGoals, short blueDefensiveGoals);

    IGameResult trackGameResult(GoalsOfAGameCollection goals);

}
