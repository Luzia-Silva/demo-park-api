-- Criando usuários
insert into users (id, username, password, role) values (100, 'aluziagabriela@gmail.com', '$2a$12$ti2fVqtHZR4ctuuIFRGJ6OUyq479VfeiDL.cM1zOYDZuJnLWTPWFK', 'ROLE_ADMIN');
insert into users  (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$ti2fVqtHZR4ctuuIFRGJ6OUyq479VfeiDL.cM1zOYDZuJnLWTPWFK', 'ROLE_CUSTOMER');
insert into users (id, username, password, role) values (102, 'aluziagabrielaTwo@gmail.com', '$2a$12$ti2fVqtHZR4ctuuIFRGJ6OUyq479VfeiDL.cM1zOYDZuJnLWTPWFK', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (103, 'anaflor@gmail.com', '$2a$12$ti2fVqtHZR4ctuuIFRGJ6OUyq479VfeiDL.cM1zOYDZuJnLWTPWFK', 'ROLE_CUSTOMER');

--Criando Clientes
insert into customers (id, name, cpf, id_user) values (10, 'Luzia Gabriela Abreu da Silva Santos', '49126010046', 100);
insert into customers (id, name, cpf, id_user) values (11, 'Beatriz Dias Santos', '20352841044', 101);
