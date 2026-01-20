-- MySQL dump 10.13  Distrib 9.5.0, for Win64 (x86_64)
--
-- Host: localhost    Database: article_db
-- ------------------------------------------------------
-- Server version	9.5.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `article_db`
--

/*!40000 DROP DATABASE IF EXISTS `article_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `article_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `article_db`;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `category_id` int NOT NULL,
  `cover_image` varchar(255) DEFAULT NULL,
  `author_id` int NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'Spring Boot入门教程',1,'/images/springboot.jpg',1,1,'2025-12-19 13:43:35','2025-12-19 13:43:35'),(2,'我的编程学习之路',3,'/images/study.jpg',1,1,'2025-12-19 13:43:35','2025-12-19 13:43:35'),(3,'美好的周末生活',2,'/images/life.jpg',1,1,'2025-12-19 13:43:35','2025-12-19 13:43:35');
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category_alias` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `create_user_id` int NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_alias` (`category_alias`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'技术文章','technology','技术相关文章',1,'2025-12-19 13:43:31','2025-12-19 13:43:31',1),(2,'生活随笔','life','生活感悟文章',1,'2025-12-19 13:43:31','2025-12-19 13:43:31',1),(3,'学习笔记','study','学习记录文章',1,'2025-12-19 13:43:31','2025-12-19 13:43:31',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '1',
  `role` varchar(50) NOT NULL DEFAULT 'USER' COMMENT '用户角色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$49r8.cUmFhUdukAjpZwdceM4pZ6UFtxgN1vecoo0hapbgdkfnv6Rq','admin@example.com',NULL,'2025-12-19 13:43:31','2025-12-24 21:13:49',1,'USER'),(2,'user1','$2a$10$49r8.cUmFhUdukAjpZwdceM4pZ6UFtxgN1vecoo0hapbgdkfnv6Rq','user1@example.com',NULL,'2025-12-19 13:43:31','2025-12-24 21:13:49',1,'USER'),(3,'fds','$2a$10$rB8qDDAIPA7QleOQmTUmJu.hMJgyg6FMYTLhOq3vGYmUmtQZ9c2wW',NULL,NULL,'2025-12-27 16:27:50','2025-12-27 16:27:50',1,'USER'),(4,'fdss','$2a$10$yRVreUXfH/LDqtujmLPz3uth5feHzSysuV1UWpodkp.OIi6a4vqWK',NULL,NULL,'2025-12-28 17:59:10','2025-12-28 17:59:10',1,'USER'),(5,'fdsss','$2a$10$yspyY5zBaVgY0BewB0Rr5eH4TihWbmImjj8Pf7H8uZS0F1mtB2Xt2',NULL,NULL,'2025-12-28 18:26:38','2025-12-28 18:26:38',1,'USER'),(6,'fdssss','$2a$10$gAwpZIvLvCi36aG/dy9mg.RkIed0umZIJad2p.ejFYkU7wbmXy2Uu',NULL,NULL,'2025-12-28 18:27:11','2025-12-28 18:27:11',1,'USER'),(7,'fdssd','$2a$10$1VWFybhvyr3Rc6A90pzMb.j/Ktz.KxzPIxrIPnrs/ss6PP0AnVenm',NULL,NULL,'2025-12-28 18:29:50','2025-12-28 18:29:50',1,'USER'),(8,'fdssad','$2a$10$sEYG7rh2.hWeUSsWA6Bc1.Ut7Ir6vGqRmQ30p/S4DuZ/w0.yb5aiG',NULL,NULL,'2025-12-28 19:02:39','2025-12-28 19:02:39',1,'USER'),(9,'fddds','$2a$10$FA5fdKAOFO3P27urf1TTnOP7h4tCz1WCHq8gng1DgZ/krtnbcM.sK',NULL,NULL,'2025-12-28 19:43:30','2025-12-28 19:43:30',1,'USER'),(10,'test-fds','$2a$10$JfoY/xw4.BpXDexfz14keevJ3l2K1Ij9Yf9W7j5b.amVaVTqOy.2G',NULL,NULL,'2026-01-03 14:26:35','2026-01-03 14:26:35',1,'USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-03 22:13:30
