package de.sernet.fluke.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.ICommunity;
import de.sernet.fluke.interfaces.ICommunityService;
import de.sernet.fluke.persistence.Community;
import de.sernet.fluke.persistence.CommunityRepository;

public class CommunityService implements ICommunityService {

    @Autowired
    CommunityRepository communityRepository;
    
    @Override
    public ICommunity save(ICommunity community) {
        return communityRepository.save((Community)community);
    }

    @Override
    public List<ICommunity> findByName(String name) {
        Iterable<Community> internalResult = communityRepository.findByName(name);
        List<ICommunity> result = new ArrayList<>(0);
        for(Community c : internalResult){
            result.add(c);
        }
        return result;
    }

    @Override
    public List<ICommunity> findAll() {
        Iterable<Community> internalResult = communityRepository.findAll(); 
        List<ICommunity> result = new ArrayList<>(0);
        for(Community c : internalResult){
            result.add((ICommunity)c);
        }
        return result;
    }

}
