create table if not exists users
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    full_name VARCHAR NOT NULL UNIQUE,
    birthday  INT    NOT NULL CHECK ( birthday > 1900 )
);

create table if not exists books
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title        VARCHAR NOT NULL,
    author       VARCHAR NOT NULL,
    published_at INT     NOT NULL,
    user_id      BIGINT REFERENCES users (id),
    rented_at    date
);

insert into books (title, author, published_at, user_id, rented_at)
VALUES ('Над пропастью во ржи', 'Джером Сэлинджер', 1951, 1, '2022-01-01'),
       ('День опричника', 'Владимир Сорокин', 2006, 2, '2022-01-01'),
       ('Тайные виды на гору Фудзи', 'Владимир Пелевин', 2018, 1, '2022-01-01'),
       ('Философия Java', 'Брюс Эккель', 2018, null, null),
       ('Психопатология обыденной жизни', 'Фрейд Зигмунд', 1904, null, null),
       ('Игра в бисер', 'Герман Гессе', 1943, 1, '2030-01-01'),
       ('Бытие и время', 'Мартин Хайдеггер', 1927, 3, '2022-01-01'),
       ('Book1', 'Author1', 1927, null, null),
       ('Book2', 'Author2', 1927, null, null),
       ('Book3', 'Author3', 1927, null, null),
       ('Book4', 'Author4', 1927, null, null),
       ('Book5', 'Author5', 1927, null, null),
       ('Book6', 'Author6', 1927, null, null),
       ('Book7', 'Author7', 1927, null, null),
       ('Book8', 'Author8', 1927, null, null),
       ('Book9', 'Author9', 1927, null, null),
       ('Book10', 'Author10', 1927, null, null),
       ('Book11', 'Author1', 1927, null, null),
       ('Book12', 'Author2', 1927, null, null),
       ('Book13', 'Author3', 1927, null, null),
       ('Book14', 'Author4', 1927, null, null),
       ('Book15', 'Author5', 1927, null, null),
       ('Book16', 'Author6', 1927, null, null),
       ('Book17', 'Author7', 1927, null, null),
       ('Book18', 'Author8', 1927, null, null),
       ('Book19', 'Author9', 1927, null, null),
       ('Book20', 'Author10', 1927, null, null),
       ('Book21', 'Author1', 1927, null, null),
       ('Book22', 'Author2', 1927, null, null),
       ('Book23', 'Author3', 1927, null, null),
       ('Book24', 'Author4', 1927, null, null),
       ('Book25', 'Author5', 1927, null, null),
       ('Book26', 'Author6', 1927, null, null),
       ('Book27', 'Author7', 1927, null, null),
       ('Book28', 'Author8', 1927, null, null),
       ('Book29', 'Author9', 1927, null, null),
       ('Book30', 'Author10', 1927, null, null);
