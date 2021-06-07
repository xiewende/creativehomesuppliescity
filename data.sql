/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.5.49 : Database - expresswaybilling
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`expresswaybilling` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `expresswaybilling`;

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `announcement_titile` varchar(50) DEFAULT NULL,
  `announcement_content` varchar(500) NOT NULL,
  `announcement_time` datetime DEFAULT NULL,
  `isDelete` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `announcement` */

insert  into `announcement`(`id`,`announcement_titile`,`announcement_content`,`announcement_time`,`isDelete`) values 
(1,'放假通知3','五一放假五一放假五一放假五一放假五一放假五一放假五一放假','2021-04-21 11:57:44',0),
(2,'放假通知','五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天','2021-04-21 11:57:44',0),
(3,'放假通知2','五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天五一放假五天','2021-04-21 11:58:17',0),
(4,'123','122','2021-05-29 15:33:41',1),
(5,'1','1','2021-05-29 15:34:19',1),
(6,'123','1','2021-05-29 17:05:43',1),
(7,'12','124','2021-05-30 01:40:35',1),
(8,'1','122','2021-05-30 01:48:02',1),
(9,'1','1','2021-05-30 02:19:36',0);

/*Table structure for table `charge` */

DROP TABLE IF EXISTS `charge`;

CREATE TABLE `charge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `car_number` varchar(20) NOT NULL,
  `car_type` varchar(100) NOT NULL,
  `distance` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `charge_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `isDelete` int(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `charge_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `charge` */

insert  into `charge`(`id`,`user_id`,`car_number`,`car_type`,`distance`,`price`,`charge_time`,`update_time`,`isDelete`) values 
(1,5,'粤P66666','客车1类',101,30,NULL,'2021-05-29 15:47:53',1),
(2,6,'粤P66667','客车2类',100,30,'2021-05-05 14:08:56','2021-05-05 14:08:56',0),
(3,6,'粤P66668','客车3类',100,30,'2021-05-05 14:09:03','2021-05-05 14:09:03',0),
(4,5,'粤P66669','客车4类',100,30,NULL,'2021-05-29 17:06:26',0),
(5,6,'12','1',1,1,NULL,'2021-05-29 16:58:34',1);

/*Table structure for table `charge_standard` */

DROP TABLE IF EXISTS `charge_standard`;

CREATE TABLE `charge_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `car_tag` int(2) NOT NULL,
  `car_type` varchar(100) NOT NULL,
  `car_other` varchar(100) NOT NULL,
  `rate` double DEFAULT NULL,
  `isDelete` int(2) DEFAULT '0',
  `begin` varchar(20) DEFAULT NULL,
  `end` varchar(20) DEFAULT NULL,
  `distance` double DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `charge_standard` */

insert  into `charge_standard`(`id`,`car_tag`,`car_type`,`car_other`,`rate`,`isDelete`,`begin`,`end`,`distance`,`total_price`) values 
(1,0,'1 类','≤9 座',0.45,0,'上坪','连平北',30,13.5),
(2,0,'2类','10 座-19 座',0.45,0,'上坪','连平北',30,13.5),
(3,0,'3类','20 座-39 座',0.45,0,'上坪','连平北',30,13.5),
(4,0,'4类','≥40 座',0.45,0,'上坪','连平北',30,13.5),
(5,1,'1类','二轴（车长小于 6000mm 且最大允 许总质量小于 4500kg）',0.45,0,'上坪','连平北',30,13.5),
(6,1,'2类','二轴（车长不小于 6000mm 或最大 允许总质量不小于 4500kg）',0.45,0,'上坪','连平北',30,13.5),
(7,1,'3类','三轴',0.45,0,'上坪','连平北',30,13.5),
(8,1,'4类','四轴',0.45,0,'上坪','连平北',30,13.5),
(9,1,'5类','五轴',0.45,0,'上坪','连平北',30,13.5),
(10,1,'6类','六轴',0.55,0,'上坪','连平北',35,19.25);

/*Table structure for table `equipment` */

DROP TABLE IF EXISTS `equipment`;

CREATE TABLE `equipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_name` varchar(50) NOT NULL,
  `equipment_number` varchar(20) NOT NULL,
  `price` double NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) NOT NULL,
  `isDelete` int(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `equipment` */

insert  into `equipment`(`id`,`equipment_name`,`equipment_number`,`price`,`create_time`,`update_time`,`remark`,`isDelete`) values 
(1,'etc机器1','2021',888,'2021-04-26 12:17:47','2021-04-26 12:31:48','很不错的机器很不错的机器很不错的机器很不错的机器很不错的机器很不错的机器',0),
(2,'etc机器2','2022',999,'2021-04-26 12:18:06','2021-04-26 12:18:06','这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好',0),
(3,'etc机器3','2023',999,'2021-04-26 12:18:13','2021-04-26 12:18:13','这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好',0),
(4,'etc机器4','2024',999,'2021-04-26 12:18:20','2021-04-26 12:18:20','这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好这个机器很好',0),
(5,'13','12',1,'2021-05-29 16:01:51','2021-05-29 16:02:01','1',1),
(6,'1','1',12,'2021-05-29 17:07:42','2021-05-29 17:07:50','1',1);

/*Table structure for table `equipment_repairs` */

DROP TABLE IF EXISTS `equipment_repairs`;

CREATE TABLE `equipment_repairs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `iphone` varchar(11) NOT NULL,
  `remark` varchar(500) NOT NULL,
  `status` int(5) NOT NULL,
  `isDelete` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `repairs_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `equipment_id` (`equipment_id`),
  CONSTRAINT `equipment_repairs_ibfk_1` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `equipment_repairs` */

insert  into `equipment_repairs`(`id`,`equipment_id`,`name`,`iphone`,`remark`,`status`,`isDelete`,`create_time`,`repairs_time`,`finish_time`) values 
(1,1,'张三','17876208191','这个设备坏了有一天了，请尽快维修！！',2,0,'2021-04-26 14:02:10','2021-04-26 14:08:45','2021-04-26 14:10:27'),
(2,2,'李四','17876208191','这个设备坏了有一天了，请尽快维修！！',2,0,'2021-04-26 14:02:56','2021-04-26 14:09:43','2021-05-30 02:20:40'),
(3,3,'王五','17876208191','这个设备坏了有一天了，请尽快维修！！',1,0,'2021-04-26 14:03:07','2021-05-30 02:20:45','2021-05-29 17:08:39'),
(4,1,'1','18218372855','1',2,1,'2021-05-29 16:02:39','2021-05-29 16:03:18','2021-05-29 16:03:22'),
(5,1,'1','18218372855','1',2,1,'2021-05-29 16:59:56',NULL,'2021-05-29 16:59:59'),
(6,1,'1','18218372855','1',2,1,'2021-05-29 17:08:15','2021-05-29 17:08:26','2021-05-29 17:08:28');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `user_number` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `iphone` varchar(11) DEFAULT NULL,
  `gender` int(2) DEFAULT NULL,
  `id_number` varchar(30) NOT NULL,
  `address` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL,
  `isDelete` int(2) DEFAULT '0',
  `age` int(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`user_number`,`password`,`iphone`,`gender`,`id_number`,`address`,`image`,`isDelete`,`age`) values 
(5,'admin','000000000000','670b14728ad9902aecba32e22fa4f6bd','17876208191',1,'441623199711030611','广东省湛江市麻章区海大路一号广东海洋大学','http://192.168.159.129/group1/M00/00/00/wKifgWCG9wqAJpS2AAQLitqWV-g491.png',0,20),
(6,'小明','201711701231','670b14728ad9902aecba32e22fa4f6bd','17876208191',0,'441623199711030611','广东省湛江市麻章区海大路一号广东海洋大学','http://192.168.159.129/group1/M00/00/00/wKifgWCG9ySACvr6AAQLitqWV-g662.png',0,20),
(7,'小鸿','201711701232','670b14728ad9902aecba32e22fa4f6bd','17876208191',0,'441623199711030611','广东省湛江市麻章区海大路一号广东海洋大学','http://192.168.159.129/group1/M00/00/00/wKifgWCG9y-AdUzuAAQLitqWV-g871.png',0,20),
(8,'小白','201711701233','670b14728ad9902aecba32e22fa4f6bd','17876208191',0,'441623199711030611','广东省湛江市麻章区海大路一号广东海洋大学','http://192.168.159.129/group1/M00/00/00/wKifgWCG9z-AMy46AAQLitqWV-g154.png',0,20),
(9,'小谢','201711701234','670b14728ad9902aecba32e22fa4f6bd','17876208191',1,'441623199711030611','广东省湛江市麻章区海大路一号广东海洋大学','http://192.168.159.129/group1/M00/00/00/wKifgWCG-YOAVl6ZAAM-bhHUC2E540.png',1,23),
(10,'黄慧玲','201711701207','670b14728ad9902aecba32e22fa4f6bd','18218372855',1,'440282199905204728','广东省','C:\\fakepath\\微信图片_20210529232021.jpg',1,23),
(11,'1','1','670b14728ad9902aecba32e22fa4f6bd','18218371855',0,'440282199905204728','12','C:\\fakepath\\微信图片_20210529232021.jpg',1,1),
(12,'1','201711701236','670b14728ad9902aecba32e22fa4f6bd','18218372890',1,'440282199905204728','12','D:毕业设计签名.jpg',1,12);

/*Table structure for table `work_time` */

DROP TABLE IF EXISTS `work_time`;

CREATE TABLE `work_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `time` int(5) NOT NULL,
  `switch_number` int(10) NOT NULL,
  `isDelete` int(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `work_time_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `work_time` */

insert  into `work_time`(`id`,`user_id`,`create_time`,`update_time`,`time`,`switch_number`,`isDelete`) values 
(1,9,'2021-05-04 14:23:10','2021-05-29 17:10:05',1,2,0),
(2,7,'2021-05-04 14:23:50','2021-05-29 16:43:53',1,1,0),
(3,8,'2021-05-04 14:27:28','2021-05-04 14:27:28',2,1,0),
(4,9,'2021-05-29 16:40:04','2021-05-29 16:44:46',0,1,1),
(5,9,'2021-05-29 17:11:10','2021-05-29 17:11:41',0,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
