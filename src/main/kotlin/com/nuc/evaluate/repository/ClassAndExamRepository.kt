package com.nuc.evaluate.repository

import com.nuc.evaluate.po.ClassAndExam
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2018/2/4 15:49
 */
@Repository
interface ClassAndExamRepository : JpaRepository<ClassAndExam, Long> {

    fun findByPaperIdAndClassId(paperId: Long, classId: Long): ClassAndExam?
}