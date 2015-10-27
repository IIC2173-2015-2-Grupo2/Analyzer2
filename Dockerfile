FROM java:8
EXPOSE 5000
COPY . .
WORKDIR .
RUN ls -l
RUN javac ./parser-connection/src/main/java/Getter/Main.java
CMD ["java", "Main"]