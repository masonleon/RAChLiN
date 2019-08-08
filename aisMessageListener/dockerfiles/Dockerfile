FROM openjdk:11-jre-slim

COPY aisMessageListener-jar-with-dependencies.jar /usr/local/bin

WORKDIR usr/local/bin

EXPOSE 10110/tcp
EXPOSE 5432/tcp

# TODO make a private file to pull from that isn't committed to GitHub.
CMD ["java", "-jar", "aisMessageListener-jar-with-dependencies.jar", "10110", "aisdb.cz93wcr6yyio.us-east-2.rds.amazonaws.com", "ais_DB", "aisDBmasterUser", "[Insert database password here]"]
