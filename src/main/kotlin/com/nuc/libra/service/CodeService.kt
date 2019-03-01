package com.nuc.libra.service

import com.nuc.libra.vo.Code
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019-02-11 10:35
 */
@Service
interface CodeService {

    /**
     * @param code 学生代码
     * @param language 语言
     * @param pageId 试卷id
     * @param studentId 学生id
     * @return 返回运行结果
     */
    fun runCode(code: Code, language: String, pageId: Long, studentId: Long): String

}