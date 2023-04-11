CREATE DATABASE app;
USE app;
CREATE TABLE `employee` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hafqwjqe2e9bcpgyj6evm52ap` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `task` (
  `id` int NOT NULL,
  `completion_date` date DEFAULT NULL,
  `deadline_date` date DEFAULT NULL,
  `is_completed` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `starting_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `employee_id` (
  `employee_id` int DEFAULT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhgptt2jytmh2fqld6t3cmbyfy` (`employee_id`),
  CONSTRAINT `FK4non5ircs877eijahprqli2r9` FOREIGN KEY (`id`) REFERENCES `task` (`id`),
  CONSTRAINT `FKhgptt2jytmh2fqld6t3cmbyfy` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `app`.`employee` (`id`,`name`) VALUES (1,'Иванов И.И.');
INSERT INTO `app`.`employee` (`id`,`name`) VALUES (2,'Карп В.Ю.');
INSERT INTO `app`.`employee` (`id`,`name`) VALUES (3,'Воронин П.А.');
INSERT INTO `app`.`employee` (`id`,`name`) VALUES (4,'Шурп К.П.ы');

INSERT INTO `app`.`task`(`id`,`completion_date`,`deadline_date`,`is_completed`,`name`,`starting_date`)
VALUES (1,'2023-05-14','2023-06-14',false,'Task 1','2023-04-25');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (2, '2023-04-18', '2023-05-13', true, 'Task 2', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (3, '2023-04-11', '2023-04-12', true, 'Task 3', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (4, '2023-04-20', '2023-04-20', true, 'Task 4', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (5, '2023-04-15', '2023-04-15', false, 'Task 5', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (6, '2023-04-16', '2023-04-30', false, 'Task 6', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (7, '2023-04-13', '2023-04-14', true, 'Task 7', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (8, '2023-04-20', '2023-04-25', false, 'Task 8', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (9, '2023-04-13', '2023-05-10', true, 'Task 9', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (10, '2023-04-20', '2023-04-30', true, '10', '2023-04-11');
INSERT INTO task (id, completion_date, deadline_date, is_completed, name, starting_date) 
VALUES (11, '2023-04-12', '2023-04-16', true, 'Task 11', '2023-04-11');

INSERT INTO `app`.`employee_id` (`employee_id`, `id`) 
VALUES (1, 2), (1, 5), (1, 6);
INSERT INTO `app`.`employee_id` (`employee_id`, `id`) 
VALUES (2, 1), (2, 4);
INSERT INTO `app`.`employee_id` (`employee_id`, `id`) 
VALUES (3, 3), (3, 7), (3, 8);


