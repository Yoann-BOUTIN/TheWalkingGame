create database if not exists thewalkinggame
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci; 
USE thewalkinggame;

drop table if exists answer;
drop table if exists question;
drop table if exists user;
drop table if exists invitation;
drop table if exists reanimer;
drop table if exists headupsidedown;
drop table if exists simon;
drop table if exists findkeys;
drop table if exists Team;
drop table if exists objective;
drop table if exists game;
drop table if exists gamefinished;


create table if not exists question(
	question_id integer auto_increment, 
	question_text varchar(500) not null,
	question_multiple_choice boolean,
	primary key (question_id) 
);
create table if not exists answer(
	answer_id integer auto_increment,
	answer_text varchar(500) not null,
	correct_answer boolean not null,
	question_id integer not null,
	constraint pk_answer primary key (answer_id),
	constraint fk_answer_question foreign key (question_id) references question(question_id)
);

create table if not exists user(
	user_id integer auto_increment,
	user_pseudo varchar(50) unique,
	user_score integer not null,
	user_pos_longitude double not null,
	user_pos_latitude double not null,
	constraint pk_user primary key (user_id)
);

create table if not exists invitation(
    id integer primary key auto_increment,
    inviteur varchar(50),
    invite varchar(50),
    date_invitation timestamp default now()
);

create table if not exists Team(
	Team_id integer auto_increment,
	joueur_1 varchar(50) unique,
	joueur_2 varchar(50),
	joueur_3 varchar(50),
	joueur_4 varchar(50),
	Team_score integer not null,
	constraint pk_Team primary key (Team_id)
);

create table if not exists objective(
	objective_id integer auto_increment,
	latitude double,
	longitude double,
	constraint pk_objective primary key (objective_id)
);

create table if not exists reanimer(
	id integer auto_increment,
	score_target integer not null,
	temps_autorise integer not null,
	constraint pk_reanimer primary key (id)
);

create table if not exists headupsidedown(
	id integer auto_increment,
	temps_autorise integer not null,
	constraint pk_headupsidedown primary key(id)
);

create table if not exists simon(
	id integer auto_increment,
	seq_length integer not null,
	constraint pk_simon primary key(id)
);

create table if not exists findkeys(
	id_find_key integer auto_increment,
	limite_time integer not null,
	constraint pk_findkey primary key(id_find_key)
);

create table if not exists game(
	game_id integer auto_increment,
	id_team_inviteur integer not null,
	id_team_invite integer,
	score_team_inviteur integer not null,
	score_team_invite integer not null,
	constraint pk_game primary key(game_id)
);

create table if not exists gamefinished(
	id integer auto_increment,
	joueur varchar(50) unique,
	finish integer not null,
	constraint pk_gameFinished primary key(id)
);

ALTER TABLE game ALTER score_team_inviteur SET DEFAULT 0;
ALTER TABLE game ALTER score_team_invite SET DEFAULT 0;
ALTER TABLE findkeys ALTER limite_time SET DEFAULT 0;
ALTER TABLE simon ALTER seq_length SET DEFAULT 0;
ALTER TABLE headupsidedown ALTER temps_autorise SET DEFAULT 0;
ALTER TABLE reanimer ALTER score_target SET DEFAULT 0;
ALTER TABLE reanimer ALTER temps_autorise SET DEFAULT 0;
ALTER TABLE Team ALTER joueur_2 SET DEFAULT 'Joueur manquant';
ALTER TABLE Team ALTER joueur_3 SET DEFAULT 'Joueur manquant';
ALTER TABLE Team ALTER joueur_4 SET DEFAULT 'Joueur manquant';
ALTER TABLE Team ALTER Team_score SET DEFAULT 0;
ALTER TABLE user ALTER user_score SET DEFAULT 0;
ALTER TABLE user ALTER user_pos_latitude SET DEFAULT 0;
ALTER TABLE user ALTER user_pos_longitude SET DEFAULT 0;
ALTER TABLE gamefinished ALTER finish SET DEFAULT 0;


USE thewalkinggame;

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("Zizou", 7.268, 43.7168);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("Yoann", 7.2672, 43.717);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("Kevin", 7.26762, 43.7163);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("Marc", 7.267, 43.7167);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("testA", 7.268, 43.7168);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("testB", 7.2672, 43.717);

insert into user(user_pseudo, user_pos_longitude, user_pos_latitude)
	values ("testC", 7.26762, 43.7163);

insert into findkeys(limite_time)
	values(15);

insert into simon(seq_length)
	values (4);

insert into headupsidedown (temps_autorise)
	values (10);

insert into reanimer (score_target, temps_autorise)
	values (1500, 10);

insert into question(question_text,question_multiple_choice)
values("Ou viserais-tu pour tuer un zombie ?",0);

insert into answer(answer_text,correct_answer,question_id)
values("Dans la tete",1,1),
("Dans le coeur",0,1),
("Dans les articulations",0,1),
("N'importe quel endroit tant que je touche",0,1);

insert into question(question_text,question_multiple_choice)
values("Un de tes partenaires est mordu, que fais-tu pour survivre ?",0);

insert into answer(answer_text,correct_answer,question_id)
values("Tu le soignes",0,2),
("Headshot sans te poser de questions",1,2),
("Tu lui jette un sort",0,2),
("Tu danses \"Thriller\" devant lui ",0,2);

insert into question(question_text,question_multiple_choice)
values("Tu vois un groupe de zombie au loin, que fais-tu ?",1);

insert into answer(answer_text,correct_answer,question_id)
values("Tu t'enfuis discretement",1,3),
("Tu fais exploser la bombe que tu avais mis a cet endroit",1,3),
("Tu leur envoies des poulet pour les distraire",0,3),
("Tu les charges en criant \" Leeeeeeroyyy jeeenkiins\" ",0,3);

insert into question(question_text,question_multiple_choice)
values("Combien de temps tient-on sans manger ?",0);

insert into answer(answer_text,correct_answer,question_id)
values("1 mois",1,4),
("3 jours",0,4),
("2 semaines",0,4),
("3 mois",0,4);

insert into question(question_text,question_multiple_choice)
values("Quelles armes sont efficaces contre les zombies ?",1);

insert into answer(answer_text,correct_answer,question_id)
values("Un Fusil a pompe",1,5),
("Une hache",1,5),
("Une bouteille en verre",0,5),
("Une epee",1,5);

insert into objective(latitude,longitude)
values
(43.7175,7.268),
(43.7171,7.26701),
(43.718,7.26701),
(43.718,7.268),
(43.717,7.2679),
(43.717,7.269),
(43.7174,7.269),
(43.716,7.2665),
(43.716,7.264),
(43.7157,7.267);
