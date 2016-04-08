# Fluke - Deployment

# Pivotal Web Services

See the getting started guide form Pivotal:
http://pivotal.io/platform/pcf-tutorials/getting-started-with-pivotal-cloud-foundry

* Download and install the Cloud Foundry Command Line Interface (cf CLI).
* Login to Pivotal Web Services: https://login.run.pivotal.io/
* Build the application
  * Clone repository: git clone ssh://git@git.verinice.org:7999/rd/fluke-goal.git
  * cd fluke-goal
  * Set database properties in file
    fluke-goal-rest/src/main/resources/application.proeprties
  * Set the URL of the REST API in file
    fluke-goal-vaadin-gui/src/main/resources/application.proeprties
  * Build with maven: mvn clean package [-DskipTests]
* Push to Pivotal Web Services
  * REST API: cf push <API-APP-NAME> -p ./fluke-goal-rest/target/fluke-goal-rest-<VERSION>.jar
  * vaadin GUI: cf push <APP-NAME> -p ./fluke-goal-vaadin-gui/target/fluke-goal-vaadin-gui-<VERSION>.jar
* Add and configure ClearDB MySQL Database at Pivotal Web Services and connect it to the
  REST API application. Repeat steps 'Build' and 'Push' with ClearDB database settings if needed.
* Run the application: http://<APP-NAME>.cfapps.io
