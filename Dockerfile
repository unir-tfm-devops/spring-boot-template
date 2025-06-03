FROM eclipse-temurin:24-jre

WORKDIR app

ADD src/main/resources/application.yml application.yml
ADD target/spring-boot-template.jar app.jar

EXPOSE 8080

ENTRYPOINT java $JAVA_OPTS -jar app.jar