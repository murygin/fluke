package de.sernet.fluke.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.sernet.fluke.interfaces.ICommunityService;
import de.sernet.fluke.model.Community;
import de.sernet.fluke.persistence.CommunityRepository;
import org.springframework.stereotype.Service;

@Service
public class CommunityService implements ICommunityService {

    @Autowired
    CommunityRepository communityRepository;
    
    @Override
    public Community save(Community community) {
        return communityRepository.save((Community)community);
    }

    @Override
    public Community findByName(String name) {
        return (Community)communityRepository.findByName(name);
    }

    @Override
    public List<Community> findAll() {
        Iterable<Community> internalResult = communityRepository.findAll(); 
        List<Community> result = new ArrayList<>(0);
        for(Community c : internalResult){
            result.add((Community)c);
        }
        return result;
    }

}
