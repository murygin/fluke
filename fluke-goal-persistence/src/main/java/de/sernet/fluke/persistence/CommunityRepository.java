package de.sernet.fluke.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends CrudRepository<Community, Long> {

    List<Community> findById(@Param("id") long id);
    
    List<Community> findByName(@Param("communityName") String name);
    
}
