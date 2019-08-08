FROM openjdk:11-jre-slim

COPY aisMessageListener-jar-with-dependencies.jar /usr/local/bin

WORKDIR usr/local/bin

EXPOSE 10110/tcp
EXPOSE 5432/tcp

CMD ["java", "-jar", "aisMessageListener-jar-with-dependencies.jar"]

# Build locally:
    # mvn package to produce jar
    # move jar from target dockerfiles directory
    # docker build --tag=[your-tag-here] .
    # docker tag image username/repo:tag
    # docker login
    # docker push username/repo:tag

# Deploy to server:
    # docker pull username/repo:tag
    # docker create --net=host [your-tag-here]
    # database credentials file must be accessible on server
    # docker cp database_credentials.txt [id-output-by-create]:/usr/local/bin/
    # docker start -a [id-output-by-create]
