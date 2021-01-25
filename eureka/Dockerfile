FROM openjdk:13-oracle

ARG APP_WAR="./build/libs/eureka-*.war"

WORKDIR "/abernathyapp-eureka/app/"

COPY ${APP_WAR} "app.war"

ENTRYPOINT ["java","-jar","app.war"]

