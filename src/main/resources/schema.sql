CREATE DATABASE  IF NOT EXISTS `system_what_where_when_dbgg` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `system_what_where_when_db`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: system_what_where_when_db
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appeal`
--

DROP TABLE IF EXISTS `appeal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appeal` (
  `appeal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appeal_stage` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `game_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`appeal_id`),
  UNIQUE KEY `appeal_id_UNIQUE` (`appeal_id`),
  KEY `FKdoguk1ekiehfmn9efpcr1qdpq` (`game_id`),
  KEY `FKghfyutrsur9fwg8fnschdw6mc` (`user_id`),
  CONSTRAINT `FKdoguk1ekiehfmn9efpcr1qdpq` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`),
  CONSTRAINT `FKghfyutrsur9fwg8fnschdw6mc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `appealed_question`
--

DROP TABLE IF EXISTS `appealed_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appealed_question` (
  `appealed_question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appeal_id` bigint(20) NOT NULL,
  `question_id` bigint(20) NOT NULL,
  PRIMARY KEY (`appealed_question_id`),
  UNIQUE KEY `appealed_question_id_UNIQUE` (`appealed_question_id`),
  KEY `FKsjgk8l69un441y28wxrnkxm9q` (`appeal_id`),
  KEY `FKcrbitsupm6tt83u6aqroti1ne` (`question_id`),
  CONSTRAINT `FKcrbitsupm6tt83u6aqroti1ne` FOREIGN KEY (`question_id`) REFERENCES `question` (`question_id`),
  CONSTRAINT `FKsjgk8l69un441y28wxrnkxm9q` FOREIGN KEY (`appeal_id`) REFERENCES `appeal` (`appeal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `game` (
  `game_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `first_player_user_id` bigint(20) NOT NULL,
  `second_player_user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE KEY `game_id_UNIQUE` (`game_id`),
  KEY `FKrex1ss1hltdffub28w98hs9u5` (`first_player_user_id`),
  KEY `FKkpwfp53xcvabyev5jsghmco86` (`second_player_user_id`),
  CONSTRAINT `FKkpwfp53xcvabyev5jsghmco86` FOREIGN KEY (`second_player_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKrex1ss1hltdffub28w98hs9u5` FOREIGN KEY (`first_player_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `history` (
  `history_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appeal_stage` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `scores` varchar(255) NOT NULL,
  `first_player_name_en` varchar(255) NOT NULL,
  `first_player_name_ua` varchar(255) NOT NULL,
  `second_player_name_en` varchar(255) NOT NULL,
  `second_player_name_ua` varchar(255) NOT NULL,
  PRIMARY KEY (`history_id`),
  UNIQUE KEY `history_id_UNIQUE` (`history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `question` (
  `question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`question_id`),
  UNIQUE KEY `question_id_UNIQUE` (`question_id`),
  KEY `FKsva05a9urpsqn3jx02mu1b2y3` (`game_id`),
  KEY `FK4ekrlbqiybwk8abhgclfjwnmc` (`user_id`),
  CONSTRAINT `FK4ekrlbqiybwk8abhgclfjwnmc` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKsva05a9urpsqn3jx02mu1b2y3` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name_en` varchar(255) NOT NULL,
  `name_ua` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-10 14:38:01
