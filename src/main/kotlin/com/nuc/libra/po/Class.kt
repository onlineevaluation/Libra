package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/4/3 18:16
 */
@Entity
@Table(name = "uek_acdemic_class", indexes = [Index(name = "id", columnList = "id")])
class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    /**
     * 班级号
     */
    lateinit var name: String
}