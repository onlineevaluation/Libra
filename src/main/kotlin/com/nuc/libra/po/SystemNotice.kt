package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2019/5/19 20:26
 * 系统通知信息
 */
@Entity
@Table(
    name = "nuc_libra_system_notice",
    indexes = [Index(name = "id", columnList = "id")]
)
class SystemNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    /**
     * 通知信息
     */
    lateinit var message: String

    /**
     * 发布时间
     */
    lateinit var createTime: Timestamp

    /**
     * 创建者id 该id只能是拥有管理员权限的id才可以
     */
    var createrId: Long = 0

    /**
     * 该通知发给谁
     * 1 全局
     * 2 教师
     * 3 学生
     */
    lateinit var belongId: String


    override fun toString(): String {
        return "SystemNotice(id=$id, message='$message', createTime=$createTime, createrId=$createrId, belongId='$belongId')"
    }


}