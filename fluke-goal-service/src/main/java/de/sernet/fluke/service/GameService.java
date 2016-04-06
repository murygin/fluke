package de.sernet.fluke.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.IPlayerService;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.interfaces.ITeamService;
import de.sernet.fluke.persistence.Game;
import de.sernet.fluke.persistence.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    IPlayerService playerService;

    @Autowired
    ITeamService teamService;

    @Override
    public IGame create(long redOffensiveId, long redDefensiveId, long blueOffensiveId, long blueDefensiveId) {
        IPlayer redOffensive = loadPlayer(redOffensiveId);
        IPlayer redDefensive = loadPlayer(redDefensiveId);
        IPlayer blueOffensive = loadPlayer(blueOffensiveId);
        IPlayer blueDefensive = loadPlayer(blueDefensiveId);
        return create(redOffensive, redDefensive, blueOffensive, blueDefensive);
    }

    @Override
    public IGame create(IPlayer redOffensive, IPlayer redDefensive, IPlayer blueOffensive, IPlayer blueDefensive) { 
        ITeam red = teamService.findOrCreate(redDefensive, redOffensive);
        ITeam blue = teamService.findOrCreate(blueDefensive, blueOffensive);
        Game game = new Game(red, blue);
        return gameRepository.save(game);
    }

    private IPlayer loadPlayer(long playerId) throws FlukeServiceException {
        IPlayer player = playerService.findOne(playerId);
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
    public IGame findGame(long gameId) {
        return gameRepository.findOne(gameId);
    }

}
