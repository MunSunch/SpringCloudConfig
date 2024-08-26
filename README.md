# Сервис для доступа к свойствам

Источник: Рустам Курамшин — Правильный DevOps для Spring Boot и Java: https://www.youtube.com/watch?v=oKaYqfIevaM&t=6s

Источник: https://sysout.ru/spring-cloud-configuration-server/

Источник: https://spring.io/guides/gs/centralized-configuration

- воможность динамического изменения пропертей в сервисах;
- централизованное хранение в git, файле или vault;
- не существует защиты от дурака, неправильно выставленные проперти могут уронить сервис-клиент;
- не все проперти можно изменить, например, подключение к БД.

## Действия
В конфигурационном сервисе:
1) Добавить зависимость spring-cloud-config-server;
2) Прописать в application.yml путь к хранилищу;
3) Рядом с @SpringBootApplication прописать @EnableConfigServer.

В клиентском сервисе:
1) Добавить зависимость spring-cloud-config-server;
2) Прописать spring.profiles.active и spring.application.name так, чтобы в хранилище совпадал с {application} и {profile} 
3) Определить дефолтные проперти, необходимые проперти для запуска приложения;
5) Прописать адрес сервера конфигурации:
```
  spring.config.import='optional:configserver:http://localhost:8888'
```

## Динамическое изменение свойств клиента
Конфигурационный сервер сам следит за обновлениями репозитория, причем обновляется мгновенно. Для клиентов необходимы доп настройки для получения новых свойств от конфиг сервера:

1) Добавить зависимость spring-boot-starter-actuator;
2) Открыть refresh-endpoint в свойствах = management.endpoints.web.exposure.include=refresh;
3) Добавить аннотацию @RefreshScope для бинов, использующих свойства;
4) Отправить запрос для обновления конфигов: curl -X POST localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"

После запроса произойдет обновление свойств, затем запускается отдельное событие для перезапуска бинов, аннотированных @RefreshScope.