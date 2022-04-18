# OTP Service
This micro-service generate and verify otp for mobile and email. 

## Requirements
* Spring boot 2.2.1
* Open JDK 11
* Redis 5.0.7
* Maven 3.6 (only to run maven commands)

## Install Redis
```
wget http://download.redis.io/releases/redis-5.0.7.tar.gz
tar xzf redis-5.0.7.tar.gz
cd redis-5.0.7
make
```
## Configuring Redis
* Adding password protecting
* Go to redis-installation-dir
* Edit redis.conf, uncomment requirepass 
```
eg: requirepass yourpassword
```

## Start Redis-Server
```
Goto redis installation directory and cd src 
./redis-server ../redis.conf
```

## Using the Redis-Cli
To view the data in redis cache, please use the redis-cli tool

* Go to redis installation directory redis-installation-dir 
* cd {redis-installation-dir}/src
* run ./redis-cli
* Authenticate by typing 
```
AUTH your password
```
## Using Redis commands
* In the redis cli you can type redis commands
* To get keys 
```
keys * 
```
* Get value of a key
```
get keyName
```
* View ttl
```
ttl keyName
```

Refer [https://redis.io/commands]

## Configuring AWS Services (SNS - Simple Notification Service and SES - Simple Email Service)

- For both services to work you need to add your aws credentials to verify your aws account. Follow the below documentation

  [https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html]

- Define the sender email address and subject in below properties located in <otp-service>/src/main/resources/application.properties file

* otp.email.sender=
* otp.email.subject=

## Configuring the micro-service

* Add the password to redis server in <otp-service>/src/main/resources/application.yml
* Fill out the relevant configurations in application.yml and bootstrap.yml in src/main/resources
  before building the application

## Dependencies

All dependencies are available in pom.xml.

## Build
```
mvn clean compile package
```

## Run

```
mvn spring-boot:run
```

or

```
java -jar target/otp-service-1.0.0-SNAPSHOT.jar
```

## Test
```
mvn test
```

### Reference Documentation
For further reference, please consider the following sections:

* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)


## License

Copyright (c) Swivel Technology (PVT) Ltd
