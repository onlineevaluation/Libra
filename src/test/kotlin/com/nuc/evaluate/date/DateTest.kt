package com.nuc.evaluate.date

import com.nuc.evaluate.repository.ClassAndPagesRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.sql.Timestamp
import java.util.*

/**
 * @author 杨晓辉 2018/2/4 15:13
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class DateTest {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var classAndPagesRepository: ClassAndPagesRepository

    @Test
    fun testDate() {
        val nowDate = Timestamp(System.currentTimeMillis())
        logger.info("now is $nowDate")
        var pages = classAndPagesRepository.findByClassId(1)

        val page = pages[0]
        logger.info("start time is ${page.startTime}")
        logger.info("end time is ${page.endTime}")
        if (nowDate.before(page.endTime) && nowDate.after(page.startTime)) {
            logger.info("zao")
        } else {
            logger.info("wan")
        }

    }
}


