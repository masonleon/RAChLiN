FROM tomcat:9.0
CMD ["rm", "-rf", "$CATALINA_HOME/webapps/basic-struts2"]
CMD ["rm", "-f", "$CATALINA_HOME/webapps/basic-struts2.war"]
COPY basic-struts2.war $CATALINA_HOME/webapps/basic-struts2.war

# This isn't the most secure. It's essentially equivalent to an scp.
COPY database_credentials $CATALINA_HOME/webapps/credentials/database_credentials

EXPOSE 8080
EXPOSE 5432
CMD ["catalina.sh", "run"]


# docker build --tag=[your-tag-here] .
# docker run -p 4000:80 [your-tag-here]
# docker tag image username/repo:tag
# docker login
# docker push username/repo:tag


