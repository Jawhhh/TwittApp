FROM alpine:3.20 AS build
COPY . /app

FROM eclipse-temurin:21-jre-alpine AS run
COPY --from=build app/build/libs/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

