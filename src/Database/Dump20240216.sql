CREATE DATABASE  IF NOT EXISTS `quanlyda` ;
USE `quanlyda`;






DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `createddate` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modifieddate` datetime(6) DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` enum('DEAN','VICE_DEAN','HEAD_OF_DEPARTMENT','CATECHISM','INSTRUCTOR','CRITICAL_LECTURERS','COUNCIL','TEACHER','STUDENT') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;



INSERT INTO `role` VALUES (NULL,1,NULL,NULL,NULL,'TRƯỞNG KHOA','DEAN'),(NULL,2,NULL,NULL,NULL,'PHÓ TRƯỞNG KHOA','VICE_DEAN'),(NULL,3,NULL,NULL,NULL,'TRƯỞNG BỘ MÔN ','HEAD_OF_DEPARTMENT'),(NULL,4,NULL,NULL,NULL,'GIÁO VỤ ','CATECHISM'),(NULL,5,NULL,NULL,NULL,'GIẢNG VIÊN HƯỚNG DẪN ','INSTRUCTOR'),(NULL,6,NULL,NULL,NULL,'GIẢNG VIÊN PHẢN BIỆN ','CRITICAL_LECTURERS'),(NULL,7,NULL,NULL,NULL,'HỘI ĐỒNG','COUNCIL'),(NULL,8,NULL,NULL,NULL,'GIẢNG VIÊN','TEACHER'),(NULL,9,NULL,NULL,NULL,'HỌC SINH','STUDENT');




DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `is_graduate` int DEFAULT NULL,
  `createddate` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modifieddate` datetime(6) DEFAULT NULL,
  `createdby` varchar(255) DEFAULT NULL,
  `modifiedby` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;


INSERT INTO `user` VALUES (1,'2024-02-08 19:30:49.377000',1,'2024-02-08 19:30:49.377000','anonymousUser','anonymousUser','$2a$12$4WSNY6tBJL2sgB4ab8Ae0OgUT/LRU7JeYXCtgmYWWpImXRu4R8j82','tester1'),(1,'2024-02-08 19:58:43.233000',2,'2024-02-08 19:58:43.233000','anonymousUser','anonymousUser','$2a$10$Rex20Hu51.xzhmWCWLM5B.OeLncsnZ/8PhvBlGqsdjd3/1lIkI7tW','tester2'),(1,'2024-02-08 19:59:00.000000',3,'2024-02-08 19:59:00.000000','anonymousUser','anonymousUser','$2a$10$8t4Z8YmjsnFHLnQG.bkMIelkEMF3yQqG/tDE6mv7WOnMwuAiHVNFu','tester3');




DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);




INSERT INTO `user_role` VALUES (1,1),(1,2),(1,3),(8,3);
