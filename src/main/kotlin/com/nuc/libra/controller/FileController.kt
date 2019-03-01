package com.nuc.libra.controller

import com.nuc.libra.result.Result
import com.nuc.libra.util.ResultUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

/**
 * @author 杨晓辉 2019-02-14 15:50
 */
@RestController
@RequestMapping("/file")
class FileController {


    @PostMapping("/upload")
    fun upload(multipartFile: MultipartFile): Result {
        val path = "E:\\testData\\web.txt"
        val targetFile = File(path)
        multipartFile.transferTo(targetFile)
        return ResultUtils.success()
    }


}
