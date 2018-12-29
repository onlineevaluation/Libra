

CREATE DATABASE IF NOT EXISTS eva  DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;;

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

INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (1, 'admin', '96e79218965eb72c92a549dd5a330112', 2);
INSERT INTO eva.uek_privilege_user (id, username, password, status) VALUES (811, '1713010101', '96e79218965eb72c92a549dd5a330112', 1);

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

INSERT INTO `uek_acdemic_students` VALUES ('782', '张晋霞', '1713010101', '0', '2', null, null, null, null, null, null, null, '811', '1')

select * from eva.uek_acdemic_students;