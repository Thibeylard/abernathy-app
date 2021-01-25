FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/app-*.war"

WORKDIR "/abernathy/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

