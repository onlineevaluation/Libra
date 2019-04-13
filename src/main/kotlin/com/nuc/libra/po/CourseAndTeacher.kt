package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/12 20:20
 * 课程教师对应中间表
 */
@Entity
@Table(
    name = "nuc_libra_course_teacher",
    indexes = [
        Index(name = "id", columnList = "id"),
        Index(name = "course_id", columnList = "courseId"),
        Index(name = "teacher_id", columnList = "teacherId")

    ]
)
class CourseAndTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    /**
     * 教师id
     */
    var teacherId: Long = 0L

    /**
     * 课程id
     */
    var courseId: Long = 0L

}