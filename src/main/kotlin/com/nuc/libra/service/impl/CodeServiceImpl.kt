package com.nuc.libra.service.impl

import com.nuc.libra.repository.AlgorithmRepository
import com.nuc.libra.repository.StudentScoreRepository
import com.nuc.libra.service.CodeService
import com.nuc.libra.vo.Code
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

/**
 * @author 杨晓辉 2019-02-11 10:34
 * 该类暂时无用
 */
@Service
class CodeServiceImpl : CodeService {


    @Autowired
    private lateinit var algorithmRepository: AlgorithmRepository

    /**
     * 运行代码块
     */
    override fun runCode(code: Code, language: String, pageId: Long, studentId: Long): String {

        val inputPath = "d:/page_$pageId/student_$studentId/${code.id}_${Date().time}.cpp"
        val outputPath = "d:/page_out_$pageId/student_$studentId"
        val outFile = File(outputPath)
        if (!outFile.exists()) {
            outFile.mkdirs()
        }
        val fileName = "${code.id}_${Date().time}_out"
        // 文件存储
        val codePath = File(inputPath)
        if (!codePath.exists()) {
            codePath.createNewFile()
        }
        codePath.writeText(code.codeString, charset = Charsets.UTF_8)
        //获取数据库答案
        val codeData = algorithmRepository.findById(code.id).get()
        return ""
    }

}


