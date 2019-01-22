
--
CREATE DATABASE IF NOT EXISTS eva  DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;;
--
USE `eva`;

-- 创建用户表

DROP TABLE IF EXISTS `uek_privilege_user`;
CREATE TABLE `uek_privilege_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1555 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- 插入用户数据

INSERT INTO uek_privilege_user (id, username, password, status) VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', 2);
INSERT INTO uek_privilege_user (id, username, password, status) VALUES (811, '1713010101', '$2a$10$ZtD1ocHypusagLSbqD/YFuA//y.juMJeawA9wQfTdZzw6l8iMIQnO', 1);

SELECT * from uek_privilege_user;

-- 创建学生表

DROP TABLE IF EXISTS `uek_acdemic_students`;
CREATE TABLE `uek_acdemic_students` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `student_number` varchar(225) DEFAULT NULL,
  `pro_team_id` int(11) DEFAULT '0' COMMENT '项目组id',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0 禁用  1 计划学员 2 正式学员 ',
  `gender` tinyint(4) DEFAULT NULL COMMENT '0女 1男',
  `nation` varchar(50) CHARACTER SET gbk DEFAULT NULL COMMENT '民族',
  `phone` varchar(20) CHARACTER SET gbk DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET gbk DEFAULT NULL,
  `email` varchar(50) CHARACTER SET gbk DEFAULT NULL,
  `idcard` varchar(50) CHARACTER SET gbk DEFAULT NULL,
  `member_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_stu_pro_team_fk` (`pro_team_id`) COMMENT '(null)',
  KEY `tg_user_students_fk` (`user_id`) COMMENT '(null)',
  KEY `tg_class_student_fk` (`class_id`) COMMENT '(null)'
) ENGINE=InnoDB AUTO_INCREMENT=1516 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='学生表';


-- 插入学生数据

INSERT INTO `uek_acdemic_students` VALUES ('782', '张晋霞', '1713010101', '0', '2', null, null, null, null, null, null, null, '811', '1');

select * from uek_acdemic_students;



select * from uek_privilege_user where id = (select user_id from uek_acdemic_students where student_number='1713010101');


-- 创建角色表
create table uek_privilege_role
(
	id int auto_increment
		primary key,
	name varchar(20) not null,
	about varchar(100) null
)ENGINE=InnoDB AUTO_INCREMENT=1516 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- 插入角色数据

INSERT INTO uek_privilege_role (id, name, about) VALUES (1, '超级管理员', null);
INSERT INTO uek_privilege_role (id, name, about) VALUES (2, '教师', '教师');
INSERT INTO uek_privilege_role (id, name, about) VALUES (3, '学生', '学生');


select * from uek_privilege_role;
-- 创建用户角色表

create table uek_privilege_user_role
(
	id int auto_increment
		primary key,
	user_id int not null,
	role_id int not null,
	constraint FKgh5u057fg34144pb86hb6yp80
		foreign key (user_id) references uek_privilege_user (id),
	constraint FKr9h1knhw993ye9thrhdd3l6cp
		foreign key (role_id) references uek_privilege_role (id)
);

-- 查询用户和角色表
INSERT INTO uek_privilege_user_role (id, user_id, role_id) VALUES (1, 811, 3);

select * from uek_privilege_user_role;

-- 创建班级 试卷表
CREATE TABLE `uek_evaluate_class_pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pages_id` int(11) NOT NULL COMMENT '试卷id',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `start_time` datetime DEFAULT NULL COMMENT '开考时间',
  `end_time` datetime DEFAULT NULL COMMENT '闭考时间',
  `invigilator` tinyint(4) DEFAULT NULL COMMENT '监考老师',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '加入记录的时间',
  `employee_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='试卷和班级中间表\r\n'

-- 插入数据
INSERT INTO uek_evaluate_class_pages (id, pages_id, class_id, start_time, end_time, invigilator, comment, add_time, employee_id) VALUES (2, 1, 1, '2018-03-23 08:00:00', '2018-04-27 22:00:00', null, null, '2018-04-03 17:26:46', null);
INSERT INTO uek_evaluate_class_pages (id, pages_id, class_id, start_time, end_time, invigilator, comment, add_time, employee_id) VALUES (3, 2, 1, '2018-03-23 08:00:00', '2018-03-27 22:00:00', null, null, '2018-03-27 09:35:09', null);

select * from uek_evaluate_class_pages;