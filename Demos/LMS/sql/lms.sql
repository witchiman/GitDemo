create database lms;

use lms;

create table Book
(
ISBN varchar(15) primary key,
name varchar(20),
author varchar(20),
category varchar(20),
price int,
number int
)

insert into Book values ('9787101051469','史记','司马迁','Hitory',28,5);
insert into Book values ('9787111255833','Effective java','Joshua Bloch ','Programming Design',52,3);
insert into Book values ('9787505724358','庆余年','猫腻','Novel',58,6);
insert into Book values ('9787532524808','人间词话','王国维','Literary Criticism',10,2);
insert into Book values ('9787805200552','西游记','吴承恩','Novel',15,2);
insert into Book values ('9787539971810','The Storied Life','Gabrielle Zevin','Novel',35,4);
insert into Book values ('9787544242516','白夜行','东野圭吾','Novel',30,2);
insert into Book values ('9787563812349','易经','某人','Philosophy',15,4);
insert into Book values ('9788702006926','The Da Vinci Code','Dan Brown','Novel',26,5);
insert into Book values ('9787536671256','A Game of Thrones','GRRM','Novel',68,2);

create table User 
(
ID int unsigned  primary key auto_increment,
name varchar(20),
password varchar(20),
sex varchar(6), 
date timestamp，
number
);

insert User values(101,'user1','123','male',3);
insert User (name, password, sex,number) values('user2','123','female',3);
insert User (name, password, sex,number) values('user3','123','male',3);

create table Record 
(
SN int unsigned primary key auto_increment,
adminName varchar(20),
userName varchar(20),
ISBN varchar(15), 
borrowDate datetime,
returnDate datetime
); 


create table Admin
(
ID int unsigned primary key auto_increment,
name varchar(20),
password varchar(20),
sex varchar(6)
);

insert into Admin values (201,'admin1','112233','female');
insert into Admin (name,password,sex)values ('admin2','112233','male');

create table Fine
(
sn int unsigned primary key auto_increment,
userName varchar(20),
ISBN varchar(15),
date datetime,
fine float(4,1)
);