package de.sernet.fluke.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.sernet.fluke.interfaces.*;
import de.sernet.fluke.persistence.*;

@Service
public class TeamService implements ITeamService {
    
    @Autowired
    TeamRepository teamRepository;

    @Override
    public ITeam save(ITeam team) {
        return teamRepository.save((Team)team);
    }

    @Override
    public ITeam findOne(long teamId) {
        return (ITeam) teamRepository.findById(teamId);
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
