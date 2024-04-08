FROM eclipse-temurin:21-jdk-alpine
COPY ride-mate-finder-app/target/ride-mate-finder-app-0.0.1-SNAPSHOT.jar ride-mate-finder.jar
ENTRYPOINT ["java","-jar","/ride-mate-finder.jar"]