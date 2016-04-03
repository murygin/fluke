package de.sernet.fluke.gui.vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class FlukeGoalVaadinGuiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FlukeGoalVaadinGuiApplication.class, args);
	}
        
        @Override
        protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
            return builder.sources(this.getClass());
        }
}
