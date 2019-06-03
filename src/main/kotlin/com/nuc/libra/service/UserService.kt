package com.nuc.libra.service

import com.nuc.libra.exception.ResultException
import com.nuc.libra.po.User
import com.nuc.libra.vo.StudentInfo
import com.nuc.libra.vo.UpdateStudentParam
import com.nuc.libra.vo.UserProfileInfo


/**
 * @author 杨晓辉 2018/2/1 15:48
 */
interface UserService {

    /**
     * 获取所有的用户
     */
    fun findUser(): List<User>


    /**
     * 通过用户名进行用户查找
     * @param username 学号
     * @param password 密码
     * @return HashMap 返回用户信息和token
     * @throws ResultException 当用户名称和密码不一致
     */
    @Throws(ResultException::class)
    fun login(username: String, password: String): String


    /**
     * 通过用户 id 获取用户信息
     */
    fun profile(id: Long): UserProfileInfo

    /**
     * 通过 student id 获取学生信息
     * @param studentId Long 学生id
     */
    fun studentProfile(studentId: Long): StudentInfo

    /**
     * 通过教师 id 获取学生
     * @param teacherId Long
     */
    fun teacherProfile(teacherId: Long)


    fun updateStudentProfile(updateStudentParam: UpdateStudentParam)
}