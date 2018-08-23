sudo service tomcat stop
sudo mkdir ./upload
sudo chown -R tomcat:tomcat ./upload/*
sudo chmod -R +777 ./upload/*
sudo javac -cp /usr/share/java/tomcat/servlet-api.jar:/usr/share/java/tomcat/poi-3.13-20150929.jar:/usr/share/java/tomcat/poi-ooxml-3.13-20150929.jar:/usr/share/java/tomcat/poi-ooxml-schemas-3.13-20150929.jar:/usr/share/java/tomcat/xmlbeans-5.3.0-rc1.jar:/usr/share/java/tomcat/dom4j-1.6.1.jar:/usr/share/java/tomcat/postgresql-9.2-1004.jdbc41.jar:/usr/share/java/tomcat/commons-fileupload-1.2.2.jar:/usr/share/java/tomcat/log4j-1.2.16.jar:/usr/share/java/tomcat/postgresql-42.2.2.jre6.jar src/*.java;
sudo jar -cvf exportZipcode.war *;
sudo rm -rfv /var/lib/tomcat/webapps/exportZipcode  #remove outdated version
sudo cp -v exportZipcode.war /var/lib/tomcat/webapps/ #add new version
sudo chmod -R +777 /var/lib/tomcat/webapps/exportZipcode/upload/*
sudo service tomcat start