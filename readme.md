# TwittApp
TwittApp — is an application based on a micro-service architecture that communicates between microservices via Apache Kafka, using S3 Minio to store images and Redis to cache posts.
Provides functions: authorization, authentication, working with your profile, subscribing to other profiles, working with posts, viewing the feed of posts and email notifications


# Stack 
- Java
- Spring: boot, cloud, web, data, security
- Redis
- Apache Kafka
- Postgres
- Gradle
- S3 minio 
	

# Architecture

<details>

<summary>Architecture:</summary>

## The main microservices :
- auth service - user registration and authentication 
- profile service - profile management
- post service - post management (selection of posts by various filters, pagination), likes, dislikes and comments
- email notification service - sending notifications to email
- subscribe service - manage user subscriptions

## The additional microservices : 
- Discovery service - Used to detect microservices, uses netflix eureka
- Configuration management - Microservices configuration management, uses spring cloud config server
- Api Gateway - Central entry point for all requests, uses spring cloud api gateway

</details>
 

## get started
```
$ git clone salfjalj
$ cd twittApp/docker
$ docker-compose up   
```
	you're up, backend address by default is: http://localhost:8080