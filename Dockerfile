FROM maven:3.8.5-openjdk-17-slim AS build

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN mvn -f /usr/src/app/pom.xml -U clean package

FROM amazoncorretto:17.0.0-alpine3.14

RUN apk add --no-cache curl

COPY --from=build /usr/src/app/target/pruebaDux.jar /usr/app/pruebaDux.jar

CMD ["java", "-jar", "/usr/app/pruebaDux.jar"]
