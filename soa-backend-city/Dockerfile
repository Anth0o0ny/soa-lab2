FROM openjdk:17
ARG JAR_FILE=build/libs/soa-backend-city-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

COPY deployment/city-service.crt city-service.crt
COPY deployment/route-service.crt route-service.crt

USER root
RUN keytool -importcert -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -alias city-service -trustcacerts -file city-service.crt -noprompt


ENTRYPOINT ["java","-jar","app.jar"]