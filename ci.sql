
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

-- page 试卷表
create table uek_evaluate_pages
(
	id int auto_increment
		primary key,
	num varchar(255) null comment '按条件生成的一个编号',
	status tinyint default 1 not null comment '0 未组卷 1 已组卷',
	course_id int not null comment '所属课程_id',
	create_time datetime not null comment '生成试卷的时间',
	creater_id int default 0 not null comment '创建者id',
	totalscores int default 0 null comment '总分数',
	name varchar(255) null,
	chapter_id int null,
	company_id bigint not null,
	day_id bigint not null,
	direction_id bigint not null,
	stage_id bigint not null,
	user_member_id bigint not null
) comment '试卷';

-- 插入试卷
INSERT INTO uek_evaluate_pages (id, num, status, course_id, create_time, creater_id, totalscores, name, chapter_id, company_id, day_id, direction_id, stage_id, user_member_id) VALUES (1, '53418', 2, 1, '2018-03-21 13:00:00', 0, 100, '面向对象程学设计第一章测试', 3, 0, 0, 0, 0, 0);
INSERT INTO uek_evaluate_pages (id, num, status, course_id, create_time, creater_id, totalscores, name, chapter_id, company_id, day_id, direction_id, stage_id, user_member_id) VALUES (2, '29435', 2, 1, '2018-03-21 13:00:00', 0, 100, '面向对象程学设计第二章测试', 4, 0, 0, 0, 0, 0);
INSERT INTO uek_evaluate_pages (id, num, status, course_id, create_time, creater_id, totalscores, name, chapter_id, company_id, day_id, direction_id, stage_id, user_member_id) VALUES (3, '29436', 2, 1, '2018-03-30 19:52:43', 0, 100, '面向对象程学设计第三章测试', 5, 0, 0, 0, 0, 0);
INSERT INTO uek_evaluate_pages (id, num, status, course_id, create_time, creater_id, totalscores, name, chapter_id, company_id, day_id, direction_id, stage_id, user_member_id) VALUES (4, null, 2, 1, '2018-04-07 11:22:35', 0, 0, '面向对象第4章测试', 6, 0, 0, 0, 0, 0);

select * from uek_evaluate_pages;

-- 创建班级表
create table uek_acdemic_class
(
	id int(4) auto_increment
		primary key,
	num varchar(255) not null
)
comment '班级表';

INSERT INTO eva.uek_acdemic_class (id, name) VALUES (1, '17130101');
INSERT INTO eva.uek_acdemic_class (id, name) VALUES (2, '17130102');

select * from uek_acdemic_class;

-- 创建班级 试卷表
CREATE TABLE `uek_evaluate_class_pages` (
  `id` int(11)  AUTO_INCREMENT primary key,
  `pages_id` int(11) NOT NULL COMMENT '试卷id',
  `class_id` int(11) NOT NULL COMMENT '班级id',
  `start_time` datetime DEFAULT NULL COMMENT '开考时间',
  `end_time` datetime DEFAULT NULL COMMENT '闭考时间',
  `invigilator` tinyint(4) DEFAULT NULL COMMENT '监考老师',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `add_time` varchar(255) COMMENT '加入记录的时间',
  `employee_id` bigint(20) DEFAULT NULL
);

-- 插入数据
INSERT INTO uek_evaluate_class_pages (id, pages_id, class_id, start_time, end_time, invigilator, comment, add_time, employee_id) VALUES (1, 1, 1, '2018-03-23 08:00:00', '2018-04-27 22:00:00', null, null, '2018-04-03 17:26:46', null);


select * from uek_evaluate_class_pages;

-- 试卷 题目表
create table uek_evaluate_pages_title
(
	id int(10) auto_increment
		primary key,
	pages_id int(10) not null comment '试卷id',
	title_id int(10) not null comment '题目id'
)
comment '试卷和题目中间表';

INSERT INTO uek_evaluate_pages_title (id, pages_id, title_id) VALUES (1, 1, 1583);

select * from uek_evaluate_pages_title;

-- 试题表

create table uek_evaluate_titles
(
	id int auto_increment comment 'id'
		primary key,
	num text null,
	title text not null comment '题上内容，也就是题干',
	category tinyint null comment '题的类型：1单选 2填空 3简答 4程序',
	difficulty tinyint null comment '难度，有5个级别',
	answer text null comment '题的答案',
	analysis text null comment '题的解析',
	teacher_id int null comment '出题人id，老师出题，如果是企业面试则是企业名',
	add_time timestamp null comment '添加题目的时间',
	sectiona text null,
	sectionb text null,
	sectionc text null,
	sectiond text null,
	orderd tinyint(1) default 0 null,
	knowledge_id int null
)
comment '题目';

-- 插入试题

INSERT INTO uek_evaluate_titles (id, num, title, category, difficulty, answer, analysis, teacher_id, add_time, sectiona, sectionb, sectionc, sectiond, orderd, knowledge_id) VALUES (1553, null, 'java语言是由那家计算机公司发布的__________.', 2, 1, '【Sun】', '无', 1, '2018-03-23 19:00:37', null, null, null, null, 0, 1436);

select * from uek_evaluate_titles;