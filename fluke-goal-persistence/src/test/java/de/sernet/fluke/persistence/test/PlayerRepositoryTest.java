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
import de.sernet.fluke.persistence.Player;
import de.sernet.fluke.persistence.PlayerRepository;


import static org.junit.Assert.*;

import org.junit.Before;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PersistenceApplication.class)
public class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;
    
    @Before
    public void init() {
       
    }
    
    @Test
    public void test() {
        Player player = new Player();
        player.setFirstName("Daniel");
        player.setLastName("Murygin");
        player = playerRepository.save(player);
        
        Player playerResult = playerRepository.findOne(player.getId());
        assertNotNull(player);
        assertEquals(player.getId(),playerResult.getId());
        assertEquals(player.getFirstName(),playerResult.getFirstName());
        assertEquals(player.getLastName(),playerResult.getLastName());
    }

}
