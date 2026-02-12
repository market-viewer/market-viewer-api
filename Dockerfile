# 1. Build Stage
FROM gradle:jdk25-alpine AS build
WORKDIR /home/gradle/project
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle bootJar --no-daemon -x test

# 2. Run Stage (The final image)
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]