FROM payara/server-full

COPY deployment/route-service.jks /opt/payara/appserver/glassfish/domains/domain1/config
COPY deployment/city-service.crt city-service.crt

USER root
RUN keytool -import -trustcacerts -alias city-service -file city-service.crt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt
USER payara

COPY build/libs/soa-backend-route-1.0-SNAPSHOT.war /opt/payara/deployments
