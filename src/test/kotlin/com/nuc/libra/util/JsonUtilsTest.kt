package com.nuc.libra.util

import com.alibaba.fastjson.JSON
import com.nuc.libra.vo.Result
import org.junit.Test

/**
 * @author 杨晓辉 2019-02-11 19:39
 */
class JsonUtilsTest {

    /**
     * 试卷提交测试
     */
    @Test
    fun jsonTest() {
        val json: String =
            " {\n" +
                    "  studentId: 1,\n" +
                    "  pageId: 1,\n" +
                    "  answer: [{\n" +
                    "      id: '1',\n" +
                    "      ans: 'A'\n" +
                    "  },\n" +
                    "  {\n" +
                    "      id: '37',\n" +
                    "      ans: '&amp;nbsp;'\n" +
                    "  },\n" +
                    "  {\n" +
                    "      id: '1024',\n" +
                    "      ans: \"（1） Grinder. 可视化的图形界面可以监控丰富的资源；报告可以导出到Word、Excel以及HTML格式。\"\n" +
                    "  },\n" +
                    "  ]\n" +
                    " }"

        val ob = JSON.parseObject(json, Result::class.java)
        println(ob)


    }

}