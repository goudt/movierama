create table users
(
    id identity,
    name varchar2(100) NOT NULL UNIQUE ,
    password varchar2 NOT NULL ,
    constraint USERS_PK
        primary key (id)
);

create table movies
(
    id identity,
    title varchar2(150) not null,
    description varchar2,
    user_id long not null,
    created_at datetime,
    constraint MOVIE_PK
        primary key (id),
    constraint OWNER__FK
        foreign key (user_id) references users (id)
);

create table votes
(
    id identity ,
    user long not null,
    movie long not null,
    rating int not null,
    constraint VOTES_PK
        primary key (id),
    constraint MOVIES__FK
        foreign key (movie) references MOVIES(id),
    constraint "USER__FK"
        foreign key (user) references USERS(id)
);

CREATE FORCE VIEW VIEW_MOVIES(ID, TITLE, DESCRIPTION, CREATED_AT, USER_ID, USER_NAME, LIKES, HATES) AS
SELECT M.ID,
       M.TITLE,
       M.DESCRIPTION,
       M.CREATED_AT,
       M.user_id,
       u.name as user_name,
       (SELECT COUNT(V.ID)
        FROM VOTES V
        WHERE ( V . RATING  = 1)
          AND (M.ID = V.MOVIE)) AS LIKES,
       (SELECT COUNT(V.ID)
        FROM VOTES V
        WHERE (V.RATING = 0)
          AND (M.ID = V.MOVIE)) AS HATES
FROM MOVIES M
         left outer join users u on u.id = m.user_id;
