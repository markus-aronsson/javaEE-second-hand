javaEE-second-hand
==================

Glassfish-server setup
======================

Configurate your server for using the JDBC-realm for authentication

1. Right-click on the Glassfish server: View Domain Admin Console
2. Goto "server-config", "Security", "Realms"
3. Press "new"
4. Enter (observe that you must enter EXACTLY this): 

Name:  jdbc-realm

Class Name: com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm

JAAS: jdbcRealm

JNDI: jdbc/projectResource

User table: APP.users

User name column: username

Password column: password

Group table: APP.user_groups

Group Table User Name Column: username

Group name column: groupname

Digest Algorithm: none

Password Encryption Algorithm: none

Leave other fields empty. 

5. Click "ok"
6. Press "Security"
7. Change the Default Realm to "jdbc-realm"
8. Press "save"

Done


Database - setup
================

1. Click "Services" in NetBeans 
2. Click on "Java DB" (The correct dependencies should be in POM.xml)
3. Create a new database
4. Enter:
   Database Name: project
   Username: app
   Password: app
