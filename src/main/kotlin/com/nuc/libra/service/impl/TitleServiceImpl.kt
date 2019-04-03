package com.nuc.libra.service.impl

import com.nuc.libra.po.WrongTitles
import com.nuc.libra.repository.WrongTitlesRepository
import com.nuc.libra.service.TitleService
import com.nuc.libra.vo.WrongTitleParam
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

/**
 * @author 杨晓辉 2019/3/23 16:57
 */
@Service
class TitleServiceImpl :TitleService {

    @Autowired
    private lateinit var wrongTitleRepository:WrongTitlesRepository


    /**
     * 添加错误试题
     */
    override fun addWrongTitle(wrongTitleParam: WrongTitleParam) {
        val wrongTitles = WrongTitles()
        BeanUtils.copyProperties(wrongTitleParam,wrongTitles)
        // 时间获取
        wrongTitles.time = Timestamp(Date().time)

        wrongTitleRepository.saveAndFlush(wrongTitles)
    }
}