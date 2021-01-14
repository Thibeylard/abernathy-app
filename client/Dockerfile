FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/client-*.war"

WORKDIR "/mediscreen-client/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

