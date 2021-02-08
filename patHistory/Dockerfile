FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/note-*.war"

WORKDIR "/abernathyapp/note/"

COPY ${APP_WAR} "note.war"

ENTRYPOINT ["java","-jar","note.war"]

