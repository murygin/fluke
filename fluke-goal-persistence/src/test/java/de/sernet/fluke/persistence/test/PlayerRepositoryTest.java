/*******************************************************************************
 * Copyright (c) 2016 Daniel Murygin <dm{a}sernet{dot}de>.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *     Daniel Murygin <dm{a}sernet{dot}de> - initial API and implementation
 ******************************************************************************/
package de.sernet.fluke.persistence.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.persistence.PersistenceApplication;
import de.sernet.fluke.model.Player;
import de.sernet.fluke.persistence.PlayerRepository;
import java.util.UUID;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PersistenceApplication.class)
public class PlayerRepositoryTest {

    private static final int NUMBER_OF_PLAYERS = 100;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    private Player daniel;
    
    @Before
    public void init() {
        daniel= new Player();
        daniel.setFirstName("Daniel");
        daniel.setLastName("Murygin");
        daniel.setConcededGoals(2);     
        daniel.setScoredDefensiveGoals(8);
        daniel.setScoredOffensiveGoals(16);
        daniel.setScoredTotalGoals(24);
        daniel.setWonGames(4);
        daniel.setLostGames(1);
        daniel = playerRepository.save(daniel);
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            playerRepository.save(createRandomPlayer());
        }
    }
    
    @Test
    public void testFindOne() { 
        Player playerResult = playerRepository.findOne(daniel.getId());
        assertNotNull(playerResult);
        check(playerResult, daniel);
    }
    
    @Test
    public void testFindAll() { 
        Iterable<Player> result = playerRepository.findAll();
        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        boolean found = false;
        for (Player player : result) {
            if(player.getId()==daniel.getId()) {
                check(player, daniel);
                found = true;
            }
        }
        assertTrue(found);    
    }
    
    private void check(Player player, Player playerExpected) {
        assertEquals(playerExpected.getId(),player.getId());
        assertEquals(playerExpected.getFirstName(),player.getFirstName());
        assertEquals(playerExpected.getLastName(),player.getLastName());
        assertEquals(playerExpected.getConcededGoals(),player.getConcededGoals());
        assertEquals(playerExpected.getLostGames(),player.getLostGames());
        assertEquals(playerExpected.getScoredDefensiveGoals(),player.getScoredDefensiveGoals());
        assertEquals(playerExpected.getScoredOffensiveGoals(),player.getScoredOffensiveGoals());
        assertEquals(playerExpected.getScoredTotalGoals(),player.getScoredTotalGoals());
        assertEquals(playerExpected.getWonGames(),player.getWonGames());
    }
    
    private Player createRandomPlayer() {
        Player player = new Player();
        player.setFirstName(UUID.randomUUID().toString());
        player.setLastName(UUID.randomUUID().toString());
        return player;
    }

}
