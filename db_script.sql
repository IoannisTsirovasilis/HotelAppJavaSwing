-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hotel_app
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hotel_app
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel_app` DEFAULT CHARACTER SET utf8 ;
USE `hotel_app` ;

-- -----------------------------------------------------
-- Table `hotel_app`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_app`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` NVARCHAR(128) NOT NULL,
  `surname` NVARCHAR(128) NOT NULL,
  `email` VARCHAR(256) NOT NULL,
  `mobile` VARCHAR(20) NOT NULL,
  `password_hash` VARCHAR(512) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_users_roles_idx` (`role_id` ASC),
  CONSTRAINT `FK_users_roles`
    FOREIGN KEY (`role_id`)
    REFERENCES `hotel_app`.`roles` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_app`.`room_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`room_statuses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_app`.`booking_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`booking_statuses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_app`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`rooms` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `price` DECIMAL(6,2) NOT NULL,
  `description` VARCHAR(128) NULL,
  `capacity` INT NOT NULL,
  `image_encoded` TEXT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_rooms_room_statuses_idx` (`status_id` ASC),
  CONSTRAINT `FK_rooms_room_statuses`
    FOREIGN KEY (`status_id`)
    REFERENCES `hotel_app`.`room_statuses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hotel_app`.`bookings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel_app`.`bookings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `persons` INT NOT NULL,
  `total_price` DECIMAL(6,2) NOT NULL,
  `check_in` DATE NOT NULL,
  `check_out` DATE NOT NULL,
  `status_id` INT NOT NULL,
  `room_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_bookings_booking_statuses_idx` (`status_id` ASC),
  INDEX `FK_bookings_users_idx` (`user_id` ASC),
  INDEX `FK_bookings_rooms_idx` (`room_id` ASC),
  CONSTRAINT `FK_bookings_booking_statuses`
    FOREIGN KEY (`status_id`)
    REFERENCES `hotel_app`.`booking_statuses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_bookings_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `hotel_app`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_bookings_rooms`
    FOREIGN KEY (`room_id`)
    REFERENCES `hotel_app`.`rooms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
