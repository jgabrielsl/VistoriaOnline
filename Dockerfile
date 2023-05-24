FROM openjdk:11-jre-slim-buster

ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE=${PROFILE}
ENV ADDITIONAL_OPTS=${ADDITIONAL_OPTS}

WORKDIR /opt/spring_boot

COPY /target/*.jar vistoria-online.jar

SHELL ["/bin/sh", "-c"]

EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar vistoria-online.jar --spring.profiles.active=${PROFILE}