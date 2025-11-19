FROM eclipse-temurin:21
WORKDIR /app

COPY /target/itrum-userservice-0.0.1-SNAPSHOT.jar build/

WORKDIR /app/build
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "itrum-userservice-0.0.1-SNAPSHOT.jar"]