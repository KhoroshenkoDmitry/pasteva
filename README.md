
# Pasteva

Небольшой yet-another pastebin-like проект. Сейчас в состоянии headless.



## Features

- JWT авторизация и аутентификация
- Создание анонимных записей



## Запуск локально

Копируем проект
```bash
  git clone https://github.com/KhoroshenkoDmitry/pasteva.git
```

Переходим в папку проекта
```bash
  cd pasteva
```

Сборка проекта

```bash
  ./gradlew build
```

Запуск проекта

```bash
  ./gradlew run
```


## Swagger (WIP)

Swagger UI находится на стандартном URL: `http://localhost:8080/swagger-ui/index.html`


## Переменные среды

Чтобы запустить проект, нужно установить следующие переменные среды:

`JWT-SECRET` `DB-PASSWORD` `DB-URL` `DB-USERNAME`

Также в `src/main/resources/logback-spring.xml` необходимо указать путь до директории, где будут храниться логи.


## TODO
- CI
- контейнеризация Docker 
- Grafana
- Shorter URL service
- Возможность менять имя/пароль пользователя
## License

[Apache License 2.0](http://www.apache.org/licenses/)

