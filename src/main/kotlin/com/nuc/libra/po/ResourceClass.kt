package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/5/8 19:20
 */
@Entity
@Table(name = "nuc_tracking_resource_class")
class ResourceClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var classId: Long = 0
    var resourceId: Long = 0
    var courseId: Long = 0
    var knowledgeId: Long = 0
    var startTime: Long = 0
    var endTime: Long = 0
}
