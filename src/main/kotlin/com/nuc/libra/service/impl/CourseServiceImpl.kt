package com.nuc.libra.service.impl

import com.nuc.libra.po.Course
import com.nuc.libra.repository.CourseClassRepository
import com.nuc.libra.repository.CourseRepository
import com.nuc.libra.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/4/11 14:57
 */
@Service
class CourseServiceImpl : CourseService {

    @Autowired
    private lateinit var courseRepository: CourseRepository


    @Autowired
    private lateinit var courseClassRepository: CourseClassRepository

    /**
     * 获取所有的课程
     * @return List<Course>
     */
    override fun getAllCourse(): List<Course> {
        return courseRepository.findAll()
    }


    /**
     * 获取该教师所教授课程
     * @param teacherId Long
     * @return List<Course>
     */
    override fun getAllCourseByTeacherId(teacherId: Long): List<Course> {

        return courseClassRepository.findByTeacherId(teacherId).distinctBy {
            it.courseId
        }.map { courseAndTeacher ->
            courseRepository.findById(courseAndTeacher.courseId).get()
        }
    }
}