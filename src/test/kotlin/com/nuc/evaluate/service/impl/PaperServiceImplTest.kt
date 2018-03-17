package com.nuc.evaluate.service.impl

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
        val pageList2 = pageServiceImpl.getOnePage(9, 1)
        Assert.assertNotNull(pageList2)
    }


    @Test
    fun testList() {
        val list1 = ArrayList<String>()
        list1.add("111")
        list1.add("222")
        list1.add("333")

        val list2 = ArrayList<String>()
        list2.add("111")

        println(list1.removeAll(list2))


        list1.map {
            println("it :: $it")
        }
    }

}