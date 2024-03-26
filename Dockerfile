FROM eclipse-temurin:21-jdk-alpine
COPY target/shared-parking-spot-app-0.0.1-SNAPSHOT.jar shared-parking-spot.jar
ENTRYPOINT ["java","-jar","/ride-mate-finder.jar"]