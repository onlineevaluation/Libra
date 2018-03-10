package com.nuc.evaluate.util

import org.junit.Before
import org.junit.Test
import javax.xml.soap.Text

/**
 * @author 杨晓辉 2018-03-09 11:45
 */
class CompilerUtilsTest {

    lateinit var targetSource: String
    lateinit var targetClassName: String
    lateinit var targetMethodName: String
    lateinit var targetPackageName: String


    @Before
    fun initClass() {
        targetSource =
                "package com.test;\n" +
                "\n" +
                "public class Hello {\n" +
                "    \n" +
                "    private int age = 10;\n" +
                "\n" +
                "    public int getAge() {\n" +
                "        return age;\n" +
                "    }\n" +
                "\n" +
                "    public void setAge(int age) {\n" +
                "        this.age = age;\n" +
                "    }\n" +
                "\n" +
                "}"
        targetClassName = "Hello"
        targetMethodName = "setAge"
        targetPackageName = "com.test"

    }


    @Test
    fun testBuildTarget() {
        val message = CompilerUtils.buildTargetSource(targetSource, targetClassName)
        println(message)
    }


    @Test
    fun testRunTarget() {
        CompilerUtils.runTargetClass(targetMethodName, targetPackageName, targetClassName)
    }

    @Test
    fun setAndGetMethod() {

        val f =
            CompilerUtils.testGetAndSetMethod("ages", targetPackageName, targetClassName, 10)
        println(f)
    }

    @Test
    fun getConsoleInfo() {
        val l = CompilerUtils.getConsoleInfo()
        println(l)
    }
}