package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/3 18:06
 */
@Entity
@Table(name = "nuc_libra_class_teacher")
class ClassAndTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    var teacherId: Long = 0L

    var classId: Long = 0L

}