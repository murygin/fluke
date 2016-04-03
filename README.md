fluke goal / Glückstor
======================

An application to track your table soccer score.

Build and run
-------------

*Prerequisite:*
* Maven
* Java 8
* A running MySQL / MariaDB server with an empty database _fluke_.

*Clone project:*
```bash
git clone ssh://git@git.verinice.org:7999/rd/fluke-goal.git
cd fluke-goal
```
Set your MySQL / MariaDB connection properties in class _fluke-goal-persistence/src/main/resources/application.properties_

*Build project:*
```bash
export JAVA_HOME=/path/to/jdk-8
mvn package [-DskipTests]
```

*Start REST service:*
```bash
java -jar fluke-goal-rest/target/fluke-goal-rest-0.1.0-SNAPSHOT.jar
```

*Test REST service:*

Add a player:
```bash
curl -i -X POST -H "Content-Type:application/json" \
-d '{  "firstName" : "Frodo",  "lastName" : "Baggins" }' http://localhost:8080/service/player
```

Get a player:
```bash
curl http://localhost:8080/service/player/1
```
