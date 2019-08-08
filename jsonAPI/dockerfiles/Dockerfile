FROM tomcat:9.0

COPY AisDecodeMessageStore.war $CATALINA_HOME/webapps/AisDecodeMessageStore.war

# TODO This isn't secure.
COPY database_credentials $CATALINA_HOME/webapps/credentials/database_credentials

EXPOSE 8080
EXPOSE 5432

CMD ["catalina.sh", "run"]
