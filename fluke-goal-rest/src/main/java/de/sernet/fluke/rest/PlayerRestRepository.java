package de.sernet.fluke.rest;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.sernet.fluke.persistence.PlayerRepository;

@RepositoryRestResource(collectionResourceRel = "player", path = "player")
public interface PlayerRestRepository extends PlayerRepository {


}
