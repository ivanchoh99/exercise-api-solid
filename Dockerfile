ARG PORT_APP=8080
ARG NAME_PROJECT=ExampleSolid
FROM gradle:jdk17-focal as builder
WORKDIR /api
COPY ./build.gradle .
COPY ./src ./src
RUN gradle clean --build-cache bootJar
FROM openjdk:17.0.2-oraclelinux8
WORKDIR /api
COPY --from=builder /api/build/libs/*.jar ./app.jar
ENV PORT $PORT_APP
EXPOSE $PORT
ENTRYPOINT ["java", "-jar", "app.jar"]
LABEL authors="ivanh"