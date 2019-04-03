package com.nuc.libra.service

import com.nuc.libra.vo.ClassInfo
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/3 18:13
 */
@Service
interface ClassService {

    fun listAllClassByTeacherId(teacherId: Long): List<ClassInfo>
}