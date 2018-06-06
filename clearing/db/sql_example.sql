-- Up

/*Creates*/

CREATE TABLE User (
    NIF INTEGER PRIMARY KEY,
    name TEXT,
    password TEXT,
    phone_number TEXT,
    state TEXT
);

CREATE TABLE Vehicle (
    id INTEGER PRIMARY KEY,
    plate TEXT UNIQUE,
    category TEXT,
    owner_NIF INTEGER,
    FOREIGN KEY(owner_NIF) REFERENCES User(NIF)
        ON UPDATE NO ACTION ON DELETE CASCADE
);


/*Inserts*/

INSERT INTO User (NIF, name, password, phone_number, state) VALUES 
(1, 'Alice', 'pass', '11111111', 'state');

INSERT INTO User (NIF, name, password, phone_number, state) VALUES 
(2, 'Bob', 'pass', '22222222', 'state');

INSERT INTO Vehicle (id, plate, category, owner_NIF) VALUES 
(1, 'plate1', 'cat 1', 1);

INSERT INTO Vehicle (id, plate, category, owner_NIF) VALUES 
(1, 'plate1', 'cat 1', 1);

-- Down
DROP TABLE Vehicle;
DROP TABLE User;