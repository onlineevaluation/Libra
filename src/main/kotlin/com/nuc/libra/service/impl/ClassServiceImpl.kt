package com.nuc.libra.service.impl

import com.nuc.libra.exception.ResultException
import com.nuc.libra.repository.ClassAndTeacherRepository
import com.nuc.libra.repository.ClassRepository
import com.nuc.libra.service.ClassService
import com.nuc.libra.vo.ClassInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/3 18:13
 */
@Service
class ClassServiceImpl : ClassService {

    @Autowired
    private lateinit var classAndTeacherRepository: ClassAndTeacherRepository


    @Autowired
    private lateinit var classRepository: ClassRepository

    /**
     * 通过教师 id 获取该教师所教授班级
     * @param teacherId 教师 id
     */
    override fun listAllClassByTeacherId(teacherId: Long): List<ClassInfo> {
        val classList = ArrayList<ClassInfo>()
        val list =
            classAndTeacherRepository.findClassAndTeachersByTeacherId(teacherId)
                    ?: throw  ResultException("没有该班级", 500)
        list.forEach {
            val `class` = classRepository.findById(it.classId).get()
            classList.add(ClassInfo(`class`.id, `class`.num))
        }
        return classList
    }


}