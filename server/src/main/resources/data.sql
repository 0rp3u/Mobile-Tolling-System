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
values (100, 'AA-00-00', 'Classe_1', 100);

insert into mts_vehicle(id, plate, tier, user_id, updated)
values (200, 'BB-00-00', 'Classe_2', 400, '2018-09-2 10:00:00');

insert into mts_vehicle(id, plate, tier, user_id, updated)
values (300, 'CC-00-00', 'Classe_3', 400, '2018-09-2 10:00:00');

insert into mts_vehicle(id, plate, tier, user_id)
values (400, 'DD-00-00', 'Classe_1', 200);

insert into mts_vehicle(id, plate, tier, user_id)
values (1100, 'DA-00-00', 'Classe_1', 200);

insert into mts_vehicle(id, plate, tier, user_id)
values (500, 'EE-00-00', 'Classe_2', 200);

insert into mts_vehicle(id, plate, tier, user_id)
values (600, 'FF-00-00', 'Classe_3', 300);

insert into mts_vehicle(id, plate, tier, user_id)
values (700, 'GG-00-00', 'Classe_1', 200);

insert into mts_vehicle(id, plate, tier, user_id)
values (800, 'HH-00-00', 'Classe_2', 200);

insert into mts_vehicle(id, plate, tier, user_id)
values (900, 'II-00-00', 'Classe_5', 200);

insert into mts_vehicle(id, plate, tier, user_id, updated)
values (1000, 'JJ-00-00', 'Classe_3', 400, '2018-09-1 10:00:00');

/* tolls */
insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (100, 'Portagem Lisboa', false, 'Brisa', -9.3407711, 38.7054309, '2018-09-1 10:00:00',
ST_SetSRID(
    ST_MakePolygon(
        ST_MakeLine(
            ARRAY[
            		ST_MakePoint(-9.340969920158386, 38.705288317650215),
                    ST_MakePoint(-9.340953826904297, 38.70479224585484),
                    ST_MakePoint(-9.340607821941376, 38.70479852526616),
                    ST_MakePoint(-9.340527355670929, 38.70526320017368),
                    ST_MakePoint(-9.340969920158386, 38.705288317650215)
            ]
        )
    ), 4326),
    ST_SetSRID(
    ST_MakePolygon(
        ST_MakeLine(
            ARRAY[
            		ST_MakePoint(-9.340913593769073, 38.705644147620035),
                    ST_MakePoint(-9.34051126241684, 38.705662985627896),
                    ST_MakePoint(-9.340559542179108, 38.70612137562299),
                    ST_MakePoint(-9.340881407260895, 38.706108817032145),
                    ST_MakePoint(-9.340913593769073, 38.705644147620035)
            ]
        )
    ), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (200, 'Portagem Porto', false, 'Brisa', -8.454753, 41.183728, '2018-09-1 10:00:00',
ST_SetSRID(
ST_MakePolygon(
     ST_MakeLine(
         ARRAY[

        ST_MakePoint(-8.45498263835907, 41.18445168669323),
        ST_MakePoint(-8.45496654510498, 41.18390665880255),
        ST_MakePoint(-8.454612493515015, 41.18391877058273),
        ST_MakePoint(-8.45474123954773, 41.18446379837263),
        ST_MakePoint(-8.45498263835907, 41.18445168669323)
         ]
     )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-8.454926311969757, 41.18352311793799),
		ST_MakePoint(-8.45484584569931, 41.182869074650725),
		ST_MakePoint(-8.454580307006836, 41.182899354576755),
		ST_MakePoint(-8.454596400260925, 41.183547341638),
		ST_MakePoint(-8.454926311969757, 41.18352311793799)
        ]
    )
), 4326)
);


insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (300, ' Portagem de Mem Martins', false, 'Brisa', -9.331716299057007, 38.818052036825385, '2018-09-2 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.331989884376526, 38.81816488689085),
		ST_MakePoint(-9.334419965744019, 38.81773856348301),
		ST_MakePoint(-9.334339499473572, 38.81737493267723),
		ST_MakePoint(-9.331861138343811, 38.81776364140118),
		ST_MakePoint(-9.331989884376526, 38.81816488689085)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.331501722335815, 38.81825265904038),
		ST_MakePoint(-9.33138906955719, 38.8178388751027),
		ST_MakePoint(-9.329232573509216, 38.81822758129442),
		ST_MakePoint(-9.329313039779663, 38.818566130119834),
		ST_MakePoint(-9.331501722335815, 38.81825265904038)
        ]
    )
), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (400, 'Portagem da Abrunheira', false,'Brisa', -9.36434268951416, 38.77861880578469, '2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.363977909088135, 38.778635533577564),
		ST_MakePoint(-9.363269805908203, 38.78021629229846),
		ST_MakePoint(-9.36384916305542, 38.78041702105738),
		ST_MakePoint(-9.364525079727173, 38.77881117516578),
		ST_MakePoint(-9.363977909088135, 38.778635533577564)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.364718198776245, 38.77843479980418),
		ST_MakePoint(-9.36558723449707, 38.77630196856888),
		ST_MakePoint(-9.365200996398926, 38.776176505921676),
		ST_MakePoint(-9.364042282104492, 38.77815878994327),
		ST_MakePoint(-9.364718198776245, 38.77843479980418)
        ]
    )
), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (500, 'Portagem de Agualva', false,'Brisa', -9.27497148513794, 38.771249831496874,'2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.274488687515259, 38.77160115061781),
		ST_MakePoint(-9.275003671646118, 38.770672660613364),
		ST_MakePoint(-9.273394346237183, 38.770170769092694),
		ST_MakePoint(-9.27294373512268, 38.77089851064566),
		ST_MakePoint(-9.274488687515259, 38.77160115061781)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.275486469268799, 38.77087341623293),
		ST_MakePoint(-9.27493929862976, 38.77175171542574),
		ST_MakePoint(-9.277160167694092, 38.77220340794261),
		ST_MakePoint(-9.277482032775879, 38.77177680952946),
		ST_MakePoint(-9.275486469268799, 38.77087341623293)
        ]
    )
), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (600, 'Portagem Oeiras',false, 'Brisa', -9.301723837852478, 38.71172228726123,'2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.301868677139282, 38.71184786321026),
		ST_MakePoint(-9.301385879516602, 38.71188553595197),
		ST_MakePoint(-9.301128387451172, 38.71267665894147),
		ST_MakePoint(-9.301772117614746, 38.71276456095542),
		ST_MakePoint(-9.301868677139282, 38.71184786321026)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.302061796188354, 38.711579967585664),
		ST_MakePoint(-9.301981329917908, 38.71058790785841),
		ST_MakePoint(-9.301562905311584, 38.71056279224359),
		ST_MakePoint(-9.30143415927887, 38.711504621760355),
		ST_MakePoint(-9.302061796188354, 38.711579967585664)
        ]
    )
), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude, azimuth, created, entry_area, exit_area)
values (700, 'Ponte 25 de abril', true, 'Luso Ponte', -9.173930, 38.675975, 6.05737304327142, '2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.174356460571289, 38.67581107732184),
		ST_MakePoint(-9.174163341522217, 38.67507399070451),
		ST_MakePoint(-9.173862934112549, 38.67437040094394),
		ST_MakePoint(-9.17332649230957, 38.67440390537509),
		ST_MakePoint(-9.173498153686523, 38.67592834040206),
		ST_MakePoint(-9.174356460571289, 38.67581107732184)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.173495471477509, 38.67603094543966),
		ST_MakePoint(-9.17404532432556, 38.677704014993786),
		ST_MakePoint(-9.174635410308838, 38.677653760658245),
		ST_MakePoint(-9.174385964870453, 38.67590949456284),
		ST_MakePoint(-9.173495471477509, 38.67603094543966)
        ]
    )
), 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude,created, entry_area, exit_area)
values (800, 'Palmela', false, 'Brisa', -8.888651, 38.584453,'2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-8.88963997364044, 38.584924757251414),
		ST_MakePoint(-8.889768719673157, 38.58463542014565),
		ST_MakePoint(-8.8889479637146, 38.584346081874145),
		ST_MakePoint(-8.888738751411438, 38.58474863915197),
		ST_MakePoint(-8.889607787132263, 38.584949916944645),
		ST_MakePoint(-8.88963997364044, 38.584924757251414)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-8.88963997364044, 38.584924757251414),
		ST_MakePoint(-8.889768719673157, 38.58463542014565),
		ST_MakePoint(-8.8889479637146, 38.584346081874145),
		ST_MakePoint(-8.888738751411438, 38.58474863915197),
		ST_MakePoint(-8.889607787132263, 38.584949916944645),
		ST_MakePoint(-8.88963997364044, 38.584924757251414)
        ]
    )
), 4326)
);


insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude, created, entry_area, exit_area)
values (900, 'Sete Rios Fake', false, 'Brisa', -9.166358, 38.740383,'2018-09-3 10:00:00',
ST_SetSRID(

ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.165816307067871, 38.740946231709216),
		ST_MakePoint(-9.165515899658203, 38.74010939542417),
		ST_MakePoint(-9.16285514831543, 38.74041065761647),
		ST_MakePoint(-9.16332721710205, 38.74114707095849),
		ST_MakePoint(-9.165816307067871, 38.740946231709216)
        ]
    )
), 4326),
ST_SetSRID(

ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.166996479034424, 38.74056128823605),
		ST_MakePoint(-9.168970584869385, 38.73965749975282),
		ST_MakePoint(-9.168455600738524, 38.73918886418447),
		ST_MakePoint(-9.16656732559204, 38.739858342626455),
		ST_MakePoint(-9.166996479034424, 38.74056128823605)
        ]
    )
)
, 4326)
);

insert into mts_toll(id, name, open_toll, concession, geolocation_longitude, geolocation_latitude, created, entry_area, exit_area)
values (1000, 'Benfica Fake', false, 'Brisa', -9.199633598327637, 38.74437715795277,'2018-09-3 10:00:00',
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.199204444885252, 38.744577987552525),
		ST_MakePoint(-9.199247360229492, 38.74400896888619),
		ST_MakePoint(-9.19731616973877, 38.74394202521559),
		ST_MakePoint(-9.197230339050291, 38.74451104441538),
		ST_MakePoint(-9.199204444885252, 38.744577987552525)
        ]
    )
), 4326),
ST_SetSRID(
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.199934005737305, 38.74464493062691),
		ST_MakePoint(-9.201393127441406, 38.74481228803831),
		ST_MakePoint(-9.201521873474121, 38.74431021462734),
		ST_MakePoint(-9.199891090393066, 38.74420979952151),
		ST_MakePoint(-9.199934005737305, 38.74464493062691)
        ]
    )
)
, 4326)
);

/* transactions */
insert into mts_transaction(id, state, vehicle, updated)
values (100, 'INCOMPLETE', 200, '2018-09-02 06:00:00');

insert into mts_transaction(id, state, vehicle, updated)
values (200, 'CONFIRMED', 300, '2018-10-02 06:00:00');

/* events */

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('BEGIN', '2018-06-24 06:00:00', 100, 100);

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('BEGIN', '2018-06-23 06:00:00', 100, 200);

insert into mts_event(type, event_date, toll_id, transaction_id)
values ('END', '2018-06-25 08:00:00', 100, 200);

insert into mts_transaction_event(transaction_id, event_toll_id, event_transaction_id, event_type)
values (100, 100, 100, 'BEGIN');
insert into mts_transaction_event(transaction_id, event_toll_id, event_transaction_id, event_type)
values (200, 100, 200, 'BEGIN');
insert into mts_transaction_event(transaction_id, event_toll_id, event_transaction_id, event_type)
values (200, 100, 200, 'END');