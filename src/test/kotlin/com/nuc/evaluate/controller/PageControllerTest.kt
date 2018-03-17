package com.nuc.evaluate.controller

import com.nuc.evaluate.util.CompilerUtils
import org.intellij.lang.annotations.Language
import org.junit.Test
import java.util.regex.Pattern

/**
 * @author 杨晓辉 2018-03-10 19:53
 */
class PageControllerTest {


    @Test
    fun testString() {
        var packageName = ""
        var className = ""
        @Language("JAVA")
        val code =
            "public class Hello {\n    private int age;\n\n    public void setAge(int age){\n        this.age = age;\n    }\n\n    public int getAge() {\n        return age;\n    }\n}"
        @Language("RegExp")
        val packageNameRegex = """package [a-zA-Z]+[0-9a-zA-Z_]*(\.[a-zA-Z]+[0-9a-zA-Z_]*)*;""".toRegex()
        @Language("RegExp")
        val classNameRegex = """public class .*?\{""".toRegex()
        var p = Pattern.compile(packageNameRegex.toString())
        var matcher = p.matcher(code)
        val l = matcher.find()
//        val l = code.split("package ".toRegex())
        if (l) {
            packageName = matcher.group(0)
        }

        p = Pattern.compile(classNameRegex.toString())
        matcher = p.matcher(code)

        val result = CompilerUtils.buildTargetSource(code, "Hello")
        println(result)
    }

}


