# TwittApp
TwittApp - это приложение основанное на микросервисной расприделительной архитектуре осуществляющее общение между микросервисами через Apache Kafka, использующее S3 Minio для хранения картинок и Redis + ElastiSearch для ленты постов.
Предоставляет функции: авторизации, аутентификации, работа со своим профилем, подписки на другие профили, работа с постами, просмотр ленты постов и email notifications


# Stack 
- Spring: boot, cloud, web, data, security
- redis
- ElasticSearch
- Apache Kafka
- Postgres
- Gradle
- S3 minio 
	

# Architecture

<details>

<summary>Architecture:</summary>

## Основные микросервисы :
- auth service - регистрация и аутентификация пользователей 
- profile service - управление профилем
- post service - управление постами, лайками, дизлайками и комментариями
- email notification service - отправка уведомлений на эл. почту
- newsfeed service - предоставление ленты постов 
- subscribe service - управление подписками пользователей

## Вспомогательные микросервисы : 
- Discovery service - Используется для обнаружения микросервисов, использует netflix eureka
- Configuration management - Управление конфигурациями микросервисов, использует spring cloud config server
- Api Gateway - Центральная точка входа для всех запросов, использует spring cloud api gateway

</details>
 

## Manual
you're up,  backend swagger is available at a localhost:8080/swagger-ui/index.html
For sign in use by default: `user` `user`


## get started
```
$ git clone salfjalj
$ cd twittApp/docker
$ docker-compose up   
```