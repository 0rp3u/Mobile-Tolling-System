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

insert into mts_vehicle(id, plate, tier, user_id)
values (200, 'BB-00-00', 'Classe_2', 400);

insert into mts_vehicle(id, plate, tier, user_id)
values (300, 'CC-00-00', 'Classe_3', 400);

insert into mts_vehicle(id, plate, tier, user_id)
values (400, 'DD-00-00', 'Classe_1', 200);

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

/* tolls */
insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (100, 'Portagem Lisboa', true, -9.3407711, 38.7054309,
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
    ),
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
    )
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (200, 'Portagem Porto', true, -8.454753, 41.183728,
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
),
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
)
);


insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (300, ' Portagem de Mem Martins', true, -9.331716299057007, 38.818052036825385,
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
),
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
)
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (400, 'Portagem da Abrunheira', true, -9.36434268951416, 38.77861880578469,
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
),
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
)
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (500, 'Portagem de Agualva', true, -9.27497148513794, 38.771249831496874,
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
),
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
)
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (600, 'Portagem Oeiras', true, -9.301723837852478, 38.71172228726123,
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
),
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
)
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (700, 'Ponte 25 de abril', false, -9.173930, 38.675975,
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.17434573173523, 38.67568962607195),
		ST_MakePoint(-9.173959493637085, 38.67445835004221),
		ST_MakePoint(-9.173364043235779, 38.674496042479824),
		ST_MakePoint(-9.173412322998047, 38.67580270138017),
		ST_MakePoint(-9.17431354522705, 38.67570219000391),
		ST_MakePoint(-9.17434573173523, 38.67568962607195)
        ]
    )
),
ST_MakePolygon(
    ST_MakeLine(
        ARRAY[

		ST_MakePoint(-9.174426198005676, 38.6760539792034),
		ST_MakePoint(-9.174394011497498, 38.6760539792034),
		ST_MakePoint(-9.173412322998047, 38.67622987315479),
		ST_MakePoint(-9.173637628555296, 38.67748624595158),
		ST_MakePoint(-9.17457103729248, 38.67739830057364),
		ST_MakePoint(-9.174426198005676, 38.6760539792034)
        ]
    )
)
);

insert into mts_toll(id, name, toll_type, geolocation_longitude, geolocation_latitude, entry_area, exit_area)
values (800, 'Palmela', true, -8.888651, 38.584453,
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
),
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
)
);

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
