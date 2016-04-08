package de.sernet.fluke.persistence;

import de.sernet.fluke.model.GameResult;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.sernet.fluke.interfaces.IGameResult;

public interface GameResultRepository extends CrudRepository<GameResult, Long> {

    List<IGameResult> findById(@Param("id") long id);
    
}
