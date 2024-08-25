# Сервис для доступа к свойствам

Источник: Рустам Курамшин — Правильный DevOps для Spring Boot и Java: https://www.youtube.com/watch?v=oKaYqfIevaM&t=6s

- воможность динамического изменения пропертей в сервисах;
- централизованное хранение в git, файле или vault;
- не существует защиты от дурака, неправильно выставленные проперти могут уронить сервис-клиент;
- не все проперти можно изменить, например, подключение к БД.

## Действия
В конфигурационном сервисе:
1) Добавить зависимость <spring-cloud-config-server>;
2) Прописать в application.yml путь к хранилищу;
3) Рядом с @SpringBootApplication прописать @EnableConfigServer.

В клиентском сервисе:
1) Добавить зависимость <spring-cloud-config-server>;
2) Прописать spring.profiles.active и spring.application.name так, чтобы в хранилище совпадал с {application} и {profile} 
3) Определить дефолтные проперти, необходимые проперти для запуска приложения;
5) Прописать адрес сервера конфигурации:
```
  spring.config.import='optional:configserver:http://localhost:8888'
```

## Динамическое изменение пропертей в конфиг сервере
Ручное управление:
1) добавьте зависимость Spring Boot Actuator
2) прописать свойства:
```
management:
  endpoints:
    web:
      exposure:
        include: refresh
```
3) отправьте POST-запрос на эндпоинт /actuator/refresh для обновления конфигурации:
```
curl -X POST http://your-config-server-url/actuator/refresh
```

Автоматическое изменение(пока для гитхаба):
1) Создайте Webhook в GitHub:

Перейдите в настройки вашего репозитория на GitHub.
Перейдите в раздел "Webhooks".
Добавьте новый Webhook с URL вашего Spring Cloud Config Server (например, http://your-config-server-url/monitor).
Убедитесь, что Webhook настроен на отправку событий push.
Настройте Spring Cloud Config Server для обработки Webhooks:

2) Добавьте зависимость для Spring Cloud Config Monitor в ваш pom.xml;

3) Настройте Spring Cloud Config Server для обработки Webhooks в application.yml:

```
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-username/your-repo.git
          clone-on-start: true
        monitor:
          git:
            enabled: true
```

## Динамическое изменение свойств клиента
