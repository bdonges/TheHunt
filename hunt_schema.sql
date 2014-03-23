SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `hunt` DEFAULT CHARACTER SET utf8 ;
USE `hunt` ;

-- -----------------------------------------------------
-- Table `hunt`.`accounts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`accounts` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`accounts` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `first_name` VARCHAR(45) NULL ,
  `last_name` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  `phone_number` VARCHAR(20) NULL ,
  `username` VARCHAR(45) NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hunt`.`hunts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`hunts` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`hunts` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `account_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL ,
  `run_date` DATETIME NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_hunts_accounts1_idx` (`account_id` ASC) ,
  CONSTRAINT `fk_hunts_accounts1`
    FOREIGN KEY (`account_id` )
    REFERENCES `hunt`.`accounts` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hunt`.`locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`locations` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`locations` (
  `int` INT(11) NOT NULL AUTO_INCREMENT ,
  `hunt_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `code` VARCHAR(45) NULL DEFAULT NULL ,
  `key` VARCHAR(45) NULL DEFAULT NULL ,
  `address` VARCHAR(45) NULL DEFAULT NULL ,
  `phone` VARCHAR(20) NULL DEFAULT NULL ,
  `special_location_id` INT(11) NULL DEFAULT NULL ,
  `has_special` VARCHAR(1) NULL DEFAULT NULL ,
  PRIMARY KEY (`int`) ,
  INDEX `fk_locations_hunts1_idx` (`hunt_id` ASC) ,
  CONSTRAINT `fk_locations_hunts1`
    FOREIGN KEY (`hunt_id` )
    REFERENCES `hunt`.`hunts` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hunt`.`teams`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`teams` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`teams` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `hunt_id` INT NOT NULL ,
  `name` VARCHAR(45) NULL DEFAULT NULL ,
  `score` INT(11) NULL DEFAULT NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_teams_hunts1_idx` (`hunt_id` ASC) ,
  CONSTRAINT `fk_teams_hunts1`
    FOREIGN KEY (`hunt_id` )
    REFERENCES `hunt`.`hunts` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hunt`.`players`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`players` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`players` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `team_id` INT(11) NOT NULL ,
  `first_name` VARCHAR(45) NULL DEFAULT NULL ,
  `last_name` VARCHAR(45) NULL DEFAULT NULL ,
  `email` VARCHAR(255) NULL DEFAULT NULL ,
  `phone_number` VARCHAR(20) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `fk_members_teams_idx` (`team_id` ASC) ,
  CONSTRAINT `fk_members_teams`
    FOREIGN KEY (`team_id` )
    REFERENCES `hunt`.`teams` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hunt`.`questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`questions` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`questions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `location_id` INT(11) NOT NULL ,
  `question` VARCHAR(200) NULL DEFAULT NULL ,
  `answer` VARCHAR(45) NULL DEFAULT NULL ,
  `points` INT NULL ,
  `question_order` INT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_questions_locations1_idx` (`location_id` ASC) ,
  CONSTRAINT `fk_questions_locations1`
    FOREIGN KEY (`location_id` )
    REFERENCES `hunt`.`locations` (`int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hunt`.`team_locations`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`team_locations` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`team_locations` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `team_id` INT(11) NOT NULL ,
  `location_int` INT(11) NOT NULL ,
  `hunt_id` INT NULL ,
  `code_entered_date` DATETIME NULL DEFAULT NULL ,
  `code_entered` VARCHAR(45) NULL DEFAULT NULL ,
  `locked_out` VARCHAR(1) NULL DEFAULT NULL ,
  `questions_submitted_date` DATETIME NULL DEFAULT NULL ,
  `score` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_team_locations_teams1_idx` (`team_id` ASC) ,
  INDEX `fk_team_locations_locations1_idx` (`location_int` ASC) ,
  CONSTRAINT `fk_team_locations_teams1`
    FOREIGN KEY (`team_id` )
    REFERENCES `hunt`.`teams` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_team_locations_locations1`
    FOREIGN KEY (`location_int` )
    REFERENCES `hunt`.`locations` (`int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hunt`.`team_answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hunt`.`team_answers` ;

CREATE  TABLE IF NOT EXISTS `hunt`.`team_answers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `team_location_id` INT(11) NOT NULL ,
  `question_id` INT(11) NULL DEFAULT NULL ,
  `answer` VARCHAR(200) NULL DEFAULT NULL ,
  `score` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_team_answers_team_locations1_idx` (`team_location_id` ASC) ,
  CONSTRAINT `fk_team_answers_team_locations1`
    FOREIGN KEY (`team_location_id` )
    REFERENCES `hunt`.`team_locations` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

USE `hunt` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
