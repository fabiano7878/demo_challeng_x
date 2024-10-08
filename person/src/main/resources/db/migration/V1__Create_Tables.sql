CREATE TABLE TB_GENDER (
    ID_GENDER INTEGER AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(10) NOT NULL
);

CREATE TABLE TB_PERSON (
    ID_PERSON INTEGER AUTO_INCREMENT PRIMARY KEY,
    ID_GENDER INTEGER NOT NULL,
    FULL_NAME VARCHAR(50) NOT NULL,
    BIRTHDATE TIMESTAMP,
    FOREIGN KEY (ID_GENDER) REFERENCES TB_GENDER(ID_GENDER) ON DELETE CASCADE
);


INSERT INTO TB_GENDER (NAME) VALUES ('MALE');
INSERT INTO TB_GENDER (NAME) VALUES ('FEMALE');

INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Jhon Silva', '1978-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'MALE'));
INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Jhon Santos', '1979-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'MALE'));
INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Mary Silva', '1986-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'FEMALE'));
INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Oswald Silva', '1950-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'MALE'));
INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Rose Silva', '1965-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'FEMALE'));
INSERT INTO TB_PERSON (FULL_NAME, BIRTHDATE, ID_GENDER) VALUES ('Rose Santos', '1966-12-10', (SELECT ID_GENDER FROM TB_GENDER WHERE NAME = 'FEMALE'));