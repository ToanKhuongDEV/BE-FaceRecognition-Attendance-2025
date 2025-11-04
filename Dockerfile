FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# run stage
FROM openjdk:17-jdk
WORKDIR /app

COPY --from=build /app/target/BE-FaceRecognition-Attendance-2025-0.0.1-SNAPSHOT.war BE-FaceRecognition-Attendance-2025.war
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "BE-FaceRecognition-Attendance-2025.war"]
