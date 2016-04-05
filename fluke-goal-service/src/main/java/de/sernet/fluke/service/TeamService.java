package de.sernet.fluke.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.interfaces.ITeamService;
import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.Team;
import de.sernet.fluke.persistence.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {
    
    @Autowired
    TeamRepository teamRepository;

    @Override
    public ITeam save(ITeam team) {
        return teamRepository.save((Team)team);
    }

    @Override
    public List<ITeam> findOne(long teamId) {
        Iterable<Team> internalResult = teamRepository.findById(teamId);
        List<ITeam> result = new ArrayList<>(0);
        for(Team c : internalResult){
            result.add(c);
        }
        return result;
    }

    @Override
    public List<ITeam> findByPlayers(IPlayer defensivePlayer, IPlayer offensivePlayer) {
        Iterable<Team> internalResult = teamRepository.findByPlayers((Player)defensivePlayer, (Player)offensivePlayer);
        List<ITeam> result = new ArrayList<>(0);
        for(Team c : internalResult){
            result.add(c);
        }
        return result;
    }

    @Override
    public ITeam findById(long teamId) {
        return teamRepository.findOne(teamId);
    }

}
