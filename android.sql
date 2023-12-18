-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 18 déc. 2023 à 09:58
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `android`
--

-- --------------------------------------------------------

--
-- Structure de la table `joueur`
--

DROP TABLE IF EXISTS `joueur`;
CREATE TABLE IF NOT EXISTS `joueur` (
  `id_joueur` int NOT NULL AUTO_INCREMENT,
  `nom_utilisateur` varchar(50) NOT NULL,
  `mdp` varchar(20) NOT NULL,
  PRIMARY KEY (`id_joueur`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `joueur`
--

INSERT INTO `joueur` (`id_joueur`, `nom_utilisateur`, `mdp`) VALUES
(1, 'admin', 'admin'),
(2, 'user', 'user'),
(3, 'test', '$2y$10$uMWNF8Fzqof9D'),
(4, 'test1', '$2y$10$8tZS7Kf2tP.Ds'),
(5, 'test2', 'test2'),
(6, 'guil', 'guil'),
(7, 'wall', 'wall');

-- --------------------------------------------------------

--
-- Structure de la table `partie`
--

DROP TABLE IF EXISTS `partie`;
CREATE TABLE IF NOT EXISTS `partie` (
  `id_partie` int NOT NULL AUTO_INCREMENT,
  `score` int NOT NULL,
  `id_joueur` int NOT NULL,
  PRIMARY KEY (`id_partie`),
  KEY `id_joueur` (`id_joueur`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `partie`
--

INSERT INTO `partie` (`id_partie`, `score`, `id_joueur`) VALUES
(1, 120, 1);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `partie`
--
ALTER TABLE `partie`
  ADD CONSTRAINT `partie_ibfk_1` FOREIGN KEY (`id_joueur`) REFERENCES `joueur` (`id_joueur`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
