FROM payara/server-full

COPY deployment/route-service.jks /opt/payara/appserver/glassfish/domains/domain1/config
COPY deployment/city-service.crt city-service.crt

USER root
RUN keytool -importcert -keystore /opt/payara/appserver/glassfish/domains/domain1/config/cacerts.p12 -storepass changeit -alias city-service -trustcacerts -file city-service.crt -noprompt
USER payara

COPY build/libs/soa-backend-route-1.0-SNAPSHOT.war /opt/payara/deployments
