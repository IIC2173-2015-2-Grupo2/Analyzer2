FROM maven:3.2-jdk-8
EXPOSE 4567
COPY . .
WORKDIR .
RUN mvn -f parser-connection/pom.xml package
RUN mvn -f parser-connection/pom.xml exec:java