/* USERS */

insert into mts_user(id, login, password_hash)
values (100, 'paulo', '$2a$10$Z86/.rH3mWsuJdWb8P5iue7itY3YiGtVZ0OjHieKPfYa5CpGztRye');


insert into mts_user(id, login, password_hash)
values (200, 'orpheu', '$2a$10$Z86/.rH3mWsuJdWb8P5iue7itY3YiGtVZ0OjHieKPfYa5CpGztRye');

insert into mts_user(id, login, password_hash)
values (300, 'red', '$2a$10$Z86/.rH3mWsuJdWb8P5iue7itY3YiGtVZ0OjHieKPfYa5CpGztRye');


insert into mts_user(id, login, password_hash)
values (400, '1234', '$2a$10$Z86/.rH3mWsuJdWb8P5iue7itY3YiGtVZ0OjHieKPfYa5CpGztRye');

/* ROLE */
insert into mts_role(id)
values ('USER');

insert into mts_role(id)
values ('ADMIN');


/* user_role */
insert into mts_user_roles(user_id, role_id)
values (100, 'USER');

insert into mts_user_roles(user_id, role_id)
values (200, 'USER');

insert into mts_user_roles(user_id, role_id)
values (300, 'USER');

insert into mts_user_roles(user_id, role_id)
values (400, 'USER');

/* vehicles */
insert into mts_vehicle(id, plate, tier, user_id)
values (100, 'AA-00-00', 'Classe_1', 400);

insert into mts_vehicle(id, plate, tier, user_id)
values (200, 'BB-00-00', 'Classe_2', 100);

insert into mts_vehicle(id, plate, tier, user_id)
values (300, 'CC-00-00', 'Classe_3', 400);

/* tolls */
insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (100, 'Portagem Lisboa', 'NORMAL', 38.7054309, -9.3407711);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (200, 'Portagem Porto', 'NORMAL', 38.7054309, -9.3407711);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (300, ' Portagem de Mem Martins', 'NORMAL', 38.8175118,-9.3332501);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (400, 'Portagem da Abrunheira', 'NORMAL', 38.7794866,-9.3606079);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (500, 'Portagem de Agualva', 'NORMAL', 38.7730868,-9.282046);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (600, 'Portagem Oeiras', 'NORMAL', 38.7118793,-9.3021738);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (700, 'Ponte 25 de abril', 'OPEN', 38.675975, -9.173930);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (800, 'Palmela', 'NORMAL', 38.584453, -8.888651);

/* transactions */
insert into mts_transaction(id, state, vehicle)
values (100, 'INCOMPLETE', 100);

insert into mts_transaction(id, state, vehicle)
values (200, 'COMPLETE', 300);

/* events */

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('BEGIN', '2018-06-24 06:00:00', 100, 100);

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('BEGIN', '2018-06-23 06:00:00', 100, 200);

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('END', '2018-06-25 08:00:00', 100, 200);
