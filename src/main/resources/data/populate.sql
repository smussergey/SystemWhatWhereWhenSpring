CREATE DATABASE  IF NOT EXISTS `system_what_where_when_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
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
-- Dumping data for table `appeal`
--

LOCK TABLES `appeal` WRITE;
/*!40000 ALTER TABLE `appeal` DISABLE KEYS */;
INSERT INTO `appeal` VALUES (93,'CONSIDERED','2020-02-07',79,3),(111,'CONSIDERED','2020-02-08',106,3),(114,'FILED','2020-02-09',107,3),(115,'FILED','2020-02-10',109,3);
/*!40000 ALTER TABLE `appeal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `appealed_question`
--

LOCK TABLES `appealed_question` WRITE;
/*!40000 ALTER TABLE `appealed_question` DISABLE KEYS */;
INSERT INTO `appealed_question` VALUES (94,93,80),(95,93,81),(112,111,107),(113,111,109),(115,114,113),(116,114,114),(117,115,121),(118,115,122);
/*!40000 ALTER TABLE `appealed_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (21,'2020-02-06',3,2),(32,'2020-02-06',3,2),(50,'2020-02-06',26,3),(79,'2020-02-07',3,2),(83,'2020-02-07',3,26),(87,'2020-02-07',2,26),(97,'2020-02-07',3,2),(100,'2020-02-08',2,3),(106,'2020-02-08',3,2),(107,'2020-02-09',3,2),(108,'2020-02-09',2,3),(109,'2020-02-10',3,26),(110,'2020-02-10',3,2),(111,'2020-02-10',3,26);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (117),(117),(117),(117),(117),(117);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (78,'CONSIDERED','2020-02-06','4:6','KPI','КПІ','KNEU','КНЕУ'),(96,'NOT_FILED','2020-02-06','5:6','KNEU','КНЕУ','KPI','КПІ'),(99,'CONSIDERED','2020-02-06','0:7','Audience','Глядачі','KNEU','КНЕУ');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (22,21,3),(23,21,2),(24,21,2),(25,21,2),(33,32,3),(34,32,2),(35,32,3),(36,32,2),(37,32,2),(38,32,2),(51,50,3),(52,50,3),(53,50,26),(54,50,3),(55,50,3),(56,50,3),(80,79,3),(81,79,2),(82,79,2),(84,83,26),(85,83,3),(86,83,3),(88,87,2),(89,87,26),(90,87,2),(91,87,26),(92,87,26),(98,97,3),(101,100,3),(102,100,3),(103,100,2),(104,100,3),(105,100,3),(107,106,3),(108,106,3),(109,106,2),(110,106,2),(111,107,3),(112,107,3),(113,107,2),(114,107,2),(115,107,2),(116,108,2),(117,108,2),(118,108,3),(119,108,2),(120,109,3),(121,109,26),(122,109,26),(123,109,3),(124,109,26),(125,109,3),(126,109,3),(127,109,26),(128,109,26),(129,110,3),(130,110,3),(131,110,2),(132,110,2),(133,110,3),(134,111,3),(135,111,26),(136,111,3),(137,111,26),(138,111,26),(139,111,26);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ad','Admin','Админ','$2a$10$8TyVmBIuacKSTjhq0.qE1eC.JdDWO3vouFRaWNkm/qCVDvmn2yt3G','ROLE_REFEREE'),(2,'Audience@gmail.com','Audience','Глядачі','$2a$10$JgMcDn2T029lOKm7ls8rXuiNk6Z5O9vW80F/XKQWWnN6E34HQjKWW','ROLE_PLAYER'),(3,'us','KNEU','КНЕУ','$2a$10$qHGtmg0A5d5b8z2sWUIp.eLdNIc39qmIQ7ijmuGhz19mSlY87bPbm','ROLE_PLAYER'),(26,'kpi','KPI','КПІ','$2a$10$JgOfK5OtHuXgeIm3ulEkIep9hw.ljIeOnHPrhcv0BZhte.JKSKLL.','ROLE_PLAYER'),(27,' xcv cx','cxv',' xcv','$2a$10$GjNuFda7wyhjO.XO48WeUe4rwrNR9uK8ZFrSo//8/w3.6lbhVKQXC','ROLE_PLAYER'),(28,'qqq','qqq','qqq','$2a$10$GDyfT5fJtKTYrJvGsaqUVuY4fg1wy7aLsDZVlp.Mxqn04p32Aqw9G','ROLE_PLAYER'),(29,'dfv','dfv','vdf','$2a$10$RSY2Eqf8oEET.kBV8GXES.N77SzpBhI76SgzpSmjHZhhnz0Q6kqaK','ROLE_PLAYER');
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

-- Dump completed on 2020-02-10 14:37:41
