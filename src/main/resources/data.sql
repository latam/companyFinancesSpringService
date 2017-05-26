INSERT INTO USER(ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, ROLES, USER_NAME) VALUES(1, 'admin@test.pl', 'Mateusz', 'Łata', '$2a$10$kKu3HtEp5PJz7veNznACMeg18yQWBfNNrzYhKhP6FBQMcH9i2Roby', 'ADMIN', 'TestUser');

INSERT INTO COMPANY(ID, USER_ID, NAME, CITY, POSTAL_CODE, STREET, NIP, REGON, CONTRACTOR, ACTIVE) VALUES
(1, 1, 'Mateusz Łata', 'Łęg Tarnowski', '33-131', 'ul. Dolna 29', '8133708831', '362594660', FALSE, TRUE),
  (2, 1, 'ISD DESIGN POLAND Sp. z o.o.', 'Rzeszów', '35-307', 'ul. Bohaterów 10 Studenckiej Dywizji Piechoty 5', '9512291330', '141957645', TRUE, TRUE),
  (3, 1, 'EURO-NET Sp. z o.o.', 'Warszawa', '02-273', 'ul. Muszkieterów 15', '5270005984', '', TRUE, TRUE);