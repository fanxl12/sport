drop table if exists sport_task;
drop table if exists sport_user;
drop table if exists sport_advertisement;
drop table if exists sport_coach;
drop table if exists sport_courseType;
drop table if exists sport_course;
drop table if exists sport_advice;
drop table if exists sport_collect;
drop table if exists sport_order;
drop table if exists sport_area;
drop table if exists sport_company;

create table sport_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	userId bigint not null,
    primary key (id)
) engine=InnoDB;

create table sport_user (
	id bigint auto_increment,
	loginName varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	registerDate timestamp not null default 0,
	phoneNumber varchar(64) not null,
	birthday datetime,
	head varchar(255),
	age bigint,
	sex tinyint,
	address varchar(128),
	primary key (id)
) engine=InnoDB;

create table sport_catalog (
	id bigint auto_increment,
	url varchar(255),
	name varchar(255) not null,
	createDate datetime not null,
	updateDate datetime,
	parentCatalogId bigint,
    primary key (id),
    CONSTRAINT `fk_parent_id` FOREIGN KEY (`parentCatalogId`) REFERENCES `sport_catalog` (`id`) ON DELETE Restrict ON UPDATE Restrict
) engine=InnoDB;

create table sport_advertisement(
	id bigint auto_increment,
	url varchar(255) not null,
	name varchar(255),
	website varchar(128),
	type bigint,
	createDate datetime not null,
	updateDate datetime,
	primary key (id)
)engine=InnoDB;

create table sport_coach(
	id bigint auto_increment,
	url varchar(255) not null,
	name varchar(255),
	coachTime varchar(128),
	createDate datetime not null,
	honor text,
	updateDate datetime,
	primary key (id)
)engine=InnoDB;

create table sport_courseType(
	id bigint auto_increment,
	name varchar(255) not null,
	type bigint,
	createDate datetime not null,
	updateDate datetime,
	primary key (id)
)engine=InnoDB;

create table sport_advice(
	id bigint auto_increment,
	content text not null,
	courseId bigint,
	userId bigint,
	score bigint,
	matchId bigint,
	createDate datetime not null,
	primary key (id)
)engine=InnoDB;

create table sport_course(
	id bigint auto_increment,
	url varchar(255),
	address varchar(255),
	price bigint,
	clssType int,
	orginalPrice bigint,
	startTime datetime,
	stopTime datetime,
	endTime datetime,
	totalNumber bigint,
	buyNumber bigint,
	introduce text,
	parentCatalogId bigint,
	childCatalogId bigint,
	notice text,
	coachId bigint,
	totalScore bigint,
	longitude bigint,
	latitude bigint,
	areaId bigint,
	name varchar(255) not null,
	courseTypeId bigint,
	createDate datetime not null,
	updateDate datetime,
	primary key (id),
	KEY `fk_course_coach` (`coachId`),
  	CONSTRAINT `fk_course_coach` FOREIGN KEY (`coachId`) REFERENCES `sport_coach` (`id`),
	CONSTRAINT `fk_ct_id` FOREIGN KEY (`courseTypeId`) REFERENCES `sport_courseType` (`id`) ON DELETE Restrict ON UPDATE Restrict
)engine=InnoDB;

create table sport_collect(
	id bigint auto_increment,
	courseId bigint,
	userId bigint,
	matchId bigint,
	createDate datetime not null,
	primary key (id)
)engine=InnoDB;

create table sport_order(
	id varchar(128),
	courseId bigint,
	userId bigint,
	matchId bigint,
	count bigint,
	totalMoney double,
	discountId bigint,
	payWay varchar(64),
	status int,
	createDate datetime not null,
	primary key (id)
)engine=InnoDB;

create table sport_area(
	id bigint auto_increment,
	name varchar(255),
	createDate datetime not null,
	primary key (id)
)engine=InnoDB;

create table sport_company(
	id bigint auto_increment,
	name varchar(128),
	address varchar(255),
	longitude bigint,
	latitude bigint,
	createDate datetime not null,
	updateDate datetime,
	primary key (id)
)engine=InnoDB;