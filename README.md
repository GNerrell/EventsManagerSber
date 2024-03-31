Для старта программы:
  1. Склонировать себе репозиторий
  2. Создать свою базу данных
  3. В файле application.yaml изменить spring.datasource.url на свою, также поменять username и password
  4. Запустить проект
  5. Перейти на адрес http://localhost:8080/login
Если требуется не пересоздавать базу при каждом запуске программы то следует поменять атрибут ddl-auto:
![image](https://github.com/GNerrell/EventsManagerSber/assets/106912430/7d7825cd-d83a-4480-affc-71a88553113c)
Структура базы данных проекта:
![image](https://github.com/GNerrell/EventsManagerSber/assets/106912430/d99b2824-df51-4f20-b597-12152fb9bea9)
Все данные записанные в БД храняться в файле import.sql
Данные для тестирования:
    Пароль для всех пользователей: 123
  Логины и роли:
     user1 COMMON_USER
     user2 COMMON_USER
     user3 COMMON_USER
     user4 COMMON_USER
     creator1 CREATOR
