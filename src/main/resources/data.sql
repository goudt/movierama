insert into USERS values(0,'Mitsis','$2a$12$3/V9TrWPZSMO0cqRlu6au.DtmWTq85TyjPdjDwFrBDOlVwuss40M.');
insert into USERS values(1,'Danikas','$2a$12$3/V9TrWPZSMO0cqRlu6au.DtmWTq85TyjPdjDwFrBDOlVwuss40M.');


INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (0, 'se7en', 'Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.', 1, '2022-02-03 20:03:37.000000');
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (1, 'dune', 'eature adaptation of Frank Herbert''s science fiction novel about the son of a noble family entrusted with the protection of the most valuable asset and most vital element in the galaxy.', 0, '2022-02-02 23:05:30.000000');
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (2, 'memento', 'A man with short-term memory loss attempts to track down his wife''s murderer.', 1, '2022-02-03 20:04:36.000000');
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (3, 'Fight Club', 'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.', 1, sysdate());
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (4, 'Alien', 'The crew of a commercial spacecraft encounter a deadly lifeform after investigating an unknown transmission.', 1, sysdate());
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (5, 'The Terminator', 'A human soldier is sent from 2029 to 1984 to stop an almost indestructible cyborg killing machine, sent from the same year, which has been programmed to execute a young woman whose unborn son is the key to humanity''s future salvation.', 0, '2021-02-02 23:05:30.000000');
INSERT INTO MOVIES (ID, TITLE, DESCRIPTION, USER_ID, CREATED_AT) VALUES (6, 'Star Trek', 'The brash James T. Kirk tries to live up to his father''s legacy with Mr. Spock keeping him in check as a vengeful Romulan from the future creates black holes to destroy the Federation one planet at a time.', 1, sysdate());


INSERT INTO VOTES (ID, USER, MOVIE, RATING) VALUES (0, 0, 2, 0);
INSERT INTO VOTES (ID, USER, MOVIE, RATING) VALUES (1, 0, 0, 1);


insert into USERS values(2,'Telly','$2a$12$3/V9TrWPZSMO0cqRlu6au.DtmWTq85TyjPdjDwFrBDOlVwuss40M.');
INSERT INTO VOTES (ID, USER, MOVIE, RATING) VALUES (3, 0, 4, 1);
INSERT INTO VOTES (ID, USER, MOVIE, RATING) VALUES (4, 0, 4, 1);
