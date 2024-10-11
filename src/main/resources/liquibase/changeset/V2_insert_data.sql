--liquibase formatted sql
--changeset tasklist:2

insert into tasklist.users (name, username, password)
values ('John Doe', 'user-1@mail.ru', '$2a$10$Xl0yhvzLIaJCDdKBS0Lld.ksK7c2Zytg/ZKFdtIYYQUv8rUfvCR4W'),
       ('Mike Smith', 'user-2@mail.ru', '$2a$10$fFLij9aYgaNCFPTL9WcA/uoCRukxnwf.vOQ8nrEEOskrCNmGsxY7m');


insert into tasklist.tasks (title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2024-09-29 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2024-09-29 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2024-09-25 00:00:00');

--changeset tasklist:3
insert into tasklist.users_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);


insert into tasklist.users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');