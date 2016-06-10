use itat_maven_test;
create table IF NOT EXISTS t_user(
	id int(10) auto_increment primary key,
	username varchar(20),
	password varchar(20)
);