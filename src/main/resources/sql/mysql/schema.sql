drop table if exists sport_task;
drop table if exists sport_company;
drop table if exists sport_user;
drop table if exists sport_advertisement;
drop table if exists sport_area;
drop table if exists sport_catalog;
drop table if exists sport_coach;
drop table if exists sport_courseType;
drop table if exists sport_advice;
drop table if exists sport_course;
drop table if exists sport_collect;
drop table if exists sport_order;



create table sport_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	userId bigint not null,
    primary key (id)
) engine=InnoDB;

CREATE TABLE `sport_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `roles` varchar(255) NOT NULL,
  `registerDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `phoneNumber` varchar(64) NOT NULL,
  `head` varchar(255) DEFAULT NULL,
  `age` bigint(20) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `deviceTokens` varchar(255) DEFAULT NULL,
  `companyId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`),
  KEY `user_wj_companyId` (`companyId`),
  CONSTRAINT `user_wj_companyId` FOREIGN KEY (`companyId`) REFERENCES `sport_company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_advertisement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `website` varchar(128) DEFAULT NULL,
  `type` bigint(20) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_catalog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `parentCatalogId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id` (`parentCatalogId`),
  CONSTRAINT `fk_parent_id` FOREIGN KEY (`parentCatalogId`) REFERENCES `sport_catalog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_coach` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `coachTime` varchar(128) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `honor` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_coursetype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` bigint(20) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `orginalPrice` bigint(20) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `totalNumber` bigint(20) DEFAULT NULL,
  `buyNumber` bigint(20) DEFAULT NULL,
  `introduce` text,
  `parentCatalogId` bigint(20) DEFAULT NULL,
  `childCatalogId` bigint(20) DEFAULT NULL,
  `notice` text,
  `coachId` bigint(20) DEFAULT NULL,
  `totalScore` bigint(20) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `courseTypeId` bigint(20) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `updateDate` datetime DEFAULT NULL,
  `stopTime` datetime DEFAULT NULL,
  `classType` int(4) DEFAULT '0',
  `areaId` bigint(20) DEFAULT NULL,
  `companyId` bigint(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_course_coach` (`coachId`),
  KEY `fk_ct_id` (`courseTypeId`),
  KEY `fk_company_id` (`companyId`),
  KEY `fk_area_id` (`areaId`),
  KEY `fk_parent_catalog` (`parentCatalogId`),
  KEY `fk_child_catalog` (`childCatalogId`),
  CONSTRAINT `fk_area_id` FOREIGN KEY (`areaId`) REFERENCES `sport_area` (`id`),
  CONSTRAINT `fk_child_catalog` FOREIGN KEY (`childCatalogId`) REFERENCES `sport_catalog` (`id`),
  CONSTRAINT `fk_company_id` FOREIGN KEY (`companyId`) REFERENCES `sport_company` (`id`),
  CONSTRAINT `fk_course_coach` FOREIGN KEY (`coachId`) REFERENCES `sport_coach` (`id`),
  CONSTRAINT `fk_ct_id` FOREIGN KEY (`courseTypeId`) REFERENCES `sport_coursetype` (`id`),
  CONSTRAINT `fk_parent_catalog` FOREIGN KEY (`parentCatalogId`) REFERENCES `sport_catalog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_advice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `courseId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `score` bigint(20) DEFAULT NULL,
  `matchId` bigint(20) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_advice_user` (`userId`),
  KEY `fk_advice_course` (`courseId`),
  CONSTRAINT `fk_advice_course` FOREIGN KEY (`courseId`) REFERENCES `sport_course` (`id`),
  CONSTRAINT `fk_advice_user` FOREIGN KEY (`userId`) REFERENCES `sport_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sport_collect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `courseId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `matchId` bigint(20) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_collect_course` (`courseId`),
  KEY `fk_collect_user` (`userId`),
  CONSTRAINT `fk_collect_course` FOREIGN KEY (`courseId`) REFERENCES `sport_course` (`id`),
  CONSTRAINT `fk_collect_user` FOREIGN KEY (`userId`) REFERENCES `sport_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

CREATE TABLE `sport_order` (
  `id` varchar(128) NOT NULL,
  `courseId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `matchId` bigint(20) DEFAULT NULL,
  `count` bigint(20) DEFAULT NULL,
  `totalMoney` double DEFAULT NULL,
  `discountId` bigint(20) DEFAULT NULL,
  `payWay` varchar(64) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `createDate` datetime NOT NULL,
  `comfirmUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_user` (`userId`),
  KEY `fk_order_course` (`courseId`),
  KEY `fk_order_confirm` (`comfirmUserId`),
  CONSTRAINT `fk_order_confirm` FOREIGN KEY (`comfirmUserId`) REFERENCES `sport_user` (`id`),
  CONSTRAINT `fk_order_course` FOREIGN KEY (`courseId`) REFERENCES `sport_course` (`id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`userId`) REFERENCES `sport_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

