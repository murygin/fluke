package de.sernet.fluke.gui.vaadin.ui.tabs;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.sort.Sort;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.sernet.fluke.client.rest.PlayerRestClient;
import de.sernet.fluke.client.rest.TeamRestClient;
import de.sernet.fluke.gui.vaadin.ui.FlukeUI;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.interfaces.ITeam;
import de.sernet.fluke.model.Player;

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
        playerGrid.setColumns(
                Player.FIRSTNAME,
                Player.LASTNAME,
                Player.WONGAMES,
                Player.LOSTGAMES,
                Player.SCOREDTOTALGOALS,
                Player.SCOREDOFFENSIVEGOALS,
                Player.SCOREDDEFENSIVEGOALS,
                Player.CONCEDEDGOALS);
        
        playerGrid.setSortOrder(Sort.by(Player.WONGAMES, SortDirection.DESCENDING)
                .then(Player.SCOREDTOTALGOALS, SortDirection.DESCENDING)
                .then(Player.LOSTGAMES, SortDirection.ASCENDING)
                .build());
        playerGrid.setWidthUndefined();

        teamGrid = new Grid();
        teamGrid.setColumns("offensivePlayer", "defensivePlayer", ITeam.WONGAMES, ITeam.LOSTGAMES, ITeam.SCOREDTOTALGOALS, ITeam.CONCEDEDGOALS, ITeam.SCOREDOFFENSIVEGOALS, ITeam.SCOREDDEFENSIVEGOALS);
        teamGrid.setSortOrder(Sort.by(ITeam.WONGAMES, SortDirection.DESCENDING).then(ITeam.LOSTGAMES, SortDirection.ASCENDING).then(ITeam.SCOREDTOTALGOALS, SortDirection.DESCENDING).build());
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

        List<Player> players = Arrays.asList(playerService.findAll());
        if(players == null || players.isEmpty()){
            Note.info("No players found");
        }else {

            playerGrid.setContainerDataSource(
                    new BeanItemContainer<>(Player.class, players));
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
