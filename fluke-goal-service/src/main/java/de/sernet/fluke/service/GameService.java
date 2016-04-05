package de.sernet.fluke.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.IGame;
import de.sernet.fluke.interfaces.IGameService;
import de.sernet.fluke.persistence.Game;
import de.sernet.fluke.persistence.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService {
    
    @Autowired
    GameRepository gameRepository;

    @Override
    public IGame save(IGame game) {
        return gameRepository.save((Game)game);
    }

    @Override
    public IGame findById(Long gameId) {
        return (IGame) gameRepository.findById(gameId);
    }
    
    @Override
    public List<IGame> findByDate(LocalDateTime time){
        Iterable<Game> internalResult = gameRepository.findByGameDate(time);
        List<IGame> result = new ArrayList<>(0);
        for(Game c : internalResult){
            result.add(c);
        }
        return result;
    }

    @Override
    public IGame findGame(long gameId) {
        return gameRepository.findOne(gameId);
    }

}
