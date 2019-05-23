FROM tomcat:9.0.20-jre11

RUN rm -rf /usr/local/tomcat/webapps/ROOT

ADD ./target/*.war $CATALINA_HOME/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
