FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/assess-*.war"

WORKDIR "/abernathyapp/assess/"

COPY ${APP_WAR} "assess.war"

ENTRYPOINT ["java","-jar","assess.war"]

