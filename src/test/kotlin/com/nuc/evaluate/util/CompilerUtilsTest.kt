package com.nuc.evaluate.util

import org.intellij.lang.annotations.Language
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
    fun getClassName() {
        val className = targetSource.substringAfter("public class").substringBefore("{").trim()
        println("className is $className")
    }

    @Test
    fun runCode() {
        @Language("JAVA")
        val test =
            "package com.test;\npublic class Hello { \n    private int age; \n\n    public void setAge(int var1) {\n        this.age = var1;\n    }\n\n    public int getAge() {\n        return this.age;\n    }\n}"
        val l = CompilerUtils.buildTargetSource(test, "Hello")
        println(l)
    }

}