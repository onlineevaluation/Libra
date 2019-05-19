package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2019/5/19 19:53
 */
@Entity
@Table(
    name = "nuc_libra_logs_teacher",
    indexes = [Index(name = "id", columnList = "id")]
)
class LogsTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    /**
     * 操作时间
     */
    lateinit var doTime: Timestamp

    /**
     * 操作类型
     * a 增加 d 删除 u 修改
     */
    lateinit var option: String


    /**
     * 操作人id
     */
    var teacherId = 0L

}