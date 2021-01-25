FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/configserver-*.war"

WORKDIR "/abernathyapp/configserver/"

COPY ${APP_WAR} "configserver.war"

ENTRYPOINT ["java","-jar","configserver.war"]

