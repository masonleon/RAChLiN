FROM openjdk:11-jre-slim

COPY aisMessageListener-jar-with-dependencies.jar /usr/local/bin

WORKDIR usr/local/bin

# arbitrary port numbers.  Bind to actual port with docker run -p host_port:10110 ais-message-listener.
# see build notes.
EXPOSE 10110/tcp
EXPOSE 5432/tcp

CMD ["java", "-jar", "aisMessageListener-jar-with-dependencies.jar", "10110", "aisdb.cz93wcr6yyio.us-east-2.rds.amazonaws.com", "ais_DB", "aisDBmasterUser", "[Insert password here]"]
