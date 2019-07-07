package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 2019/5/5 11:51
 */
@Entity
@Table(name = "uek_resource_dirctory_file")
class ResourceDirctory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var name: String? = null
    var pid: Long = 0
    var addtime: Long  =0L
    var url: String? = null
    var type: Long = 0
    var size: String? = null
    var percent: Float = 0f
    var courseId: Long = 0
    var chapterId: Long = 0
    var knowledgeId: Long = 0
    var playTimes:Long = 0
}