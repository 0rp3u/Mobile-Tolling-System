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
insert into mts_vehicle(id, plate, type, user_id)
values (100, 'AA-00-00', 100, 400);

insert into mts_vehicle(id, plate, type, user_id)
values (200, 'BB-00-00', 200, 100);

insert into mts_vehicle(id, plate, type, user_id)
values (300, 'CC-00-00', 300, 400);

/* tolls */
insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (100, 'Portagem Lisboa', 'TWOWAY', 38.7054309, -9.3407711);

insert into mts_toll(id, name, toll_type, geolocation_latitude, geolocation_longitude)
values (200, 'Portagem Porto', 'TWOWAY', 38.7054309, -9.3407711);

/* trips */
insert into mts_trip(id, state, vehicle)
values (100, 'INCOMPLETE', 100);

insert into mts_trip(id, state, vehicle)
values (200, 'COMPLETE', 300);

/* transactions */

insert into mts_transaction(type, event_date, toll_id, trip_id)
values ('BEGIN', '2018-06-24 06:00:00', 100, 100);

insert into mts_transaction(type, event_date, toll_id, trip_id)
values ('BEGIN', '2018-06-23 06:00:00', 100, 200);

insert into mts_transaction(type, event_date, toll_id, trip_id)
values ('END', '2018-06-25 08:00:00', 100, 200);
