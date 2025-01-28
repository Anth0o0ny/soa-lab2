FROM quay.io/wildfly/wildfly:33.0.2.Final-jdk21

COPY deployment/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/
COPY deployment/postgresql-42.7.4.jar /opt/jboss/wildfly/modules/system/layers/base/org/postgresql/main/

COPY deployment/standalone.xml /opt/jboss/wildfly/standalone/configuration/
COPY deployment/city-service.jks /opt/jboss/wildfly/standalone/configuration/

COPY build/libs/soa-backend-city-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/