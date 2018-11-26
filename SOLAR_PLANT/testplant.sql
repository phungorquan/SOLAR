-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 19, 2018 at 04:15 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id7269981_solar`
--
CREATE DATABASE IF NOT EXISTS `testplant` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `testplant`;

-- --------------------------------------------------------

--
-- Table structure for table `collecteddata`
--

DROP TABLE IF EXISTS `collecteddata`;
CREATE TABLE `collecteddata` (
  `IndexData` int(11) NOT NULL,
  `ID` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Field1` float DEFAULT NULL,
  `Field2` float DEFAULT NULL,
  `Field3` float DEFAULT NULL,
  `TimeGet` time DEFAULT NULL,
  `DateGet` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `collecteddata`
--

INSERT DELAYED IGNORE INTO `collecteddata` (`IndexData`, `ID`, `Field1`, `Field2`, `Field3`, `TimeGet`, `DateGet`) VALUES
(63, 'CEEC-Solar', 416, 261, 210, '10:09:00', '2018-10-08'),
(64, 'CEEC-Solar', 99, 128, 241, '10:10:00', '2018-10-08'),
(65, 'CEEC-Solar', 290, 158, 363, '10:11:00', '2018-10-08'),
(66, 'CEEC-Solar', 122, 123, 266, '10:12:00', '2018-10-08'),
(67, 'CEEC-Solar', 69, 419, 473, '10:13:00', '2018-10-08'),
(68, 'CEEC-Solar', 247, 1, 429, '10:14:00', '2018-10-12'),
(69, 'CEEC-Solar', 461, 229, 324, '10:15:00', '2018-10-12'),
(70, 'CEEC-Solar', 322, 79, 251, '10:16:00', '2018-10-12'),
(71, 'CEEC-Solar', 455, 217, 453, '10:17:00', '2018-10-12'),
(72, 'CEEC-Solar', 25, 469, 96, '10:18:00', '2018-10-12'),
(73, 'CEEC-Solar', 22, 229, 149, '10:19:00', '2018-10-12'),
(74, 'CEEC-Solar', 231, 197, 320, '10:20:00', '2018-10-08'),
(75, 'CEEC-Solar', 142, 7, 377, '10:21:00', '2018-10-08'),
(76, 'CEEC-Solar', 178, 451, 324, '10:22:00', '2018-10-08'),
(77, 'CEEC-Solar', 273, 446, 55, '10:23:00', '2018-10-08'),
(78, 'CEEC-Solar', 422, 493, 314, '10:24:00', '2018-10-08'),
(79, 'CEEC-Solar', 116, 199, 30, '10:25:00', '2018-10-08'),
(80, 'Quân', 20, 290, 110, '10:26:00', '2018-10-08'),
(81, 'Quân', 239, 166, 91, '10:27:00', '2018-10-08'),
(82, 'Quân', 66, 391, 226, '10:28:00', '2018-10-08'),
(83, 'Quân', 472, 193, 68, '10:29:00', '2018-10-08'),
(84, 'Quân', 154, 54, 298, '10:30:00', '2018-10-08'),
(85, 'CEEC-Solar', 200, 364, 220, '10:31:00', '2018-10-08'),
(86, 'CEEC-Solar', 137, 144, 326, '10:32:00', '2018-10-09'),
(87, 'CEEC-Solar', 451, 278, 167, '10:44:00', '2018-10-09');

-- --------------------------------------------------------

--
-- Table structure for table `currentdata`
--

DROP TABLE IF EXISTS `currentdata`;
CREATE TABLE `currentdata` (
  `ID` char(20) COLLATE utf8_unicode_ci NOT NULL,
  `Voltage` float DEFAULT NULL,
  `Intensity` float DEFAULT NULL,
  `Wat` float DEFAULT NULL,
  `StatusConnect` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `currentdata`
--

INSERT DELAYED IGNORE INTO `currentdata` (`ID`, `Voltage`, `Intensity`, `Wat`, `StatusConnect`) VALUES
('CEEC-Solar', 127, 44, 698, b'0');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` char(20) COLLATE utf8_unicode_ci NOT NULL,
  `Pass` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT DELAYED IGNORE INTO `users` (`ID`, `Pass`) VALUES
('CEEC-Solar', 'ce.uit.edu.vn'),
('Quân', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `collecteddata`
--
ALTER TABLE `collecteddata`
  ADD PRIMARY KEY (`IndexData`),
  ADD KEY `FK_CollectedData_Users` (`ID`);

--
-- Indexes for table `currentdata`
--
ALTER TABLE `currentdata`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `collecteddata`
--
ALTER TABLE `collecteddata`
  MODIFY `IndexData` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `collecteddata`
--
ALTER TABLE `collecteddata`
  ADD CONSTRAINT `FK_CollectedData_Users` FOREIGN KEY (`ID`) REFERENCES `users` (`ID`);

--
-- Constraints for table `currentdata`
--
ALTER TABLE `currentdata`
  ADD CONSTRAINT `FK_CurrentData_Users` FOREIGN KEY (`ID`) REFERENCES `users` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
