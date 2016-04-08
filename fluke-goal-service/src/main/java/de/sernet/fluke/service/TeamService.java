package de.sernet.fluke.service;

import de.sernet.fluke.model.Team;
import de.sernet.fluke.model.Player;
import java.util.ArrayList;
import java.util.List;

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
    public ITeam findByPlayers(Player defensivePlayer, Player offensivePlayer) {
        return teamRepository.findByPlayers((Player)defensivePlayer, (Player)offensivePlayer);
    }

    @Override
    public ITeam findById(long teamId) {
        return teamRepository.findOne(teamId);
    }

    @Override
    public ITeam findOrCreate(Player defensivePlayer, Player offensivePlayer) {
        ITeam team = findByPlayers(defensivePlayer, offensivePlayer);
        if(team==null) {
            team = new Team((Player)defensivePlayer,(Player)offensivePlayer);
            team = save(team);
        }
        return team;
    }
    
    @Override
    public ITeam[] findAll() {
        Iterable<Team> teams = teamRepository.findAll();
        List<ITeam> castedTeams = new ArrayList<>();

        for (Team team : teams) {
            castedTeams.add((Team) team);
        }

        return castedTeams.toArray(new ITeam[] {});
    }

}
