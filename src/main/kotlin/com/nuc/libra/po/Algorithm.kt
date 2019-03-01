package com.nuc.libra.po

import javax.persistence.*

/**
 * @author 杨晓辉 2019/2/25 16:06
 */
@Entity
@Table(name = "nuc_libra_algorithm")
class Algorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    /**
     * 算法名称/题目
     */
    lateinit var title: String

    /**
     * 测试数据集
     * 改数据集是 json 格式数据集。
     * 该数据是通过TestSet生成的json数据
     */
    @Column(columnDefinition = "text")
    lateinit var testSet: String
    /**
     * 运行限制时间
     * 单位毫秒
     */
    var limitTime: Int = 0

    /**
     * 运行内存限制
     * 单位 kb？
     */
    var limitMemory: Int = 0
}

/**
 * 数据集
 */
class TestSet {
    var datas = ArrayList<DataSet>()
}

/**
 * 输入输出数据
 */
class DataSet {
    lateinit var input: String
    lateinit var output: String
}
