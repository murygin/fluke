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
    public ITeam findByPlayers(IPlayer defensivePlayer, IPlayer offensivePlayer) {
        return teamRepository.findByPlayers((Player)defensivePlayer, (Player)offensivePlayer);
    }

    @Override
    public ITeam findById(long teamId) {
        return teamRepository.findOne(teamId);
    }

    @Override
    public ITeam findOrCreate(IPlayer defensivePlayer, IPlayer offensivePlayer) {
        ITeam team = findByPlayers(defensivePlayer, offensivePlayer);
        if(team==null) {
            team = new Team((Player)defensivePlayer,(Player)offensivePlayer);
            team = save(team);
        }
        return team;
    }

}
