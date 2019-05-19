package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/17 17:22
 * 课程班级表
 */
@Entity
@Table(name = "uek_acdemic_course_class")
class CourseClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
    var teacherId: Long = 0L
    var classId: Long = 0L
    var courseId: Long = 0L
}

