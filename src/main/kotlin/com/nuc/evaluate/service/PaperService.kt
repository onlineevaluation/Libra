package com.nuc.evaluate.service

import com.nuc.evaluate.po.ClassAndPages
import com.nuc.evaluate.po.Page

/**
 * @author 杨晓辉 2018/2/3 15:55
 */
interface PaperService {
    fun getOneClassPaper(classId: Long): List<ClassAndPages>
}