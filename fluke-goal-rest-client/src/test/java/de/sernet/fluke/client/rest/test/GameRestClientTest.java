/*
 * Copyright 2016 SerNet Service Network GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.sernet.fluke.client.rest.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.sernet.fluke.client.rest.*;
import de.sernet.fluke.model.Game;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Daniel Murygin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class GameRestClientTest {
    
    @Autowired
    GameRestClient gameRestClient;
    
    @Value("${rest-server.url:http://localhost:8080}")
    private String serverUrl;
    
    private RestTemplate restTemplate;
    
    MockRestServiceServer mockServer;
    private ResourceBundle resourceBundle;
    
    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        gameRestClient.setRestHandler(restTemplate);
        mockServer = MockRestServiceServer.createServer(restTemplate);
        this.resourceBundle = PropertyResourceBundle.getBundle("GameRestClientTest");
    }
    
    @Test
    public void testCreate() {
        int redOffensiveId = 1;
        int redDefensiveId = 2;
        int blueOffensiveId = 3;
        int blueDefensiveId = 4;
        
        String responseBody = this.resourceBundle.getString("testCreateResult");
        
        mockServer.expect(MockRestRequestMatchers.requestTo(createCreateUrl()))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.jsonPath("$.redOffensiveId").value(redOffensiveId))
                .andExpect(MockRestRequestMatchers.jsonPath("$.redDefensiveId").value(redDefensiveId))
                .andExpect(MockRestRequestMatchers.jsonPath("$.blueOffensiveId").value(blueOffensiveId))
                .andExpect(MockRestRequestMatchers.jsonPath("$.blueDefensiveId").value(blueDefensiveId))
                .andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON));
        
        
        Game newGame = gameRestClient.create(
                Long.valueOf(redOffensiveId), 
                Long.valueOf(redDefensiveId), 
                Long.valueOf(blueOffensiveId), 
                Long.valueOf(blueDefensiveId)
        );
        
        mockServer.verify();
        
        Assert.assertNotNull(newGame);
        Assert.assertEquals(blueDefensiveId, newGame.getBlueTeam().getDefensivePlayer().getId());
        Assert.assertEquals(blueOffensiveId, newGame.getBlueTeam().getOffensivePlayer().getId());
        Assert.assertEquals(redDefensiveId, newGame.getRedTeam().getDefensivePlayer().getId());
        Assert.assertEquals(redOffensiveId, newGame.getRedTeam().getOffensivePlayer().getId());
    }

    private String createCreateUrl() {
        return gameRestClient.getCreateUrl();
    }
    
}
