FROM openjdk:11-jre

COPY target/city-builder-backend-jar-with-dependencies.jar /usr/src/game/

#CMD ["java","-jar","/usr/src/game/original-game-backend-1.3.0.jar", "http://0.0.0.0:4444/"]
CMD ["java","-jar","/usr/src/game/city-builder-backend-jar-with-dependencies.jar"]
EXPOSE 4444
