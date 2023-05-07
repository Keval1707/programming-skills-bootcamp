# Setting up MySQL Connector/J on Windows 11

MySQL Connector/J is a JDBC driver for MySQL that allows you to connect to a MySQL database from a Java application. In order to use Connector/J in your Java project, you'll need to add the mysql-connector.jar file to your project's classpath.

### Here's how you can add the mysql-connector.jar file to the environment variable in Windows 11:

 * Download the Connector/J from - [mysql-connector](mysql-connector-j-8.0.33.jar)
 * Extract the mysql-connector.jar file from the downloaded archive.
 * Open the Start menu and search for "Environment Variables".
 * Click on "Edit the system environment variables".
 * Click on the "Environment Variables" button.
 * Under the "System Variables" section, click on the "New" button.
 * Enter CLASSPATH as the variable name and the full path to the mysql-connector.jar file as the variable value (e.g.mysql-connector-java-8.0.27.jar).
 * Click "OK" to close all windows.

# 
For more information on adding JAR files to your classpath, refer to the official documentation: https://www.vertica.com/docs/9.3.x/HTML/Content/Authoring/ConnectingToVertica/ClientJDBC/ModifyingTheJavaCLASSPATH.htm
