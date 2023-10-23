FROM maven:3.8.5-openjdk-17 as build-stage
WORKDIR /opt/build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY ./src ./src
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17-slim
EXPOSE 8080
ENV JAVA_OPTIONS="-XX:+UseParallelGC -Djava.awt.headless=true"
COPY --from=build-stage /opt/build/target/jwtbackend.jar /opt/app/jwtbackend.jar
CMD ["java", "-jar", "/opt/app/jwtbackend.jar"]