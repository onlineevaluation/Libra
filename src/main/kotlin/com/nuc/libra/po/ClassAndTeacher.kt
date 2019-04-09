package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/3 18:06
 */
@Entity
@Table(
    name = "nuc_libra_class_teacher", indexes = [Index(name = "id", columnList = "id"),
        Index(name = "teacher_id", columnList = "teacherId"),
        Index(name = "class_id", columnList = "classId")]
)
class ClassAndTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var teacherId: Long = 0L

    var classId: Long = 0L

}