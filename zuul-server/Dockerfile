FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/zuul-server-0.0.1-SNAPSHOT.war"

WORKDIR "/mediscreen-zuul-server/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

