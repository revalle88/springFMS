- Устанавливаем posgresql:
```
sudo apt-get install postgresql postgresql-contrib
```

- Создаем базу данных и привязываем к ней пользователя:
```
sudo su - postgres
createdb fms
psql -s fms
create user fmsuser password 'dance7';
GRANT ALL PRIVILEGES ON DATABASE fms TO fmsuser;
```
(На Windows можно установить postgres через инсталлер и через pgadmin создать базу fms и пользователя  user fmsuser password 'dance7')

- Клонируем репозиторий с гитхаба:
```
git clone https://github.com/revalle88/springFMS.git
```

- Переходим в корневую папку репозитория (где лежит pom.xml) и запускаем сборку проекта:
```
cd springFMS/
mvn clean install
```
(если тесты валятся, то .. =)
```
mvn clean install -DskipTests)
```
- Запускаем приложение из jar файла.
```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

- Запускаем приложение в браузере:
```
http://localhost:8080/
```