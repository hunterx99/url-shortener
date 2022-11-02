FROM openjdk:8
EXPOSE 8080
ADD target/url-shortener.jar url-shortener.jar
ENTRYPOINT ["java", "-jar", "url-shortener.jar"]
