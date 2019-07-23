FROM openjdk:11-jre-slim

COPY aisMessageListener.jar /usr/local/bin

WORKDIR usr/local/bin

# arbitrary port number.  Bind to actual port with docker run -p host_port:9000 ais-message-listener
EXPOSE 10110/tcp

# TODO: create a separate, non-root user profile or two for application database access.
CMD ["java", "-jar", "ais-message-listener.jar", "10110",
"aisdb.cz93wcr6yyio.us-east-2.rds.amazonaws.com", "aisDB", "aisDBmasterUser", "[enter password]"]
