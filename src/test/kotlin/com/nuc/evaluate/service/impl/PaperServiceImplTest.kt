package com.nuc.evaluate.service.impl

import com.nuc.evaluate.repository.ClassAndPagesRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2018/2/18 15:24
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class PaperServiceImplTest {


    @Autowired
    private lateinit var pageServiceImpl: PaperServiceImpl

    @Test
    fun listClassPageTest() {
        val page = pageServiceImpl.listClassPage(1L)
        Assert.assertNotNull(page)
    }

    @Test
    fun getOnePageTest() {
//        val pageList1 = pageServiceImpl.getOnePage(1, 1)
//        Assert.assertNull(pageList1)
        val pageList2 = pageServiceImpl.getOnePage(1,9)
        Assert.assertNotNull(pageList2)
    }
}