-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 17-08-31 02:38
-- 서버 버전: 5.7.14
-- PHP 버전: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `bicycleshare`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `bicycle_table`
--

CREATE TABLE `bicycle_table` (
  `Bicycle_No` int(100) NOT NULL,
  `In_Use` tinyint(1) NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `Station_Id` int(11) DEFAULT NULL,
  `Dock_No` int(3) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `bicycle_table`
--

INSERT INTO `bicycle_table` (`Bicycle_No`, `In_Use`, `Username`, `Station_Id`, `Dock_No`) VALUES
(5678, 0, NULL, 2, 1),
(1234, 0, NULL, 1, 2),
(1963, 0, NULL, 1, 1),
(1245, 0, '', 1, 3),
(1122, 0, NULL, 2, 2);

-- --------------------------------------------------------

--
-- 테이블 구조 `borrow_table`
--

CREATE TABLE `borrow_table` (
  `Id` int(5) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Bicycle_No` int(20) NOT NULL,
  `Borrow_Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Station_Id` int(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `borrow_table`
--

INSERT INTO `borrow_table` (`Id`, `Username`, `Bicycle_No`, `Borrow_Date`, `Station_Id`) VALUES
(2, 'larsson', 1234, '2017-08-15 13:07:55', 1),
(3, 'larsson', 1234, '2017-08-15 13:16:25', 1),
(4, 'larsson', 1234, '2017-08-15 13:37:28', 1),
(5, 'larsson', 1234, '2017-08-21 10:10:08', 1),
(6, 'larsson', 1234, '2017-08-21 10:45:41', 1),
(7, 'larsson', 1234, '2017-08-21 11:15:11', 1),
(8, 'larsson', 1234, '2017-08-21 11:18:11', 1),
(9, 'Raju', 1963, '2017-08-21 11:27:32', 1),
(24, 'larsson', 1234, '2017-08-30 12:11:03', 1),
(23, 'larsson', 1234, '2017-08-30 12:06:50', 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `dock_table`
--

CREATE TABLE `dock_table` (
  `Id` int(4) NOT NULL,
  `Dock_No` smallint(3) NOT NULL,
  `Station_No` int(3) NOT NULL,
  `Bike_Avai` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `dock_table`
--

INSERT INTO `dock_table` (`Id`, `Dock_No`, `Station_No`, `Bike_Avai`) VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 1, 2, 1),
(4, 2, 2, 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `parking_table`
--

CREATE TABLE `parking_table` (
  `Id` int(5) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Bicycle_No` int(20) NOT NULL,
  `Station_Id` int(20) NOT NULL,
  `Parking_Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `parking_table`
--

INSERT INTO `parking_table` (`Id`, `Username`, `Bicycle_No`, `Station_Id`, `Parking_Date`) VALUES
(2, 'larsson', 1234, 1, '2017-08-15 13:11:53'),
(3, 'larsson', 1234, 1, '2017-08-15 13:25:18'),
(4, 'larsson', 1234, 1, '2017-08-15 13:45:29'),
(5, 'larsson', 1234, 1, '2017-08-21 10:28:01'),
(6, 'larsson', 1234, 1, '2017-08-21 10:46:09'),
(7, 'larsson', 1234, 1, '2017-08-21 11:15:43'),
(8, 'larsson', 1234, 1, '2017-08-21 11:29:59'),
(9, 'Raju', 1963, 1, '2017-08-21 11:32:49'),
(10, 'larsson', 1234, 1, '2017-08-21 11:42:11'),
(11, 'larsson', 1234, 1, '2017-08-21 11:47:12'),
(12, 'larsson', 1234, 1, '2017-08-21 12:25:01'),
(13, 'larsson', 1234, 1, '2017-08-30 12:07:37'),
(14, 'larsson', 1234, 1, '2017-08-30 12:12:20');

-- --------------------------------------------------------

--
-- 테이블 구조 `password_table`
--

CREATE TABLE `password_table` (
  `Id` int(5) NOT NULL,
  `Bicycle_No` int(5) NOT NULL,
  `Bicycle_Password` varchar(50) NOT NULL,
  `Username` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `password_table`
--

INSERT INTO `password_table` (`Id`, `Bicycle_No`, `Bicycle_Password`, `Username`) VALUES
(41, 1234, 'dbc4d84bfcfe2284ba11beffb853a8c4', 'larsson');

-- --------------------------------------------------------

--
-- 테이블 구조 `recharge_table`
--

CREATE TABLE `recharge_table` (
  `Recharge_Id` int(100) NOT NULL,
  `Username` varchar(30) NOT NULL,
  `Recharge_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Recharge_Period` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- 테이블 구조 `station_table`
--

CREATE TABLE `station_table` (
  `Station_Id` int(100) NOT NULL,
  `Station_Name` varchar(20) NOT NULL,
  `Location` varchar(30) NOT NULL,
  `Bicycle_Unit` int(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `station_table`
--

INSERT INTO `station_table` (`Station_Id`, `Station_Name`, `Location`, `Bicycle_Unit`) VALUES
(1, 'jyatha station', 'Jyatha, KTM', 2),
(2, 'Lalitpur', 'Path Durbar', 2);

-- --------------------------------------------------------

--
-- 테이블 구조 `user_table`
--

CREATE TABLE `user_table` (
  `User_Id` int(100) NOT NULL,
  `First_Name` varchar(20) NOT NULL,
  `Last_Name` varchar(20) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Use_Limit` date NOT NULL,
  `Active` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `user_table`
--

INSERT INTO `user_table` (`User_Id`, `First_Name`, `Last_Name`, `Email`, `Username`, `Password`, `Use_Limit`, `Active`) VALUES
(1, 'Larsson', 'Bajracharya', 'larssonbajra@gmail.com', 'larsson', '7ff25c4d2de13986ef9151141cfd2ed0', '2018-12-31', 0),
(2, 'RAJU', 'Tamang', 'raju@hotmail.com', 'Raju', '67719c4c2dae2189c6a83110e9461c15', '2018-12-25', 0),
(3, 'lara', 'lara', 'larss@gmail.com', 'larss', '414ee59dea4b68271fa6aa52a3e18dff', '2017-08-07', 0),
(4, 'mrijan', 'mrijan', 'mrijan@gmail.com', 'mrijan', '4d98aba5394ec3827a631da32505e19b', '2017-08-25', 0);

-- --------------------------------------------------------

--
-- 테이블 구조 `verify_table`
--

CREATE TABLE `verify_table` (
  `Station_Id` int(3) NOT NULL,
  `Dock_No` int(3) DEFAULT NULL,
  `Pass_Key` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- 테이블의 덤프 데이터 `verify_table`
--

INSERT INTO `verify_table` (`Station_Id`, `Dock_No`, `Pass_Key`) VALUES
(1, 2, 0),
(2, NULL, 0);

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `bicycle_table`
--
ALTER TABLE `bicycle_table`
  ADD PRIMARY KEY (`Bicycle_No`);

--
-- 테이블의 인덱스 `borrow_table`
--
ALTER TABLE `borrow_table`
  ADD PRIMARY KEY (`Id`);

--
-- 테이블의 인덱스 `dock_table`
--
ALTER TABLE `dock_table`
  ADD PRIMARY KEY (`Id`);

--
-- 테이블의 인덱스 `parking_table`
--
ALTER TABLE `parking_table`
  ADD PRIMARY KEY (`Id`);

--
-- 테이블의 인덱스 `password_table`
--
ALTER TABLE `password_table`
  ADD PRIMARY KEY (`Id`);

--
-- 테이블의 인덱스 `recharge_table`
--
ALTER TABLE `recharge_table`
  ADD PRIMARY KEY (`Recharge_Id`);

--
-- 테이블의 인덱스 `station_table`
--
ALTER TABLE `station_table`
  ADD PRIMARY KEY (`Station_Id`);

--
-- 테이블의 인덱스 `user_table`
--
ALTER TABLE `user_table`
  ADD PRIMARY KEY (`User_Id`);

--
-- 테이블의 인덱스 `verify_table`
--
ALTER TABLE `verify_table`
  ADD PRIMARY KEY (`Station_Id`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `borrow_table`
--
ALTER TABLE `borrow_table`
  MODIFY `Id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- 테이블의 AUTO_INCREMENT `dock_table`
--
ALTER TABLE `dock_table`
  MODIFY `Id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- 테이블의 AUTO_INCREMENT `parking_table`
--
ALTER TABLE `parking_table`
  MODIFY `Id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- 테이블의 AUTO_INCREMENT `password_table`
--
ALTER TABLE `password_table`
  MODIFY `Id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;
--
-- 테이블의 AUTO_INCREMENT `recharge_table`
--
ALTER TABLE `recharge_table`
  MODIFY `Recharge_Id` int(100) NOT NULL AUTO_INCREMENT;
--
-- 테이블의 AUTO_INCREMENT `user_table`
--
ALTER TABLE `user_table`
  MODIFY `User_Id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
