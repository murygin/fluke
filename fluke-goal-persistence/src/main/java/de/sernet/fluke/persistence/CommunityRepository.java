package de.sernet.fluke.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommunityRepository extends CrudRepository<Community, Long> {

    Community findById(@Param("id") long id);
    
    Community findByName(@Param("communityName") String name);
    
}
