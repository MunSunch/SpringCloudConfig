# Сервис для доступа к свойствам

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