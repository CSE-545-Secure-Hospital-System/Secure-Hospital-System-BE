--drop table if exists role;
--drop table if exists user;
--drop table if exists user_roles;
--create table role (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=MyISAM;
--create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=MyISAM;
--
--
--INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ADMIN');
--INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');

--
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('1', 'ADMIN');
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('2', 'PATIENT');
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('3', 'HOSPITAL_STAFF');
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('4', 'LAB_STAFF');
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('5', 'INSURANCE_STAFF');
INSERT INTO `cse545`.`roles` (`role_id`, `role`) VALUES ('6', 'DOCTOR');
