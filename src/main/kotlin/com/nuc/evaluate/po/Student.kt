package com.nuc.evaluate.po

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
    var status: Long ?= 0
    var gender: Long? = 0
    var nation: String? = null
    var school: String? = null
    var major: String? = null
    @Column(name = "gradutionTime")
    var graduationTime: java.sql.Date? = null
    var education: String? = null
    var workException: String? = null
    var birthday: java.sql.Date? = null
    var married: Long ?= 0
    var politicsStatus: String? = null
    var address: String? = null
    var cenRegister: String? = null
    var fTel: String? = null
    var phone: String? = null
    var qq: String? = null
    var email: String? = null
    var idcard: String? = null
    var urgency: String? = null
    var relation: String? = null
    var uTel: String? = null
    var memberId: Long? = 0
    var userId: Long = 0
    var classId: Long? = 0
    var clientId: Long? = 0
    var rentalId: Long? = 0
    var city: String? = null
    var avatar: String? = null
    var wechatname: String? = null

}