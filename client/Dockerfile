FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/client-0.0.1-SNAPSHOT.war"

WORKDIR "/mediscreen-client/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

