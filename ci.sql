

CREATE DATABASE IF NOT EXISTS eva  DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;;

USE `eva`;

-- 创建用户表

create table uek_privilege_user
(
	id int auto_increment
		primary key,
	username varchar(255) not null,
	password varchar(255) not null,
	status tinyint not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;;

-- 插入用户数据

INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', 2);
INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (811, '1713010101', '96e79218965eb72c92a549dd5a330112', 1);

SELECT * from uek_privilege_user;

-- 创建学生表

create table uek_acdemic_students
(
	id int auto_increment
		primary key,
	name varchar(50) null,
	student_number varchar(225) null,
	pro_team_id int default 0 null comment '项目组id',
	status tinyint default 2 not null comment '0 禁用  1 计划学员 2 正式学员 ',
	gender tinyint null comment '0女 1男',
	nation varchar(50) charset gbk null comment '民族',
	phone varchar(20) charset gbk null,
	qq varchar(20) charset gbk null,
	email varchar(50) charset gbk null,
	idcard varchar(50) charset gbk null,
	member_id int null,
	user_id int null,
	class_id int null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create index t_stu_pro_team_fk
	on uek_acdemic_students (pro_team_id)
	comment '(null)';

create index tg_class_student_fk
	on uek_acdemic_students (class_id)
	comment '(null)';

create index tg_user_students_fk
	on uek_acdemic_students (user_id)
	comment '(null)';

-- 插入学生数据

INSERT INTO eva.uek_acdemic_students (id, name, student_number, pro_team_id, status, gender, nation, phone, qq, email, idcard, member_id, user_id, class_id) VALUES (782, '张晋霞', '1713010101', 0, 2, null, null, null, null, null, null, null, 811, 1);

select * from eva.uek_acdemic_students;