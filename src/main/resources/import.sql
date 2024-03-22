INSERT INTO roles (name) VALUES ('COMMON_USER');
INSERT INTO roles (name) VALUES ('CREATOR');
INSERT INTO roles (name) VALUES ('ADMINISTRATOR');

--password 123
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('user1', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 1, 'Артем', 'Михалков');
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('user2', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 1, 'Василий', 'Дидляков');
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('user3', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 1, 'Кирилл', 'Тутцев');
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('user4', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 1, 'Анатолий', 'Овальный');
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('creator1', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 2, 'Максим', 'Сотин');
INSERT INTO users (username, password, role, first_name, last_name) VALUES ('administrator1', '$2a$10$ybvrwq9fGFHllwIG5iK7reLz2K8jYcxS2IMV1nUMva3U7La0HqOG6', 3, 'Сергей', 'Путин');

INSERT INTO tags (name) VALUES ('образовательное'); --1
INSERT INTO tags (name) VALUES ('научное'); --2
INSERT INTO tags (name) VALUES ('культурное'); --3
INSERT INTO tags (name) VALUES ('развлекательное'); --4
INSERT INTO tags (name) VALUES ('благотворительное'); --5
INSERT INTO tags (name) VALUES ('для детей'); --6
INSERT INTO tags (name) VALUES ('для молодежи'); --7
INSERT INTO tags (name) VALUES ('для взрослых'); --8
INSERT INTO tags (name) VALUES ('семинар'); --9
INSERT INTO tags (name) VALUES ('конференция'); --10
INSERT INTO tags (name) VALUES ('мастер-класс'); --11
INSERT INTO tags (name) VALUES ('выставка'); --12
INSERT INTO tags (name) VALUES ('концерт'); --13
INSERT INTO tags (name) VALUES ('спортивное'); --14

INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Чайкиной', '13');
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Зубковой', '12'); --2 Дворец спорта «Олимпийский»
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Завражнова', '4'); --3 Deep
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Костычева', '1'); --4 Дворец культуры РГАТУ
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Ленина', '26'); --5 Филармония
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Первомайский просп.', '68/2'); --6 Муниципальный культурный центр
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'Соборная пл.', '21'); --7 Свобода
INSERT INTO locations (city, street, house) VALUES ('Рязань', 'пр. Яблочкова', '5е'); --8 конференц зал

INSERT INTO events (name, description, location_id, date, time) VALUES ('Выставка «100 лет тому назад»', 'Архивные фотографии города и района. Было/стало. Предметы быта того времени, истории о жителях – как простых, так и знаменитых.', 6, '2024-04-05', '12:00:00');
INSERT INTO events (name, description, location_id, date, time) VALUES ('Выставка «100 лет тому назад»', 'Архивные фотографии города и района. Было/стало. Предметы быта того времени, истории о жителях – как простых, так и знаменитых.', 6, '2024-04-06', '12:00:00');
INSERT INTO events (name, description, location_id, date, time) VALUES ('Концерт «Музыка русской души»', 'Исполнение романсов под аккомпанемент живой музыки.', 5, '2024-05-12', '18:00:00');
INSERT INTO events (name, description, location_id, date, time) VALUES ('Фристайл-баттл «На районе»', 'Участники сочиняют рэп в режиме живого времени на заданную ведущим тему. Зрители определяют победителя.', 3, '2024-04-10', '16:00:00');
INSERT INTO events (name, description, location_id, date, time) VALUES ('Семинар «Проектирование игр»', 'Участники узнают, какие бывают игровые механики, как поможет в создании игр математика и теория вероятностей. Создадут свою первую механику для квеста', 8, '2024-04-21', '08:00:00');
INSERT INTO events (name, description, location_id, date, time) VALUES ('Спортивная эстафета', 'Приглашаем детей и взрослых для участия в легкоатлетической спортивной эстафете', 2, '2024-04-05', '10:00:00');

INSERT INTO event_tags (event_id, tag_id) VALUES (1, 1);
INSERT INTO event_tags (event_id, tag_id) VALUES (1, 3);
INSERT INTO event_tags (event_id, tag_id) VALUES (1, 12);
INSERT INTO event_tags (event_id, tag_id) VALUES (2, 1);
INSERT INTO event_tags (event_id, tag_id) VALUES (2, 3);
INSERT INTO event_tags (event_id, tag_id) VALUES (2, 12);
INSERT INTO event_tags (event_id, tag_id) VALUES (3, 13);
INSERT INTO event_tags (event_id, tag_id) VALUES (3, 3);
INSERT INTO event_tags (event_id, tag_id) VALUES (3, 4);
INSERT INTO event_tags (event_id, tag_id) VALUES (4, 7);
INSERT INTO event_tags (event_id, tag_id) VALUES (4, 4);
INSERT INTO event_tags (event_id, tag_id) VALUES (5, 9);
INSERT INTO event_tags (event_id, tag_id) VALUES (5, 2);
INSERT INTO event_tags (event_id, tag_id) VALUES (5, 1);
INSERT INTO event_tags (event_id, tag_id) VALUES (6, 14);
INSERT INTO event_tags (event_id, tag_id) VALUES (6, 6);
INSERT INTO event_tags (event_id, tag_id) VALUES (6, 4);

INSERT INTO participants (event_id, user_id, subscribed) VALUES (1, 1, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (1, 2, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (2, 2, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (2, 3, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (3, 4, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (4, 1, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (4, 2, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (5, 3, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (5, 4, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (5, 1, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (6, 2, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (6, 3, true);
INSERT INTO participants (event_id, user_id, subscribed) VALUES (6, 4, true);