FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/config-server-0.0.1-SNAPSHOT.war"

WORKDIR "/mediscreen-config-server/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

