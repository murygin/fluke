package de.sernet.fluke.interfaces;

public interface IGameResultService {
    
    void trackGameResult(IGame game, short goalsRedTeam, short goalsBlueTeam);
    
    void trackGameResult(IGame game, short redOffensiveGoals, short redDefensiveGoals,
            short blueOffensiveGoals, short blueDefensiveGoals);
    
}
