package de.sernet.fluke.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.interfaces.ITeamService;
import de.sernet.fluke.model.Game;
import de.sernet.fluke.model.Player;
import de.sernet.fluke.persistence.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    ITeamService teamService;

    @Override
    public IGame create(long redOffensiveId, long redDefensiveId, long blueOffensiveId, long blueDefensiveId) {
        Player redOffensive = loadPlayer(redOffensiveId);
        Player redDefensive = loadPlayer(redDefensiveId);
        Player blueOffensive = loadPlayer(blueOffensiveId);
        Player blueDefensive = loadPlayer(blueDefensiveId);
        return create(redOffensive, redDefensive, blueOffensive, blueDefensive);
    }

    @Override
    public IGame create(Player redOffensive, Player redDefensive, Player blueOffensive, Player blueDefensive) { 
        ITeam red = teamService.findOrCreate(redDefensive, redOffensive);
        ITeam blue = teamService.findOrCreate(blueDefensive, blueOffensive);
        Game game = new Game(red, blue);
        return gameRepository.save(game);
    }

    private Player loadPlayer(long playerId) throws FlukeServiceException {
        Player player = playerService.findOne(playerId);
        if (player == null) {
            throw new FlukeServiceException("No player found for id: " + playerId);
        }
        return player;
    }

    @Override
    public IGame save(IGame game) {
        return gameRepository.save((Game) game);
    }

    @Override
    public IGame findById(Long gameId) {
        return (IGame) gameRepository.findById(gameId);
    }

    @Override
    public List<IGame> findByDate(LocalDateTime time) {
        Iterable<Game> internalResult = gameRepository.findByGameDate(time);
        List<IGame> result = new ArrayList<>(0);
        for (Game c : internalResult) {
            result.add(c);
        }
        return result;
    }

    @Override
    public IGame[] findAllUntrackedGames() {
        List<IGame> result = new ArrayList<>();
        for(IGame game : gameRepository.findAll()){
            if(game.getResult() == null || (game.getResult().getRedTeamGoals() == 0 && game.getResult().getBlueTeamGoals() == 0)){
                result.add(game);
            }
        }
        return result.toArray(new IGame[result.size()]);
    }

}
