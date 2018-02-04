package com.nuc.evaluate.vo

import com.nuc.evaluate.po.Title
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author 杨晓辉 2018/2/4 10:54
 */
class TitleVOTest {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    lateinit var titleVO: TitleVO
    lateinit var title: Title

    @Before
    fun init() {
        titleVO = TitleVO()
        title = Title()
        title.title ="直通车三种推广方式_____、_____和_____。"

        title.category = "0"
    }


    @Test
    fun testPo2Vo() {
        titleVO.title = title.title
        titleVO.category = title.category
        titleVO.setSection(titleVO.category)
        logger.info("title: ${titleVO.title}")
        logger.info("A: ${titleVO.sectionA}")
        logger.info("B: ${titleVO.sectionB}")
        logger.info("C: ${titleVO.sectionC}")
        logger.info("D: ${titleVO.sectionD}")

    }
}