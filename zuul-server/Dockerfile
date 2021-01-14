FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/zuul-server-*.war"

WORKDIR "/mediscreen-zuul-server/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

