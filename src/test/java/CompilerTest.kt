/**
 * @author 杨晓辉 2018-03-09 12:30
 */

import java.io.File
import java.lang.reflect.Method
import java.net.URI
import java.net.URL
import java.net.URLClassLoader
import java.util.Arrays
import java.util.Collections

import javax.tools.Diagnostic
import javax.tools.DiagnosticCollector
import javax.tools.JavaCompiler
import javax.tools.JavaCompiler.CompilationTask
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject
import javax.tools.StandardJavaFileManager
import javax.tools.ToolProvider

/**
 * Java动态编译运行测试类
 * @author Keary
 * @since 2015-11-4
 */
object CompilerTest {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // 1.获得JavaCompiler
        val compiler = ToolProvider.getSystemJavaCompiler()

        // 2.创建一个DiagnosticCollector对象，用于获取编译输出信息
        val diagnosticCollector = DiagnosticCollector<JavaFileObject>()

        // 3.获得JavaFileManager对象，用于管理需要编译的文件
        val fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null)

        // 4.生成一个JavaFileObject对象，表示需要编译的源文件
        val source = StringBuilder(
            "package com.keary.demo;"
                    + "public class TestClass { " + "\r\n"
                    + "public void printMethod(String str) {" + "\r\n"
                    + "System.out.println(\"This is a \" + str + \"!\");" + "\r\n" + "}"
                    + "\r\n" + "}"
        )
        val fileObject = MyJavaFileObject("TestClass", source.toString())

        // 5.组成一个JavaFileObject的遍历器
        val fileObjects = listOf(fileObject)

        // 6.编译后文件输出地址
        val options = Arrays.asList("-d", ".")

        // 7.获得编译任务
        val task = compiler.getTask(null, fileManager, diagnosticCollector, options, null, fileObjects)

        // 8.编译
        val result = task.call()

        if (result!!) {
            // 编译成功
            println("编译成功")
        } else {
            // 编译失败，打印错误信息
            for (diagnostic in diagnosticCollector.diagnostics) {
                println(
                    "编译错误。 Code:" + diagnostic.code + "\r\n"
                            + "Kind:" + diagnostic.kind + "\r\n"
                            + "StartPosition:" + diagnostic.startPosition + "\r\n"
                            + "EndPosition:" + diagnostic.endPosition + "\r\n"
                            + "Position:" + diagnostic.position + "\r\n"
                            + "Source:" + diagnostic.source + "\r\n"
                            + "Message:" + diagnostic.getMessage(null) + "\r\n"
                            + "ColumnNumber:" + diagnostic.columnNumber + "\r\n"
                            + "LineNumber:" + diagnostic.lineNumber
                )
            }
        }

        // 9.关闭JavaFileManager
        fileManager.close()

        // 运行

        // 10.创建ClassLoader，并设置目录为编译时的输出目录
        val file = File(".")
        val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))

        // 11.构建测试类的Class对象
        val testClass = classLoader.loadClass("com.keary.demo.TestClass")

        // 12.获得测试类的方法
        val testMethod = testClass.getDeclaredMethod("printMethod", String::class.java)

        // 13.创建一个测试类的实例
        val testObj = testClass.newInstance()

        // 14.运行测试函数
        testMethod.invoke(testObj, "hahaha")

        classLoader.close()
    }

}

/**
 * 自定义的JavaFileObject类
 *
 * @author Keary
 * @since 2015-11-4
 */
internal class MyJavaFileObject @Throws(Exception::class)
constructor(className: String, private val content: String) : SimpleJavaFileObject(
    URI.create(
        "string:///" + className.replace('.', '/')
                + JavaFileObject.Kind.SOURCE.extension
    ), JavaFileObject.Kind.SOURCE
) {

    override fun getCharContent(ignoreEncodingErrors: Boolean): CharSequence {
        return content
    }

}
