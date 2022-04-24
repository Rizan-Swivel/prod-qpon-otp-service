FROM openjdk:11-jre-slim
COPY /target/qpon-otp-service-1.0.1-SNAPSHOT.jar /home/qpon-otp-service-1.0.1-SNAPSHOT.jar
ENV REDIS_HOST qpon-prod-redis.poc2a8.0001.aps1.cache.amazonaws.com
ENV REDIS_PORT 6379
WORKDIR /home
EXPOSE 8082
CMD ["java", "-jar", "qpon-otp-service-1.0.1-SNAPSHOT.jar"]
