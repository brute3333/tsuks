# Use a base Java image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY libs/robotworlds.jar /app/robotworlds.jar

EXPOSE 5000

# Run the Java application when the container starts
CMD ["java", "-jar", "robotworlds.jar"]
