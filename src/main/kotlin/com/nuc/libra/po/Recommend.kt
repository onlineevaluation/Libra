package com.nuc.libra.po

import java.sql.Timestamp
import javax.persistence.*

/**
 * @author 杨晓辉 5/3/2019 9:11 AM
 */
@Entity
@Table(name = "nuc_libra_recommend")
class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    /**
     * student id
     */
    var stundetId = 0

    /**
     * 知识点id
     */
    var knowledgeId = 0


    /**
     * 资源id
     */
    var resourceId = 0

    /**
     * 资源类型
     */
    var resourceType = 0

    /**
     * 创建时间
     */
    lateinit var createData: Timestamp

}