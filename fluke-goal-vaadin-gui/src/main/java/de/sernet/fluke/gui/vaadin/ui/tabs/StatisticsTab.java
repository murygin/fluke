package de.sernet.fluke.gui.vaadin.ui.tabs;

import java.util.Arrays;
import java.util.List;

import com.vaadin.data.sort.Sort;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.sernet.fluke.client.rest.TeamRestClient;
import de.sernet.fluke.gui.vaadin.ui.Note;
import de.sernet.fluke.model.Player;
import de.sernet.fluke.model.Team;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@VaadinSessionScope
@Service
public class StatisticsTab extends AbstractFlukeTab implements IFlukeUITab {
    
    public static final String TYPE_ID = "statisticsView";
    public static final String LABEL = "Statistics";

    private static final long serialVersionUID = 20160408105841L;
    
    private Grid playerGrid;
    private Grid teamGrid;
    private VerticalLayout tabRootLayout;

    private Button switchStatistic;

    @Autowired
    private TeamRestClient teamService;
    
    public StatisticsTab(){
        super();
        setCaption(LABEL);
    }
    
    protected void initContent() {
        
        tabRootLayout = new VerticalLayout();
        tabRootLayout.setSizeFull();

        switchStatistic = new Button("", this::switchStatistic);
        switchStatistic.setIcon(FontAwesome.EXCHANGE);
        switchStatistic.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
        switchStatistic.setDescription("Switch Player/Team stats");
        
        playerGrid = new Grid();
        playerGrid.setSizeFull();

        playerGrid.setColumns(
                Player.FIRSTNAME,
                Player.LASTNAME,
                Player.WONGAMES,
                Player.LOSTGAMES,
                Player.SCOREDTOTALGOALS,
                Player.SCOREDOFFENSIVEGOALS,
                Player.SCOREDDEFENSIVEGOALS,
                Player.CONCEDEDGOALS,
                Player.WONKPI,
                Player.WONLOSTRATIO,
                Player.WONPERCENT);
        
        playerGrid.setSortOrder(Sort.by(Player.WONGAMES, SortDirection.DESCENDING)
                .then(Player.SCOREDTOTALGOALS, SortDirection.DESCENDING)
                .then(Player.LOSTGAMES, SortDirection.ASCENDING)
                .build());


        teamGrid = new Grid();
        teamGrid.setSizeFull();
        teamGrid.setColumns("offensivePlayer", "defensivePlayer", Team.WONGAMES, Team.LOSTGAMES, Team.SCOREDTOTALGOALS, Team.CONCEDEDGOALS, Team.SCOREDOFFENSIVEGOALS, Team.SCOREDDEFENSIVEGOALS);
        teamGrid.setSortOrder(Sort.by(Team.WONGAMES, SortDirection.DESCENDING).then(Team.LOSTGAMES, SortDirection.ASCENDING).then(Team.SCOREDTOTALGOALS, SortDirection.DESCENDING).build());
        teamGrid.setVisible(false);
        
        tabRootLayout.addComponent(playerGrid);
        tabRootLayout.addComponent(teamGrid);
        
        addCrudButton(switchStatistic);
        
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
        List<Team> teams = Arrays.asList(teamService.findAll());
        if(teams == null || teams.isEmpty()){
            Note.info("No teams found");
        } else {
            teamGrid.setContainerDataSource(
                    new BeanItemContainer<>(Team.class, teams));
        }
    }
    
    protected void updatePlayerList() {

        List<Player> players = Arrays.asList(getPlayerService().findAll());
        if(players == null || players.isEmpty()){
            Note.info("No players found");
        }else {

            playerGrid.setContainerDataSource(
                    new BeanItemContainer<>(Player.class, players));
        }
    }

    
    protected Component getMainComponent() {
        return tabRootLayout;
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

    @Override
    protected Grid getGrid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doEnter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
