package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2018/2/3 14:48
 */
@Entity
@Table(name = "uek_acdemic_students")
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var name: String? = null
    var studentNumber: String? = null
    var proTeamId: Long? = 0
    var status: Long? = 0
    var gender: Long? = 0
    var nation: String? = null
    var phone: String? = null
    var qq: String? = null
    var email: String? = null
    var idcard: String? = null
    var memberId: Long? = 0
    var userId: Long = 0
    var classId: Long = 0

    override fun toString(): String {
        return "Student(id=$id, name=$name, studentNumber=$studentNumber, proTeamId=$proTeamId, status=$status, gender=$gender, nation=$nation, phone=$phone, qq=$qq, email=$email, idcard=$idcard, memberId=$memberId, userId=$userId, classId=$classId)"
    }


}