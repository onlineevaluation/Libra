package com.nuc.libra.service.impl

import com.nuc.libra.jni.Capricornus
import com.nuc.libra.jni.GoString
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
 */
@Service
class CodeServiceImpl : CodeService {

    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Autowired
    private lateinit var algorithmRepository: AlgorithmRepository

    /**
     * 运行代码块
     */
    override fun runCode(code: Code, language: String, pageId: Long, studentId: Long): String {

        // 判断系统
        val os = System.getProperty("os.name")

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


        val judgeCode = Capricornus.INSTANCE.judgeCode(
            GoString.ByValue(inputPath),
            GoString.ByValue(outputPath),
            GoString.ByValue(fileName),
            GoString.ByValue(codeData.testSet),
            codeData.limitTime
        )

        println(judgeCode)
        println("code is ${judgeCode.substring(5,6)}")
        // 判断 judge code
        when(judgeCode.substring(5,6)) {
            "9"-> {

            }
            "8"->{

            }
            "7"->{

            }
            "6"->{

            }
            "5"->{

            }
            "4"->{

            }
            "3"->{

            }
            "2"->{

            }
            "1"->{

            }
        }




        return ""
    }



    fun addTestSets() {



    }
}


