-- phpMyAdmin SQL Dump
-- version 4.5.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 29, 2016 at 11:18 AM
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

--
-- Table structure for table `area`
--
Create Database witmap;
use witmap;

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
(1, '437', '356', '100', '100'),
(2, '262', '494', '30', '130'),
(3, '657', '428', '40', '30'),
(4, '567', '481', '30', '100');

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
(1, '1969 Main Building', 'This building contains the reception, \nWitCard office, \nAib Branch\nAib ATM.\nThe Canteen,\nInternational Office,\n\nand Morre ', 'Mon    8.00am to 10.00pm\nTues   8.00am to 10.00pm\nWed   8.00am to 10.00pm\nThurs 8.00am to 10.00pm\nFri      8.00am to 7.00pm\nSat    Opening times will vary, please check notices\nSun   Closed\n', 1, 1),
(2, 'Tourism and Leisure ', 'Dedicated Language and Information Technology laboratories.\n\nTen state of the art training kitchens\n\nFine dining training restaurant and Bistro training restaurant\n\nFull operational training bar and lounge\n\nReception training laboratory\n\nTwenty four teaching classrooms', 'Mon		8:00am - 6:15pm\nTue		8:00am - 6:15pm\nWed		8:00am - 6:15pm\nThur		8:00am - 6:15pm\nFri		8:00am - 6:15pm\nSat		Closed', 2, 40),
(3, 'Walton IT Building', 'Named after Ernest TS Walton (the Co Waterford-born Nobel Physics Laureate)\nthe 3,000 square metre Walton Building greatly enhances and expands the Institute’s world-class information and communications infrastructure.\nThe 18 large computer laboratories in the building each feature an innovative passive air movement system that helps ensure comfortable learning conditions for users. A daylight-filled central atrium located alongside the entrance accommodates all circulation and social spaces.\n\nThis building also  hosts The Computer Services department \nwhich is responsible for the maintenance and development of \nInstitute IT facilities including general computing facilities for staff\nand students, data and phone networks, e-mail facilities and MIS systems.\n', 'Mon	8:00AM - 10:00PM\nTue	8:00AM - 10:00PM\nWed	8:00AM - 10:00PM\nThur	8:00AM - 10:00PM\nFri	8:00AM -   6:30PM\nSat	8:00AM -   1:45PM		', 3, 44),
(4, 'Luke Wadding Library', 'Luke Wadding Library on the Cork Road Campus is the Institutes main library and is one of the largest and most innovative third-level libraries in Ireland. Covering 6,000 sq. metres, with a range of resources and services and in excess of 1,000 reader spaces, the library is well-equipped to support the diverse learning needs of staff and students at the Institute.  The building includes a dedicated learning centre, Special Collections room, staff reading area, and group study area. It is also home to the Academic Skills Centre, the eLearning Support Unit, and a Careers Advice centre.\nThe library collection comprises over 180,000 print items, and over 120,000 eBook titles. In addition, the library houses over 340 hardcopy journals, and provides access to thousands of online journals through our database collections. There are over 150 student workstations available in the building with full Internet access and the library is a wireless zone. Luke Wadding Library is an energy efficient green building and has won important architectural and Department of the Environment sustainable building awards. The building underpins Waterford Institute of Technologys commitment to being a regional centre of excellence and a world-class learning environment.', 'Mon   8:30AM - 8:45PM\nTue   8:30AM - 8:45PM\nWed   8:30AM - 8:45PM\nThur  8:30AM - 8:45PM\nFri   8:30AM - 8:45PM\nSat   Closed\n\n', 4, 56);

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
(1, 1, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/main.jpg'),
(40, 2, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/Tourism__Leisure_web.JPG'),
(41, 2, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/TL_building.jpg'),
(44, 3, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/Walton_IT_web.jpg'),
(45, 3, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/it.jpg'),
(46, 3, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/Walton.jpg'),
(56, 4, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/library.jpg'),
(57, 2, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/tl.jpg'),
(60, 2, '/home/lyonzy/Projects/java/WitMap/src/xyz/lyonzy/map/resources/images/wit_fitness_suite.jpg');

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
(7, 1, 'C1'),
(8, 1, 'C2'),
(10, 1, 'C3'),
(11, 1, 'C4'),
(13, 1, 'C5'),
(14, 1, 'C6'),
(15, 2, 'TL110'),
(16, 2, 'TL111'),
(17, 2, 'TL112'),
(18, 2, 'TL113'),
(19, 2, 'TL114'),
(20, 2, 'TL115'),
(21, 2, 'TL116'),
(22, 2, 'TL117'),
(23, 2, 'TL118'),
(24, 2, 'TL119'),
(25, 2, 'TL120'),
(26, 2, 'TL210'),
(27, 2, 'TL211'),
(28, 2, 'TL212'),
(29, 2, 'TL213'),
(31, 3, 'ITG1'),
(32, 3, 'ITG2'),
(33, 3, 'ITG3'),
(34, 3, 'ITG4'),
(35, 3, 'ITG5'),
(36, 3, 'ITG6'),
(37, 3, 'IT101'),
(38, 3, 'IT102'),
(39, 3, 'IT120'),
(40, 3, 'IT119'),
(41, 3, 'IT118'),
(43, 3, 'IT217'),
(44, 3, 'IT218'),
(49, 1, 'C7');

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
  MODIFY `iId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;
--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `rId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
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
