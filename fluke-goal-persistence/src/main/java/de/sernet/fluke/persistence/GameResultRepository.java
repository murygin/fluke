package de.sernet.fluke.persistence;

import de.sernet.fluke.model.GameResult;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface GameResultRepository extends CrudRepository<GameResult, Long> {

    List<GameResult> findById(@Param("id") long id);
    
}
