##################################
# Stage 1 - Build (Amazon Corretto)
FROM amazoncorretto:21 AS build
WORKDIR /app

# Gradle wrapper and config
COPY gradlew gradlew.bat build.gradle settings.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew --no-daemon dependencies || true

# Application sources
COPY src ./src

# Build jar (skip tests in Docker build)
RUN ./gradlew --no-daemon clean bootJar -x test

########################################
# Stage 2 - Runtime (slim Corretto image)
FROM amazoncorretto:21
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]