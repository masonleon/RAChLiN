FROM tomcat:9.0
CMD ["rm", "-rf", "$CATALINA_HOME/webapps/basic-struts2"]
CMD ["rm", "-f", "$CATALINA_HOME/webapps/basic-struts2.war"]
COPY basic-struts2.war $CATALINA_HOME/webapps/basic-struts2.war
EXPOSE 8080
CMD ["catalina.sh", "run"]

# docker build --tag=[your-tag-here] .
# docker run -p 4000:80 [your-tag-here]
# docker tag image username/repo:tag
# docker push username/repo:tag


