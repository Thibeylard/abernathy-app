FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/client-*.war"

WORKDIR "/abernathyapp/client/"

COPY ${APP_WAR} "client.war"

ENTRYPOINT ["java","-jar","client.war"]

