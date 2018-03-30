# README #

The Application in purpose of managing own store network.

### Environment requirements (Need to have flowing tools properly installed) ###

* MySQL 5
* Java 8
* Tomcat 8
* Maven 3

### Environment set-up ###

* execute SQL script ./pwr_stores.sql
* go to /store/filters/
* copy /username directory under the name of the system username
* open application.properties in newly created dir
* change app.jdbc.username app.jdbc.password to match your mysql credentials, save and close
* open maven.properties in newly created dir
* change logging.directory property as the path to server log files, save and close (make sure such directory exists!)
* go to store/ using console
* enter mvn clean install
* if build went successfully, in target/ dir there should be store.war file, copy it to ~/Tomcat 8.0/webapps, move ROOT/ dir from there, and rename store.war to ROOT.war
* start tomcat
* application should be available under http://localhost:8080/login
* credentials for prime user are: test@mail.com\pass