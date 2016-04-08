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
package de.sernet.fluke.client.rest.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.interfaces.IPlayer;

/**
 *
 *
 * @author Daniel Murygin <dm{a}sernet{dot}de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class PlayerRestClientTest {

    @Autowired
    PlayerRestClient playerClient;
    
    @Before
    public void init() {
        playerClient.setServerUrl("http://localhost:8080/service/");
        playerClient.setPath("player");
    }
    
    @Test
    public void test() {
        IPlayer player = new Player();
        player.setFirstName("Daniel");
        player.setLastName("Murygin");
        player = playerClient.save(player);
        
        IPlayer playerResult = playerClient.findOne(player.getId());
        IPlayer[] allPlayers = playerClient.findAll();
        assertNotNull(allPlayers);
        assertNotNull(player);
        assertEquals(player.getId(),playerResult.getId());
        assertEquals(player.getFirstName(),playerResult.getFirstName());
        assertEquals(player.getLastName(),playerResult.getLastName());
    }

    @Test
    public void deleteRemovesPlayer() {
        IPlayer player = new Player();
        player.setFirstName("Donald");
        player = playerClient.save(player);
        
        assertThat(playerClient.findOne(player.getId()), notNullValue());
        playerClient.delete(player.getId());
        assertThat(playerClient.findOne(player.getId()), nullValue());
    }
}
