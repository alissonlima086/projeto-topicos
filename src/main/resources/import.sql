-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into physicalPerson (cpf, gender) values('4852169852', 1);
insert into physicalPerson (cpf, gender) values('5165561651', 1);
insert into physicalPerson (cpf, gender) values('4852146852', 2);

insert into user_table (username, email, `password`, `profile`, id_physicalPerson) values('Alisson', 'alisson@mail.com', 'NuCgY6/GPMQTMdNiush/UNx86FJs4rFVBcCfuzRRIREuEbf42eMqkc+ex10zbq4TK4fvrcJUpNH85V1+nUEcJg==', 2, 2);
insert into user_table (username, email, `password`, `profile`, id_physicalPerson) values('Frederico', 'fred@wetmail.com', 'NuCgY6/GPMQTMdNiush/UNx86FJs4rFVBcCfuzRRIREuEbf42eMqkc+ex10zbq4TK4fvrcJUpNH85V1+nUEcJg==', 2, 1);
insert into user_table (username, email, `password`, `profile`, id_physicalPerson) values('Joao', 'joao@mail.com', 'NuCgY6/GPMQTMdNiush/UNx86FJs4rFVBcCfuzRRIREuEbf42eMqkc+ex10zbq4TK4fvrcJUpNH85V1+nUEcJg==', 1, 3);

insert into person(`name`) values ('Frederico Santos');
insert into person(`name`) values ('Alisson Luís');
insert into person(`name`) values ('André Pagode');

insert into phone(areaCode, `number`, id_user) values('63', '2222222', 1);
insert into phone(areaCode, `number`, id_user) values('63', '777567575', 2);
insert into phone(areaCode, `number`, id_user) values('63', '2232535', 3);

insert into phone(areaCode, `number`, id_user) values('63', '75594748', 1);
insert into phone(areaCode, `number`, id_user) values('63', '5353535353', 2);


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


insert into publisher (`name`) values('DC');
insert into publisher (`name`) values('Marvel');

insert into author_table (`name`, email) values('Frederic Vertman', 'Emaildecontato@gmail.com');
insert into author_table (`name`, email) values('Arison Limão', 'contateaqui@yahoo.com');

insert into comic(`name`, price, inventory, numPages, `binding`, id_publisher, id_author) values('Comic teste 1', 30, 100, 200, 1, 1, 1);
insert into comic(`name`, price, inventory, numPages, `binding`, id_publisher, id_author) values('Comic teste 2', 60, 50, 300, 2, 2, 2);
insert into comic(`name`, price, inventory, numPages, `binding`, id_publisher, id_author) values('Comic teste 3', 60, 50, 300, 2, 2, 2);
insert into comic(`name`, price, inventory, numPages, `binding`, id_publisher, id_author) values('Comic teste 4', 60, 50, 300, 1, 2, 2);
insert into comic(`name`, price, inventory, numPages, `binding`, id_publisher, id_author) values('Comic teste 5', 60, 50, 300, 2, 2, 2);

insert into payment(`value`, confirmationPayment, dateConfimationPayment) values(0, true, '2023-05-22');
insert into pix(id, `name`, cpf, dateExpirationTokenPix) values (1,'Joao', '5165561651', '2023-05-22');

insert into order_table(totalOrder, id_address, id_user, id_payment) values(0, 1, 2, 1);

insert into itemOrder(id_order, quantity, totalItem, idBuyed, id_user, id_product) values (1, 5, 12.50, true , 1, 1);
insert into itemOrder(id_order, quantity, totalItem, idBuyed, id_user, id_product) values (1, 5, 12.50, false , 2, 2);


