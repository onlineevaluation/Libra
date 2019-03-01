package com.nuc.libra.util

import com.alibaba.fastjson.JSON
import com.nuc.libra.po.DataSet
import com.nuc.libra.po.TestSet
import com.nuc.libra.vo.Code
import java.io.File
import java.util.*

/**
 * @author 杨晓辉 2019/2/28 14:41
 */
class makeFileTest {


}

fun main() {


    val testData = TestSet()
    for (i in 0..3) {
        val ds = DataSet()
        ds.input = "#$%$i,4%$#"
        ds.output = "#$%${i+4}%$#"

        testData.datas.add(ds)
    }

    val jsonString = JSON.toJSONString(testData)
    println(jsonString)

}