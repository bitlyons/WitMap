-- phpMyAdmin SQL Dump
-- version 4.5.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 29, 2016 at 04:58 AM
-- Server version: 10.0.23-MariaDB
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `witmap`
--

-- --------------------------------------------------------
Create database `witmap`;
use witmap;
--
-- Table structure for table `area`
--

CREATE TABLE `area` (
  `aId` int(11) NOT NULL,
  `x` decimal(11,0) NOT NULL,
  `y` decimal(11,0) NOT NULL,
  `height` decimal(11,0) NOT NULL DEFAULT '20',
  `width` decimal(11,0) NOT NULL DEFAULT '20'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `area`
--

INSERT INTO `area` (`aId`, `x`, `y`, `height`, `width`) VALUES
(1, '436', '354', '100', '100'),
(2, '751', '449', '39', '39');

-- --------------------------------------------------------

--
-- Table structure for table `building`
--

CREATE TABLE `building` (
  `bId` int(3) NOT NULL,
  `bName` varchar(60) NOT NULL,
  `bInfo` text NOT NULL,
  `bOpeningHours` text NOT NULL,
  `aId` int(11) DEFAULT NULL,
  `image` int(11) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `building`
--

INSERT INTO `building` (`bId`, `bName`, `bInfo`, `bOpeningHours`, `aId`, `image`) VALUES
(1, '1969 Main Building', 'This building contains the reception, \nWitCard office, \nAib Branch\nAib ATM.\nThe Canteen,\nInternational Office,\n\nand Morre ', 'Mon    8.00am to 10.00pm\nTues   8.00am to 10.00pm\nWed   8.00am to 10.00pm\nThurs 8.00am to 10.00pm\nFri      8.00am to 7.00pm\nSat    Opening times will vary, please check notices\nSun   Closed\n', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `iId` int(11) NOT NULL,
  `buId` int(11) NOT NULL,
  `iURL` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`iId`, `buId`, `iURL`) VALUES
(1, 1, '../'),
(36, 1, 'http://www.narincomicro.com/productimage/158.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `rId` int(11) NOT NULL,
  `bId` int(11) NOT NULL,
  `rName` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `room`
--

INSERT INTO `room` (`rId`, `bId`, `rName`) VALUES
(7, 1, 'D1'),
(8, 1, 'D2'),
(10, 1, 'D3'),
(11, 1, 'D4'),
(13, 1, 'D5'),
(14, 1, 'D6');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`aId`);

--
-- Indexes for table `building`
--
ALTER TABLE `building`
  ADD PRIMARY KEY (`bId`),
  ADD KEY `aId` (`aId`),
  ADD KEY `image` (`image`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`iId`),
  ADD KEY `bId` (`buId`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`rId`),
  ADD UNIQUE KEY `rName` (`rName`),
  ADD KEY `bId` (`bId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `iId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `rId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `building`
--
ALTER TABLE `building`
  ADD CONSTRAINT `building_ibfk_1` FOREIGN KEY (`aId`) REFERENCES `area` (`aId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `building_ibfk_2` FOREIGN KEY (`image`) REFERENCES `image` (`iId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `image_ibfk_1` FOREIGN KEY (`buId`) REFERENCES `building` (`bId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_1` FOREIGN KEY (`bId`) REFERENCES `building` (`bId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
