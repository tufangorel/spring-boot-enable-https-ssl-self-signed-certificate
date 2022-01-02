FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /app
WORKDIR /app
COPY target/*.jar /app/app.jar
ARG CERT="custapi.crt"
COPY src/main/resources/ssl/custapi.crt $JAVA_HOME/lib/security

RUN \
    cd $JAVA_HOME/lib/security && \
    keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias custapi -file $CERT

ENTRYPOINT ["java", "-jar", "/app/app.jar"]