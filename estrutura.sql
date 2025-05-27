-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: sanea_mais
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `acompanhamento`
--

DROP TABLE IF EXISTS `acompanhamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acompanhamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reclamacao_id` int NOT NULL,
  `responsavel` varchar(100) DEFAULT NULL,
  `acao` text NOT NULL,
  `data_acao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `reclamacao_id` (`reclamacao_id`),
  CONSTRAINT `acompanhamento_ibfk_1` FOREIGN KEY (`reclamacao_id`) REFERENCES `reclamacoes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reclamacoes`
--

DROP TABLE IF EXISTS `reclamacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reclamacoes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int DEFAULT NULL,
  `tipo_problema_id` int NOT NULL,
  `regiao_id` int NOT NULL,
  `endereco` varchar(200) NOT NULL,
  `descricao` text NOT NULL,
  `latitude` decimal(10,8) DEFAULT NULL,
  `longitude` decimal(11,8) DEFAULT NULL,
  `data_reclamacao` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('pendente','em_analise','resolvido','arquivado') DEFAULT 'pendente',
  `anexo_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`),
  KEY `tipo_problema_id` (`tipo_problema_id`),
  KEY `regiao_id` (`regiao_id`),
  CONSTRAINT `reclamacoes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `reclamacoes_ibfk_2` FOREIGN KEY (`tipo_problema_id`) REFERENCES `tipos_problema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regioes_santos`
--

DROP TABLE IF EXISTS `regioes_santos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regioes_santos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome_regiao` varchar(50) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `populacao` int DEFAULT NULL,
  `area` decimal(10,2) DEFAULT NULL COMMENT 'Área em km²',
  `zona` varchar(20) DEFAULT NULL COMMENT 'Zona da cidade',
  `taxa_urbanizacao` decimal(5,2) DEFAULT NULL COMMENT 'Percentual de urbanização',
  `densidade_populacional` decimal(10,2) DEFAULT NULL COMMENT 'hab/km²',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tipos_problema`
--

INSERT INTO `regioes_santos` (`id`, `nome_regiao`, `latitude`, `longitude`, `populacao`, `area`, `zona`, `taxa_urbanizacao`, `densidade_populacional`) VALUES (1,'Caruara',-23.95555600,-46.38000000,2500,12.50,'Noroeste',85.00,200.00),(2,'Vila São Jorge',-23.97000000,-46.37000000,8000,3.20,'Noroeste',95.00,2500.00),(3,'Jardim Castelo',-23.96500000,-46.37500000,12000,4.50,'Noroeste',98.00,2666.67),(4,'Jardim São Manoel',-23.96000000,-46.36500000,15000,5.00,'Noroeste',97.00,3000.00),(5,'Jardim Rádio Clube',-23.95500000,-46.36000000,18000,6.00,'Noroeste',96.00,3000.00),(6,'Centro',-23.96083300,-46.33388900,15000,2.50,'Central',100.00,6000.00),(7,'Vila Mathias',-23.96388900,-46.33472200,12000,1.80,'Central',100.00,6666.67),(8,'Macuco',-23.95833300,-46.33194400,8000,1.20,'Central',100.00,6666.67),(9,'Valongo',-23.95694400,-46.33000000,6000,1.00,'Central',100.00,6000.00),(10,'Paquetá',-23.96166700,-46.33055600,10000,1.50,'Central',100.00,6666.67),(11,'Gonzaga',-23.96750000,-46.33694400,25000,3.80,'Leste',100.00,6578.95),(12,'Boqueirão',-23.98500000,-46.33888900,32000,6.00,'Leste',100.00,5333.33),(13,'Embaré',-23.97416700,-46.32972200,19000,3.20,'Leste',100.00,5937.50),(14,'Aparecida',-23.96611100,-46.34444400,28000,4.80,'Leste',100.00,5833.33),(15,'Ponta da Praia',-23.98083300,-46.29972200,18000,4.20,'Leste',100.00,4285.71),(16,'José Menino',-23.98694400,-46.30555600,22000,3.50,'Leste',100.00,6285.71),(17,'Santa Maria',-23.99166700,-46.31111100,15000,2.80,'Leste',100.00,5357.14),(18,'São Manoel',-23.99027800,-46.34166700,21000,4.50,'Leste',100.00,4666.67),(19,'Vila Nova',-23.99305600,-46.32222200,30000,5.10,'Sudoeste',98.00,5882.35),(20,'Campo Grande',-23.99638900,-46.35500000,27000,7.20,'Sudoeste',95.00,3750.00),(21,'Estuário',-23.99888900,-46.34000000,5000,8.00,'Sudoeste',60.00,625.00),(22,'Sabooó',-24.00000000,-46.34583300,18000,4.00,'Sudoeste',90.00,4500.00),(23,'Alemoa',-24.00305600,-46.35000000,15000,5.50,'Sudoeste',85.00,2727.27),(24,'Chico de Paula',-24.00694400,-46.35555600,8000,6.00,'Sudoeste',70.00,1333.33),(25,'Marapé',-23.97194400,-46.34000000,20000,3.00,'Intermediária',98.00,6666.67),(26,'Vila Belmiro',-23.97361100,-46.34583300,12000,2.00,'Intermediária',98.00,6000.00),(27,'Jabaquara',-23.97638900,-46.35000000,15000,2.50,'Intermediária',97.00,6000.00),(28,'Monte Serrat',-23.96527800,-46.33888900,5000,1.50,'Intermediária',100.00,3333.33),(29,'Morro São Bento',-23.96805600,-46.34222200,7000,1.80,'Intermediária',100.00,3888.89),(30,'Morro da Nova Cintra',-23.95000000,-46.38000000,12000,5.00,'Noroeste',90.00,2400.00),(31,'Morro Santa Maria',-23.94805600,-46.37500000,10000,4.50,'Noroeste',85.00,2222.22),(32,'Morro José Menino',-23.94583300,-46.37000000,8000,4.00,'Noroeste',80.00,2000.00),(33,'Morro Fontana',-23.94388900,-46.36500000,6000,3.50,'Noroeste',75.00,1714.29),(34,'Morro Santa Terezinha',-23.94166700,-46.36000000,5000,3.00,'Noroeste',70.00,1666.67),(35,'Porto',-23.95305600,-46.32500000,500,10.00,'Portuária',30.00,50.00),(36,'Ilha Barnabé',-23.97500000,-46.29000000,200,2.00,'Insular',10.00,100.00),(37,'Ilha Diana',-23.98500000,-46.28000000,300,3.00,'Insular',15.00,100.00);

DROP TABLE IF EXISTS `tipos_problema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos_problema` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `descricao` text,
  `icone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

INSERT INTO `tipos_problema` (`id`, `nome`, `descricao`, `icone`) VALUES (1,'Esgoto a céu aberto','Vazamento ou acúmulo de esgoto em vias públicas','fa-tint'),(2,'Falta de água','Ausência prolongada de abastecimento de água','fa-faucet'),(3,'Água contaminada','Água com cor, odor ou sabor estranho','fa-flask'),(4,'Entupimento','Bueiros ou redes de esgoto entupidas','fa-water'),(5,'Alagamento','Pontos de alagamento recorrente','fa-umbrella'),(6,'Vazamento','Vazamentos de água em vias públicas','fa-tint'),(7,'Outros','Outros problemas relacionados a saneamento','fa-question-circle');

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `cpf` varchar(11) DEFAULT NULL,
  `senha_hash` varchar(255) DEFAULT NULL,
  `data_cadastro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `telefone` (`telefone`),
  UNIQUE KEY `cpf` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-24 22:23:52
