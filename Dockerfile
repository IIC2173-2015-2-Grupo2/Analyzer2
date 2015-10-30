FROM maven:3.2-jdk-8-onbuild
EXPOSE 4567
COPY . /usr/src/app
RUN mvn install
