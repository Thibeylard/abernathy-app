FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/patient-*.war"

WORKDIR "/abernathyapp/patient/"

COPY ${APP_WAR} "patient.war"

ENTRYPOINT ["java","-jar","patient.war"]

