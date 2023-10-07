-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into user_table (username, email, `password`) values('Alisson', 'alisson@mail.com', '12345');
insert into user_table (username, email, `password`) values('Frederico', 'fred@wetmail.com', '54321');

insert into genre(`name`) values('Romance');
insert into genre(`name`) values('Fantasia');
insert into genre(`name`) values('Ficção');
insert into genre(`name`) values('Ficção Cientifica');
insert into genre(`name`) values('Yaoi');

insert into `state`(`name`, abbreviation) values('Tocantins', 'TO');
insert into `state`(`name`, abbreviation) values('São Paulo', 'SP');
insert into `state`(`name`, abbreviation) values('Rio De Janeiro', 'RJ');
insert into `state`(`name`, abbreviation) values('Acre', 'AC');

insert into city(`name`, `id_state`) values('Palmas', 1);
insert into city(`name`, `id_state`) values('Araguaína', 1);
insert into city(`name`, `id_state`) values('Caraguatatuba', 2);
insert into city(`name`, `id_state`) values('São Paulo', 2);

insert into `address`(`name`, postalCode, `address`, complement, id_city, id_user) values('Casa', '0000000', 'Avenida 5 casa 5', 'Em frente ao bar do zé', 1, 1);
insert into `address`(`name`, postalCode, `address`, complement, id_city, id_user) values('Trabalho', '11111111', 'Rua almeida, predio 12', 'sede tal', 1, 1);

insert into `address`(`name`, postalCode, `address`, complement, id_city, id_user) values('Casa', '55555440', 'Avenida santa hora ap 22b', 'condominio tal', 1, 2);
