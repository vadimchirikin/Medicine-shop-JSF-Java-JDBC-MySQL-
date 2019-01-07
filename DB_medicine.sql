-- phpMyAdmin SQL Dump
-- version 3.4.9
-- http://www.phpmyadmin.net
--
-- Хост: localhost
-- Версия сервера: 5.5.20
-- Версия PHP: 5.3.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `medicine`
--

-- --------------------------------------------------------

--
-- Структура таблицы `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL,
  `Name` text COLLATE cp1251_bin NOT NULL,
  `Group` text COLLATE cp1251_bin NOT NULL,
  `Manufacturer` text COLLATE cp1251_bin NOT NULL,
  `Form` text COLLATE cp1251_bin NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `employeeID` (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=cp1251 COLLATE=cp1251_bin AUTO_INCREMENT=9 ;

--
-- Дамп данных таблицы `customer`
--

INSERT INTO `customer` (`ID`, `price`, `Name`, `Group`, `Manufacturer`, `Form`, `quantity`) VALUES
(1, 1.45, 'Aspirin', 'Analgetic', 'Ukraine', 'drags', 1),
(5, 5.75, 'Aroma', 'Therapy', 'USA', 'oil', 1),
(6, 1.5, 'Lime', 'Herbs', 'Japan', 'mixture', 1),
(7, 1.45, 'Lemon', 'Herbs', 'USA', 'mixture', 1),
(8, 5.75, 'Citrus', 'Herbs', 'Japan', 'oil', 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
