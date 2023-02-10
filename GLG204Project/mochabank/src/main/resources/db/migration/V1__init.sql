/*============================================================================*/
/* DBMS: MySQL 5                                                              */
/* Created on : 24/06/2021 ‏‎21:42:23                                           */
/*============================================================================*/

/*============================================================================*/
/*                                  TABLES                                    */
/*============================================================================*/
--
-- Structure de la table `t_bankclerk`
--

CREATE TABLE `T_BANKCLERK` (
  `ID` varchar(20) NOT NULL,
  `FIRSTNAME` varchar(50) NOT NULL,
  `LASTNAME` varchar(50) NOT NULL,
  `EMAIL` varchar(255),
  `PASSWORD` varchar(128),  
  `ROLE_FK` int(3),
  CONSTRAINT `PK_T_BANKCLERK` PRIMARY KEY (`ID`)
) 
;

-- --------------------------------------------------------

--
-- Structure de la table `t_customer`
--

CREATE TABLE `T_CUSTOMER` (
  `ID` varchar(20) NOT NULL,
  `FIRSTNAME` varchar(50) NOT NULL,
  `LASTNAME` varchar(50) NOT NULL,  
  `EMAIL` varchar(255),  
  `BIRTHDATE` date,
  `PHONE_NUMBER` varchar(10),
  `COMPANY` varchar(50),
  `PASSWORD` varchar(128),  
  `STREET1` varchar(40),
  `STREET2` varchar(40),
  `ZIPCODE` varchar(10),
  `CITY` varchar(25),
  `ROLE_FK` int(3),
  `ACCOUNT_FK` bigint(20),
  CONSTRAINT `PK_T_CUSTOMER` PRIMARY KEY (`ID`)
) 
;

-- --------------------------------------------------------

--
-- Structure de la table `t_prospectcustomer`
--

CREATE TABLE `T_PROSPECTCUSTOMER` (
  `ID` varchar(20) NOT NULL,
  `FIRSTNAME` varchar(50) NOT NULL,
  `LASTNAME` varchar(50) NOT NULL,  
  `EMAIL` varchar(255),  
  `BIRTHDATE` date,
  `PHONE_NUMBER` varchar(10),
  `COMPANY` varchar(50),
  `PASSWORD` varchar(128),  
  `STREET1` varchar(40),
  `STREET2` varchar(40),
  `ZIPCODE` varchar(10),
  `CITY` varchar(25),
  `ROLE_FK` int(3),
  CONSTRAINT `PK_T_PROSPECTCUSTOMER` PRIMARY KEY (`ID`)
) 
;

--
-- Structure de la table `t_role`
--

CREATE TABLE `T_ROLE` (
  `ID` int(3) NOT NULL,
  `NAME` varchar(40),
  CONSTRAINT `PK_T_ROLE` PRIMARY KEY (`ID`)
) 
;

-- --------------------------------------------------------

--
-- Structure de la table `t_openaccountrequest`
--

CREATE TABLE `T_OPENACCOUNTREQUEST` (
  `ID` varchar(10) NOT NULL,
  `FIRSTNAME` varchar(50) NOT NULL,
  `LASTNAME` varchar(50) NOT NULL,
  `EMAIL` varchar(255),
  `BIRTHDATE` date,
  `PHONE_NUMBER` varchar(10),
  `COMPANY` varchar(50),
  `STREET1` varchar(40),
  `STREET2` varchar(40),
  `ZIPCODE` varchar(10),
  `CITY` varchar(25), 
  `ACCOUNT_OFFER` varchar(10) ,
  `ACCEPTED` bit(1),
  CONSTRAINT `PK_T_OPENACCOUNTREQUEST` PRIMARY KEY (`ID`)
) 
;

-- --------------------------------------------------------

--
-- Structure de la table `t_account`
--

CREATE TABLE `T_ACCOUNT` (
  `ID` bigint NOT NULL,
  `ACCOUNT_NAME` varchar(30),
  `BALANCE` bigint(50) NOT NULL,
  `BANK_ID` varchar(20),
  `BANK_NAME` varchar(30),
  `CUSTOMER_FK` varchar(20),
  CONSTRAINT `PK_T_ACCOUNT` PRIMARY KEY (`ID`)
) 
;


-- --------------------------------------------------------


/*============================================================================*/
/*                               FOREIGN KEYS                                 */
/*============================================================================*/

ALTER TABLE `T_BANKCLERK`
    ADD CONSTRAINT `FK_ROLE_FK`
        FOREIGN KEY (`ROLE_FK`)
            REFERENCES `T_ROLE` (`ID`)
 ;

ALTER TABLE `T_CUSTOMER`
    ADD CONSTRAINT `FK_ROLE_FK`
        FOREIGN KEY (`ROLE_FK`)
            REFERENCES `T_ROLE` (`ID`)
 ;
 
ALTER TABLE `T_CUSTOMER`
	ADD CONSTRAINT `FK_ACCOUNT_FK`
        FOREIGN KEY (`ACCOUNT_FK`)
            REFERENCES `T_ACCOUNT` (`ID`)			
 ; 

ALTER TABLE `T_PROSPECTCUSTOMER`
    ADD CONSTRAINT `FK_ROLE_FK`
        FOREIGN KEY (`ROLE_FK`)
            REFERENCES `T_ROLE` (`ID`)
 ;
 
ALTER TABLE `T_ACCOUNT`
    ADD CONSTRAINT `FK_CUSTOMER_FK`
        FOREIGN KEY (`CUSTOMER_FK`)
            REFERENCES `T_CUSTOMER` (`ID`)
 ;



-- Cleanup

DELETE FROM T_BANKCLERK;
DELETE FROM T_CUSTOMER;
DELETE FROM T_PROSPECTCUSTOMER;
DELETE FROM T_OPENACCOUNTREQUEST;
DELETE FROM T_ROLE;
DELETE FROM T_ACCOUNT;

-- Load

/* Roles */
INSERT INTO T_ROLE VALUES('1','ROLE_BANKCLERKHEAD');
INSERT INTO T_ROLE VALUES('2','ROLE_BANKCLERK');
INSERT INTO T_ROLE VALUES('3','ROLE_CUSTOMER');
INSERT INTO T_ROLE VALUES('4','ROLE_PROSPECTCUSTOMER');

/* Bank clerk heads */
INSERT INTO T_BANKCLERK VALUES ('Isa001', 'Isabelle', 'Deligniere', 'deligniere.isabelle@orange.fr', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '1');
INSERT INTO T_BANKCLERK VALUES ('Serge002', 'Serge', 'Duval', 'serge.duval@hotmail.fr', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '1');
INSERT INTO T_BANKCLERK VALUES ('Pascal002', 'Pascal', 'Leroy', 'pascal.leroy@gmail.fr', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '1');

/* Bank clerks  */
INSERT INTO T_BANKCLERK VALUES ('James001', 'James', 'Gosling', 'james.gosling@java.com', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '2');
INSERT INTO T_BANKCLERK VALUES ('Rod001', 'Rod', 'Johnson', 'rod.johnson@spring.com', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '2');

/* Customers */
/* Date format : YYYY/MM/DD */
/** Mocha Bank customers **/
INSERT INTO T_CUSTOMER VALUES ('Serge001', 'Serge', 'Duval', 'serge.duval@hotmail.fr', '1990/03/06',  '0606060606', 'CNAM', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', '292 Rue Saint-Martin', '', '75003', 'Paris', '3', '1');
INSERT INTO T_CUSTOMER VALUES ('Anne001', 'Anne', 'Dupont', 'anne.dupont@gmail.com', '1962/01/07',  '0660606060', '', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', 'rue de Paris', '', '29200', 'Brest', '3', '2');
INSERT INTO T_CUSTOMER VALUES ('Jacques001', 'Jacques', 'Dupont', 'jacques.dupont@hotmail.fr', '1964/04/12',  '0600000000', '', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', 'rue de Paris', '', '29200', 'Brest', '3', '3');
INSERT INTO T_CUSTOMER VALUES ('Eva001', 'Eva', 'Durand', 'eva.durand@gmail.com', '1979/02/11',  '0666666666', '', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', 'rue de Brest', '', '75000', 'Paris', '3', '4');
INSERT INTO T_CUSTOMER VALUES ('Marc001', 'Marc', 'Durand', 'marc.durand@hotmail.fr', '1977/06/03',  '0606060606', 'Durand et Durand', '$2a$10$id93M61FLpdk9sXOBzZlsuhNETXn7YsjyBZs3X09Ll7f5S3lqpee2', 'rue de Brest', 'BP12', '75000', 'Paris', '3', '5');

/** Other bank customers **/
INSERT INTO T_CUSTOMER VALUES ('Pascal001', 'Pascal', 'Leroy', '', null,  '', '', '', '', '', '', '', '3', '111');
INSERT INTO T_CUSTOMER VALUES ('Stéphane001', 'Stéphane', 'Moreau', '', null,  '', '', '', '', '', '', '', '3', '222');
INSERT INTO T_CUSTOMER VALUES ('Aurélie001', 'Aurélie', 'Thomas', '', null,  '', '', '', '', '', '', '', '3', '333');
INSERT INTO T_CUSTOMER VALUES ('Richard001', 'Richard', 'Martin', '', null,  '', '', '', '', '', '', '', '3', '444');
INSERT INTO T_CUSTOMER VALUES ('Julia001', 'Julia', 'Martin', '', null,  '', '', '', '', '', '', '', '3', '555');

/* Accounts */
/* Internal accounts (Mocha Bank) */
INSERT INTO T_ACCOUNT VALUES('1','Compte courant','1000','1234','Mocha Bank','Serge001');
INSERT INTO T_ACCOUNT VALUES('2','Compte courant','1000','1234','Mocha Bank','Anne001');
INSERT INTO T_ACCOUNT VALUES('3','Compte courant','1000','1234','Mocha Bank','Jacques001');
INSERT INTO T_ACCOUNT VALUES('4','Compte courant','1000','1234','Mocha Bank','Eva001');
INSERT INTO T_ACCOUNT VALUES('5','Compte courant','1000','1234','Mocha Bank','Marc001');

/* External accounts (Other Bank) */
INSERT INTO T_ACCOUNT VALUES('111','Pascal Leroy','1000','5678','Other Bank','Pascal001');
INSERT INTO T_ACCOUNT VALUES('222','Stéphane Moreau','1000','5678','Other Bank','Stéphane001');
INSERT INTO T_ACCOUNT VALUES('333','Aurélie Dubois','1000','5678','Other Bank','Aurélie001');
INSERT INTO T_ACCOUNT VALUES('444','Richard Martin','1000','5678','Other Bank','Richard001');
INSERT INTO T_ACCOUNT VALUES('555','Julia Martin','1000','5678','Other Bank','Julia001');















