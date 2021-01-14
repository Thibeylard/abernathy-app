FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/patient-ms-0.0.1-SNAPSHOT.war"

WORKDIR "/mediscreen-patient-ms/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

