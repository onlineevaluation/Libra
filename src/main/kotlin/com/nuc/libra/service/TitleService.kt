package com.nuc.libra.service

import com.nuc.libra.vo.WrongTitleParam
import org.springframework.stereotype.Service

/**
 * @author 杨晓辉 2019/3/23 16:56
 */
@Service
interface TitleService {

    /**
     * 添加错误试题
     */
    fun addWrongTitle(wrongTitleParam: WrongTitleParam)

}