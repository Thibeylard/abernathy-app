FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/patHistory-*.war"

WORKDIR "/abernathyapp/patHistory/"

COPY ${APP_WAR} "patHistory.war"

ENTRYPOINT ["java","-jar","patHistory.war"]

