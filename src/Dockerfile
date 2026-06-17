#############################################################################################
###              Stage where Docker is building spring boot app using maven               ###
#############################################################################################
FROM maven:3.8.5-openjdk-17 AS build

ARG MVN_PROFILE=default
ARG SERVICE_NAME=jag-mail-it
ARG SKIP_TESTS=false

ENV SERVICE_NAME=${SERVICE_NAME}

WORKDIR /

COPY . .

RUN mvn -B clean install \
        -P ${MVN_PROFILE} \
        -Dmaven.test.skip=${SKIP_TESTS} \
        -f jag-mail-it/pom.xml

#############################################################################################

#############################################################################################
### Stage where Docker is running a java process to run a service built in previous stage ###
#############################################################################################
FROM eclipse-temurin:17-jre-jammy

ARG SERVICE_NAME=jag-mail-it

RUN apt-get update \
      && apt-get upgrade -y \
      && apt-get install -y openssl

COPY --from=build ./${SERVICE_NAME}/target/${SERVICE_NAME}-*.jar /app/service.jar

CMD ["java", "-jar", "/app/service.jar"]

#############################################################################################
