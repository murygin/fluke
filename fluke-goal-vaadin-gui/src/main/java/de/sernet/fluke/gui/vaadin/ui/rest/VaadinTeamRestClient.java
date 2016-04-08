package de.sernet.fluke.gui.vaadin.ui.rest;

import org.springframework.stereotype.Service;

import com.vaadin.spring.annotation.VaadinSessionScope;

import de.sernet.fluke.client.rest.TeamRestClient;

@Service
@VaadinSessionScope
public class VaadinTeamRestClient extends TeamRestClient {

}
