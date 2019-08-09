# To build and deploy this image, see instructions in build_process.txt

FROM tomcat:9.0

COPY AisDecodeMessageStore.war $CATALINA_HOME/webapps/AisDecodeMessageStore.war

EXPOSE 8080
EXPOSE 5432

CMD ["catalina.sh", "run"]
