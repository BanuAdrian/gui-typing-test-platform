-- DROP TABLE public.typing_tests;
-- DROP TABLE public.user_achievements;
-- DROP TABLE public.user_challenges;
-- DROP TABLE public.notifications;
-- DROP TABLE public.users;
-- DROP TABLE public.texts;
-- DROP TABLE public.achievements;
-- DROP TABLE public.challenges;

CREATE TABLE public.users (
    id serial NOT NULL,
    username varchar(25) NOT NULL,
    CONSTRAINT users_username_uk UNIQUE (username),
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE public.texts (
    id serial NOT NULL,
    text_category varchar(25) NOT NULL,
    content varchar(250) NOT NULL,
    CONSTRAINT texts_pk PRIMARY KEY (id)
);


CREATE TABLE public.typing_tests (
    id serial NOT NULL,
    user_id integer NOT NULL,
    text_id integer NOT NULL,
    wpm integer NOT NULL,
    correct_words integer NOT NULL,
    CONSTRAINT typing_tests_pk UNIQUE (id),
    CONSTRAINT typing_tests_users_fk FOREIGN KEY (user_id) REFERENCES public.users,
    CONSTRAINT typing_tests_texts_fk FOREIGN KEY (text_id) REFERENCES public.texts
);


CREATE TABLE public.achievements (
    id serial NOT NULL,
    name varchar(25) NOT NULL,
    description varchar(100) NOT NULL,
    achievement_type varchar(25) NOT NULL,
    required_wpm integer,
    required_tests integer,
    CONSTRAINT achievements_pk PRIMARY KEY (id)
);

CREATE TABLE public.notifications (
    id serial NOT NULL,
    user_id integer NOT NULL,
    message varchar(100) NOT NULL,
    CONSTRAINT notifications_pk PRIMARY KEY (id),
    CONSTRAINT notifications_users_fk FOREIGN KEY (user_id) REFERENCES public.users
);

CREATE TABLE public.challenges (
    id serial NOT NULL,
    name varchar(25) NOT NULL,
    description varchar(100) NOT NULL,
    challenge_type varchar(25) NOT NULL,
    score integer NOT NULL,
    target_wpm integer,
    target_seconds integer,
    CONSTRAINT challenges_pk PRIMARY KEY (id)
);

CREATE TABLE public.user_challenges (
	id serial NOT NULL,
    user_id integer NOT NULL,
    challenge_id integer NOT NULL,
    is_completed boolean NOT NULL,
    CONSTRAINT user_challenges_pk PRIMARY KEY (id),
    CONSTRAINT user_challenges_users_fk FOREIGN KEY (user_id) REFERENCES public.users,
    CONSTRAINT user_challenges_challenges_fk FOREIGN KEY (challenge_id) REFERENCES public.challenges
);

CREATE TABLE public.user_achievements (
    user_id integer NOT NULL,
    achievement_id integer NOT NULL,
    CONSTRAINT user_achievements_pk PRIMARY KEY (user_id, achievement_id),
    CONSTRAINT user_achievements_users_fk FOREIGN KEY (user_id) REFERENCES public.users,
    CONSTRAINT user_achievements_achievements_fk FOREIGN KEY (achievement_id) REFERENCES public.achievements
);

create table public.challenge_texts (
	challenge_id serial not null,
	text_id serial not null,
	constraint challenge_texts_pk primary key (challenge_id, text_id),
	constraint challenge_texts_texts_fk foreign key (text_id) references texts,
	constraint challenge_texts_challenges_fk foreign key (challenge_id) references challenges
);
