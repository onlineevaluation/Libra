package com.nuc.evaluate.cache

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * @author 杨晓辉 2018/2/6 16:09
 */
@RunWith(SpringRunner::class)
@SpringBootTest
class TestQM {

    @Autowired
    lateinit var sender: Sender

    @Test
    fun hello() {
        sender.send()

//        val receiver = Receiver()
//        receiver.process("hello")
    }
}