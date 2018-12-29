CREATE DATABASE IF NOT EXISTS eva;

use eva;

create table uek_privilege_user
(
	id int auto_increment
		primary key,
	username varchar(255) not null,
	password varchar(255) not null,
	status tinyint not null
);

INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', 2);
INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (811, '1713010101', '96e79218965eb72c92a549dd5a330112', 1);