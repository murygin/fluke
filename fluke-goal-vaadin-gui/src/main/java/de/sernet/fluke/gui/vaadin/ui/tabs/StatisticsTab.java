package de.sernet.fluke.gui.vaadin.ui.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.data.sort.Sort;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.sernet.fluke.client.rest.Player;
import de.sernet.fluke.client.rest.PlayerRestClient;
import de.sernet.fluke.client.rest.TeamRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.IPlayer;
import de.sernet.fluke.interfaces.ITeam;

public class StatisticsTab extends FormLayout implements IFlukeUITab {
    
    public static final String TYPE_ID = "statisticsView";
    public static final String LABEL = "Statistics";

    private static final long serialVersionUID = 20160408105841L;
    
    private Grid playerGrid;
    private Grid teamGrid;
    private Button switchStatistic;
    private VerticalLayout mainLayout;
    
    private final PlayerRestClient playerService;
    private final TeamRestClient teamService;
    
    public StatisticsTab(){
        super();
        setCaption(LABEL);
        playerService = ((FlukeUI) UI.getCurrent()).getPlayerRestClient();
        teamService = ((FlukeUI) UI.getCurrent()).getTeamRestClient();
        initContent();
    }
    
    protected void initContent() {
        
        switchStatistic = new Button("Switch Player/Team stats", this::switchStatistic);
        
        playerGrid = new Grid();
        playerGrid.setColumns("firstName", "lastName", "wonGames", "lostGames", "scoredTotalGoals", "scoredOffensiveGoals", "scoredDefensiveGoals", "concededGoals");
        
        playerGrid.setSortOrder(Sort.by("wonGames", SortDirection.DESCENDING).then("scoredTotalGoals", SortDirection.DESCENDING).then("lostGames", SortDirection.ASCENDING).build());
        playerGrid.setWidthUndefined();

        teamGrid = new Grid();
        teamGrid.setColumns("offensivePlayer", "defensivePlayer", "wonGames", "lostGames", "scoredTotalGoals", "concededGoals", "scoredOffensiveGoals", "scoredDefensiveGoals");
        teamGrid.setSortOrder(Sort.by("wonGames", SortDirection.DESCENDING).then("lostGames", SortDirection.ASCENDING).then("scoredTotalGoals", SortDirection.DESCENDING).build());
        teamGrid.setVisible(false);
        
        mainLayout = new VerticalLayout();
        mainLayout.setWidthUndefined();
        mainLayout.setSpacing(true);
        
        mainLayout.addComponent(switchStatistic);
        mainLayout.addComponent(playerGrid);
        mainLayout.addComponent(teamGrid);
        
        addComponent(mainLayout);
        
    }
    
    private void switchStatistic(Button.ClickEvent event){
        if(event.getSource() == switchStatistic){
            updateLists();
            teamGrid.setVisible(!teamGrid.isVisible());
            playerGrid.setVisible(!teamGrid.isVisible());
        }
    }
    
    private void updateLists(){
        updatePlayerList();
        updateTeamList();
    }
    
    
    private void updateTeamList(){
        List<ITeam> teams = Arrays.asList(teamService.findAll());
        if(teams == null || teams.isEmpty()){
            Note.info("No teams found");
        } else {
            teamGrid.setContainerDataSource(
                    new BeanItemContainer<>(ITeam.class, teams));
        }
    }
    
    private void updatePlayerList() {

        List<IPlayer> players = Arrays.asList(playerService.findAll());
        if(players == null || players.isEmpty()){
            Note.info("No players found");
        }else {

            playerGrid.setContainerDataSource(
                    new BeanItemContainer<>(IPlayer.class, players));
        }
    }

    
    protected Component getMainComponent() {
        return mainLayout;
    }

    public String getTypeID() {
        return TYPE_ID;
    }

    public String getLabel() {
        return LABEL;
    }

    @Override
    public void doOnEnter() {
       updateLists();
    }
}
