

--
-- Table structure for table `education`
--

DROP TABLE IF EXISTS `education`;

CREATE TABLE `education` (
  `education_id` binary(16) NOT NULL,
  `degree` text,
  `institution_name` text,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `field_of_study` text,
  `country` text,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`education_id`),
  UNIQUE KEY `education_id` (`education_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
CREATE TABLE `interest` (
  `interest_id` binary(16) NOT NULL,
  `interest_name` varchar(100) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`interest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
CREATE TABLE `language` (
  `language_id` binary(16) NOT NULL,
  `language_name` varchar(100) NOT NULL,
  `proficiency_level` varchar(50) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`language_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `travel_info`
--

DROP TABLE IF EXISTS `travel_info`;
CREATE TABLE `travel_info` (
  `travel_info_id` binary(16) NOT NULL,
  `available_to_travel` tinyint(1) DEFAULT NULL,
  `available_to_relocate` tinyint(1) DEFAULT NULL,
  `visa_type` varchar(30) DEFAULT NULL,
  `visa_expiration_date` date DEFAULT NULL,
  `passport_expiration_date` date DEFAULT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`travel_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
CREATE TABLE `certification` (
  `certification_id` binary(16) NOT NULL,
  `certification_name` text,
  `certification_year` date DEFAULT NULL,
  `link` text,
  `institution` text,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`certification_id`),
  UNIQUE KEY `certification_id` (`certification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `professional_experience`
--

DROP TABLE IF EXISTS `professional_experience`;
CREATE TABLE `professional_experience` (
  `experience_id` binary(16) NOT NULL,
  `experience_name` text,
  `role_name` text,
  `project_name` text,
  `experience_location` text,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `experience_description` text,
  `user_id` binary(16) NOT NULL,
  `to_present` tinyint DEFAULT NULL,
  `technologies` text,
  PRIMARY KEY (`experience_id`),
  UNIQUE KEY `experience_id` (`experience_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


--
-- Table structure for table `skill_category_catalog`
--

DROP TABLE IF EXISTS `skill_category_catalog`;
CREATE TABLE `skill_category_catalog` (
  `skill_category_id` binary(16) NOT NULL,
  `skill_category_name` text NOT NULL,
  `display_order` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`skill_category_id`),
  UNIQUE KEY `skill_category_id` (`skill_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


--
-- Table structure for table `skill_catalog`
--

DROP TABLE IF EXISTS `skill_catalog`;
CREATE TABLE `skill_catalog` (
  `skill_id` binary(16) NOT NULL,
  `skill_category_id` binary(16) NOT NULL,
  `skill_name` text NOT NULL,
  PRIMARY KEY (`skill_id`),
  UNIQUE KEY `skill_id` (`skill_id`),
  KEY `skill_category_id` (`skill_category_id`),
  CONSTRAINT `skill_catalog_ibfk_1` FOREIGN KEY (`skill_category_id`) REFERENCES `skill_category_catalog` (`skill_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;



--
-- Table structure for table `user_skill`
--

DROP TABLE IF EXISTS `user_skill`;
CREATE TABLE `user_skill` (
  `user_skill_id` binary(16) NOT NULL,
  `skill_id` binary(16) NOT NULL,
  `level` enum('L1_INTERN','L2_ASSOCIATE','L3_ENGINEER','L4_SR_ENGINEER','L5_ARCHI') NOT NULL DEFAULT 'L1_INTERN',
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`user_skill_id`),
  UNIQUE KEY `user_skill_id` (`user_skill_id`),
  KEY `skill_id` (`skill_id`),
  CONSTRAINT `user_skill_ibfk_1` FOREIGN KEY (`skill_id`) REFERENCES `skill_catalog` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile` (
  `profile_id` binary(16) NOT NULL,
  `profile_summary` text,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` binary(16) NOT NULL COMMENT 'Primary key that uniquely identifies each permission.',
  `name` varchar(50) NOT NULL COMMENT 'Internal name of the permission. Must be unique.',
  `display_name` varchar(120) DEFAULT NULL COMMENT 'User-friendly name to be displayed in the frontend.',
  `description` varchar(255) DEFAULT NULL COMMENT 'Optional field for additional information about the permission.',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp indicating when the record was created, stored in UTC and displayed in the session time zone.',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Indicates whether the permission is active. Can be used to disable permissions without deleting them.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Catalog of system permissions used to control access to features and actions.';

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` binary(16) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `display_name` varchar(120) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` enum('SYSTEM','CUSTOM') DEFAULT 'CUSTOM',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` binary(16) NOT NULL COMMENT 'Primary key that uniquely identifies each realtionship between permissions and roles.',
  `role_id` binary(16) NOT NULL COMMENT 'Primary key keep relationship between role_permission and role entities',
  `permission_id` binary(16) NOT NULL COMMENT 'Primary key keep relationship between role_permission and permission entities',
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Timestamp indicating when the record was created, stored in UTC and displayed in the session time zone.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_role_permission` (`role_id`,`permission_id`),
  KEY `role_permission_ibfk_2` (`permission_id`),
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Intermediate table representing the many-to-many association between roles and permissions in the system.';

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Indicates whether the user is active.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` binary(16) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `practice` varchar(50) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `lan_id` varchar(11) DEFAULT NULL COMMENT 'Internal employee identifier. Must be unique.',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `lan_id` (`lan_id`),
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `role_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_user_role` (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

