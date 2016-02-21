ALTER TABLE `sport`.`sport_company` ADD CONSTRAINT `fk_company_area` FOREIGN KEY (`areaId`) 
REFERENCES `sport`.`sport_area`(`id`);

