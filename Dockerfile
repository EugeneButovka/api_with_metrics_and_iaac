FROM openjdk:17-jdk-slim

COPY ./build/libs/api_with_metrics_and_iaac.jar /app/api_with_metrics_and_iaac.jar
WORKDIR /app

# Expose port 8080 for the application
EXPOSE 8080

ENTRYPOINT ["java","-jar","api_with_metrics_and_iaac.jar"]
