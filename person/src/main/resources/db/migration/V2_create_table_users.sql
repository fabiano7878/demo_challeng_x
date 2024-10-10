CREATE TABLE Users(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);

INSERT INTO Users (login, password, role) VALUES ('fabiano.lancer@gmail.com', '$2a$10$yGxds61zvkqbcnluZxmWhOee9k/N2g67ZDQ3m0MhQPD7wqAnCjMHO', 'DEVELOPER');
INSERT INTO Users (login, password, role) VALUES ('system@system.com', '$2a$10$eDED41FeUb3bBstrtPCYReIqVjmkpUl1zOoI2ufNvz/bMKpqZM46q', 'SYSTEM');
INSERT INTO Users (login, password, role) VALUES ('fabiano.asistemas@gmail.com', '$2a$10$AfDJ8LMsF1kA3vlB07..zuRNnQCtwJKrohdCXTC9lN9FYZ/qGzZyO', 'ADMIN');