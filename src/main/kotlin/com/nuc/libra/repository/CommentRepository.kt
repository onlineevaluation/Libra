package com.nuc.libra.repository

import com.nuc.libra.po.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author 杨晓辉 2019/5/7 8:22
 */
@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    /**
     * 更加视频id统计有多少评论
     * @param videoId Long
     * @return Long
     */
    fun countByVideoId(videoId: Long): Long
}