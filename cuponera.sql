-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 05-08-2018 a las 21:18:07
-- Versión del servidor: 5.7.23-0ubuntu0.16.04.1
-- Versión de PHP: 7.0.30-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cuponera`
--
CREATE DATABASE cuponera;
USE cuponera;
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `authentication`
--

CREATE TABLE `authentication` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `token` varchar(255) NOT NULL,
  `auth` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `company`
--

CREATE TABLE `company` (
  `id` varchar(6) COLLATE utf8_spanish2_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `contact_name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `telephone` char(9) COLLATE utf8_spanish2_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `type_company` int(11) NOT NULL,
  `pct_comission` int(11) NOT NULL,
  `password` char(64) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `company`
--

INSERT INTO `company` (`id`, `name`, `address`, `contact_name`, `telephone`, `email`, `type_company`, `pct_comission`, `password`) VALUES
('ABC123', 'Prueba 1', 'San Salvador', 'Diego Lemus', '2222-4444', 'prueba@gmail.com', 1, 10, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `company_type`
--

CREATE TABLE `company_type` (
  `id` int(11) NOT NULL,
  `type` varchar(50) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `company_type`
--

INSERT INTO `company_type` (`id`, `type`) VALUES
(1, 'Restaurante'),
(2, 'Transportes'),
(3, 'Taller'),
(4, 'Salon de Belleza');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `password` char(64) COLLATE utf8_spanish2_ci NOT NULL,
  `id_company` varchar(6) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `employee`
--

INSERT INTO `employee` (`id`, `name`, `last_name`, `email`, `password`, `id_company`) VALUES
(1, 'Carlos', 'Lemus', 'carlos@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'ABC123');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_resets`
--

CREATE TABLE `password_resets` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `token` varchar(255) NOT NULL,
  `auth` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promotion`
--

CREATE TABLE `promotion` (
  `id` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `regular_price` decimal(18,2) NOT NULL,
  `ofert_price` decimal(18,2) NOT NULL,
  `init_date` date NOT NULL,
  `end_date` date NOT NULL,
  `limit_date` date NOT NULL,
  `limit_cant` int(11) NOT NULL,
  `description` varchar(500) COLLATE utf8_spanish2_ci NOT NULL,
  `other_details` varchar(500) COLLATE utf8_spanish2_ci NOT NULL,
  `image` varchar(75) COLLATE utf8_spanish2_ci NOT NULL,
  `coupons_sold` int(11) NOT NULL,
  `coupons_available` int(11) NOT NULL,
  `earnings` decimal(18,2) NOT NULL,
  `charge_service` decimal(18,2) NOT NULL,
  `id_company` varchar(6) COLLATE utf8_spanish2_ci NOT NULL,
  `id_state` int(11) NOT NULL,
  `rejected_description` varchar(300) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `promotion`
--

INSERT INTO `promotion` (`id`, `title`, `regular_price`, `ofert_price`, `init_date`, `end_date`, `limit_date`, `limit_cant`, `description`, `other_details`, `image`, `coupons_sold`, `coupons_available`, `earnings`, `charge_service`, `id_company`, `id_state`) VALUES
(1, 'Hay cocteles amor', '4.50', '4.45', '2018-08-10', '2018-08-11', '2018-08-11', 10, 'No te pierdas estos ricos cocteles amor', 'Especialidad la que usted quiera amor', 'coctel.png', 0, 0, '5.95', '4.00', 'ABC123', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promotion_state`
--

CREATE TABLE `promotion_state` (
  `id` int(11) NOT NULL,
  `state` varchar(50) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `promotion_state`
--

INSERT INTO `promotion_state` (`id`, `state`) VALUES
(1, 'En espera de aprobación'),
(2, 'Aprobada'),
(3, 'Rechazada'),
(4, 'Descartada'),
(5, 'Activa'),
(6, 'Pasada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `coupon_code` varchar(13) COLLATE utf8_spanish2_ci NOT NULL,
  `promotion_id` int(11) NOT NULL,
  `client_id` int(11) NOT NULL,
  `verified` tinyint(1) NOT NULL,
  `sales_state` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `sales`
--

INSERT INTO `sales` (`id`, `coupon_code`, `promotion_id`, `client_id`, `verified`, `sales_state`) VALUES
(1, 'ABC1230000001', 1, 1, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sales_state`
--

CREATE TABLE `sales_state` (
  `id` int(11) NOT NULL,
  `state` varchar(20) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `sales_state`
--

INSERT INTO `sales_state` (`id`, `state`) VALUES
(1, 'Canjeado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `password` char(64) COLLATE utf8_spanish2_ci NOT NULL,
  `user_type` int(11) NOT NULL,
  `dui` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `nit` char(17) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `name`, `last_name`, `email`, `password`, `user_type`, `dui`, `nit`) VALUES
(1, 'Alberto', 'Lemus', 'albeto@gmai.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, '12345678-9', '1234-123456-123-1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_type`
--

CREATE TABLE `user_type` (
  `id` int(11) NOT NULL,
  `type` varchar(20) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `user_type`
--

INSERT INTO `user_type` (`id`, `type`) VALUES
(1, 'client'), (2, 'administrator');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `authentication`
--
ALTER TABLE `authentication`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `type_company` (`type_company`);

--
-- Indices de la tabla `company_type`
--
ALTER TABLE `company_type`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `password_resets`
--
ALTER TABLE `password_resets`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_empresa` (`id_company`),
  ADD KEY `id_estado` (`id_state`);

--
-- Indices de la tabla `promotion_state`
--
ALTER TABLE `promotion_state`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `coupon_code` (`coupon_code`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `promotion_id` (`promotion_id`),
  ADD KEY `sales_state` (`sales_state`);

--
-- Indices de la tabla `sales_state`
--
ALTER TABLE `sales_state`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `dui` (`dui`),
  ADD UNIQUE KEY `nit` (`nit`),
  ADD KEY `user_type` (`user_type`);

--
-- Indices de la tabla `user_type`
--
ALTER TABLE `user_type`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `authentication`
--
ALTER TABLE `authentication`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `company_type`
--
ALTER TABLE `company_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `password_resets`
--
ALTER TABLE `password_resets`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `promotion`
--
ALTER TABLE `promotion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `promotion_state`
--
ALTER TABLE `promotion_state`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `sales_state`
--
ALTER TABLE `sales_state`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `user_type`
--
ALTER TABLE `user_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

USE cuponera;

-- Default
ALTER TABLE promotion ALTER coupons_sold SET DEFAULT 0;
ALTER TABLE promotion ALTER earnings SET DEFAULT 0;
ALTER TABLE promotion ALTER charge_service SET DEFAULT 0;
ALTER TABLE sales ALTER verified SET DEFAULT 0;

-- FOREIGN KEY
ALTER TABLE user
ADD CONSTRAINT FK_UserType
FOREIGN KEY(user_type) REFERENCES user_type(id);

ALTER TABLE promotion
ADD CONSTRAINT FK_PromotionState
FOREIGN KEY(id_state) REFERENCES promotion_state(id);

ALTER TABLE promotion
ADD CONSTRAINT FK_PromotionCompany
FOREIGN KEY(id_company) REFERENCES company(id);

ALTER TABLE sales
ADD CONSTRAINT FK_SalesPromotion
FOREIGN KEY(promotion_id) REFERENCES promotion(id);

ALTER TABLE sales
ADD CONSTRAINT FK_ClientSales
FOREIGN KEY(client_id) REFERENCES user(id);

ALTER TABLE company
ADD CONSTRAINT FK_CompanyType
FOREIGN KEY(type_company) REFERENCES company_type(id);

ALTER TABLE sales
ADD CONSTRAINT FK_SalesState
FOREIGN KEY(sales_state) REFERENCES sales_state(id);

ALTER TABLE employee
ADD CONSTRAINT FK_EmployeeCompany
FOREIGN KEY(id_company) REFERENCES company(id);

SHOW EVENTS;
-- Evento que se ejecuta cada media noche y cambia el estado ha "pasado" cuando la fecha es menor a la actual
SHOW VARIABLES LIKE 'event_scheduler';
SET GLOBAL event_scheduler = ON;
DROP EVENT IF EXISTS change_state_promotion;
DELIMITER //
CREATE DEFINER=`root`@`localhost` EVENT IF NOT EXISTS change_state_promotion 
ON SCHEDULE EVERY 1 DAY STARTS DATE_FORMAT(NOW(), '%Y-%m-%d 00:00:00') 
ON COMPLETION PRESERVE ENABLE 
DO 
	UPDATE cuponera.promotion SET id_state = 6 WHERE limit_date < DATE_FORMAT(NOW(), '%Y-%m-%d')/ 
END//
DELIMITER ;


DROP TRIGGER IF EXISTS update_promotion_data;
-- Cambia los datos en las promociones despues del ingreso de una venta
delimiter //
CREATE DEFINER=`root`@`localhost` TRIGGER update_promotion_data BEFORE INSERT ON sales
	FOR EACH ROW
BEGIN
	DECLARE ofer_price DOUBLE;
    DECLARE pct_comission INTEGER;
    DECLARE limit_cant INTEGER;
    
	SELECT P.ofert_price, C.pct_comission, P.limit_cant INTO @ofert_price, @pct_comission, @limit_cant FROM promotion AS P INNER JOIN company AS C ON P.id_company = C.id WHERE P.id = NEW.promotion_id;
	IF @limit_cant > 0 THEN
		UPDATE promotion SET coupons_available = (coupons_available - 1), coupons_sold = (coupons_sold + 1), earnings =  ((coupons_sold) *(@ofert_price)), charge_service = ((earnings)*(@pct_comission / 100)) WHERE id =  NEW.promotion_id;
	ELSE
		UPDATE promotion SET coupons_sold = (coupons_sold + 1), earnings = ((coupons_sold) *(@ofert_price)), charge_service = ((earnings)*(@pct_comission / 100)) WHERE id =  NEW.promotion_id;
    END IF;
END;//
delimiter ;

DROP PROCEDURE IF EXISTS insert_sales;
-- Ingreso de las ventas de cupones (con validación)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE insert_sales(IN _coupon_code VARCHAR(13), IN _promotion INTEGER, IN _client INTEGER, _state INTEGER, OUT _affected_row INTEGER)
BEGIN
	DECLARE xlimit_cant integer;
	DECLARE xcoupons_available integer;
    
    SELECT limit_cant INTO xlimit_cant FROM promotion WHERE id = _promotion;
    SELECT coupons_available INTO xcoupons_available FROM promotion WHERE id = _promotion;
	
    IF xlimit_cant > 0 THEN
		IF  1 > xcoupons_available THEN
			SET _affected_row = 0;
		ELSE
			INSERT INTO sales(coupon_code, promotion_id, client_id, sales_state) VALUE(_coupon_code, _promotion, _client, _state);
            SET _affected_row = 1;
		END IF;
	ELSE
		INSERT INTO sales(coupon_code, promotion_id, client_id, sales_state) VALUE(_coupon_code, _promotion, _client,  _state);
        SET _affected_row = 2;
	END IF;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS insert_promotion;
-- Ingreso en promociones
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE insert_promotion(IN _title VARCHAR(50), IN _regular_price INTEGER, IN _ofert_price INTEGER, IN _init_date DATE, IN _end_date DATE,IN _limit_date DATE, IN _limit_cant INTEGER, IN _description VARCHAR(500), IN _other_details VARCHAR(500), IN _image VARCHAR(75), IN _id_company VARCHAR(6))
BEGIN
	DECLARE _coupons_available INTEGER;  
	SET @_coupons_available = _limit_cant;
    INSERT INTO promotion(title, regular_price, ofert_price, init_date, end_date, limit_date, limit_cant, description, other_details, image, coupons_available, id_company, id_state, rejected_description) VALUES(_title, _regular_price, _ofert_price, _init_date, _end_date, _limit_date, _limit_cant, _description, _other_details, _image, @_coupons_available, _id_company, 1, "");
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS update_user;
-- Modificación en usuario
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_user(IN _id INTEGER, IN _name VARCHAR(50), IN _last_name VARCHAR(50), IN _email VARCHAR(50), IN _id_type INTEGER)
BEGIN
	UPDATE user SET name = _name, last_name = _last_name, email = _email, user_type = _id_type  WHERE id = _id;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS update_user_password;
-- Modificación en usuario(contraseña)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_user_password(IN _id INTEGER, IN _password CHAR(64))
BEGIN
	UPDATE user SET password = _password WHERE id = _id;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS update_company;
-- Modificación en usuario(contraseña)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_company(IN _id VARCHAR(6), IN _address VARCHAR(100), IN _contact_name VARCHAR(50), IN _telephone VARCHAR(9), IN _email VARCHAR(50), IN _name VARCHAR(50))
BEGIN
	UPDATE company SET address = _address, contact_name = _contact_name, telephone = _telephone, email = _email, name = _name WHERE id = _id;
END;;
DELIMITER ;


DROP PROCEDURE IF EXISTS update_company_password;
-- Modificación en usuario(contraseña)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_company_password(IN _id VARCHAR(6), IN _password CHAR(64))
BEGIN
	UPDATE company SET password = _password WHERE id = _id;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS update_employee;
-- Modificación en usuario(contraseña)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_employee(IN _id INTEGER, IN _email VARCHAR(50), IN _name VARCHAR(50), IN _last_name VARCHAR(50))
BEGIN
	UPDATE employee SET name = _name, email = _email, last_name = _last_name WHERE id = _id;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS update_employee_password;
-- Modificación en usuario(contraseña)
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE update_employee_password(IN _id INTEGER, IN _password CHAR(64))
BEGIN
	UPDATE employee SET password = _password WHERE id = _id;
END;;
DELIMITER ;

DROP PROCEDURE IF EXISTS check_sale;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE check_sale(IN _coupon_code VARChAR(13), IN _dui VARCHAR(10)) 
BEGIN
	SELECT * FROM sales AS S INNER JOIN user AS U ON S.client_id = U.id WHERE S.coupon_code = _coupon_code AND U.dui = _dui;
END;;
DELIMITER ;
use cuponera;
-- Vista para login y para verificar si existe un usuario con un correo
DROP VIEW IF EXISTS all_users;
CREATE VIEW all_users AS 
SELECT email, password, 'employee' AS user_type, id FROM employee
UNION SELECT email, password, 'company' AS user_type, id FROM company
UNION (SELECT U.email, password, T.type AS user_type, U.id FROM user AS U INNER JOIN user_type AS T ON U.user_type = T.id);

