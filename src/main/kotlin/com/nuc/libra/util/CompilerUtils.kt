package com.nuc.libra.util

import com.nuc.libra.vo.CompilerMessage
import wu.seal.jvm.kotlinreflecttools.changePropertyValue
import wu.seal.jvm.kotlinreflecttools.getPropertyValue
import java.io.Console
import java.io.File
import java.net.URI
import java.net.URLClassLoader
import java.util.*
import javax.tools.DiagnosticCollector
import javax.tools.JavaFileObject
import javax.tools.SimpleJavaFileObject
import javax.tools.ToolProvider

/**
 * @author 杨晓辉 2018-03-09 11:34
 */
object CompilerUtils {

    fun buildTargetSource(targetSource: String, targetClassName: String): CompilerMessage {
        // 1.获得JavaCompiler
        val compiler = ToolProvider.getSystemJavaCompiler()

        // 2.创建一个DiagnosticCollector对象，用于获取编译输出信息
        val diagnosticCollector = DiagnosticCollector<JavaFileObject>()

        // 3.获得JavaFileManager对象，用于管理需要编译的文件
        val fileManager = compiler.getStandardFileManager(diagnosticCollector, null, null)

        val fileObject = MyJavaFileObject(targetClassName, targetSource)

        // 5.组成一个JavaFileObject的遍历器
        val fileObjects = listOf(fileObject)

        // 6.编译后文件输出地址
        val options = Arrays.asList("-d", ".")

        // 7.获得编译任务
        val task = compiler.getTask(null, fileManager, diagnosticCollector, options, null, fileObjects)

        val compilerMessage = CompilerMessage()
        // 8.编译
        val result = task.call()
        compilerMessage.message = "编译成功"
        if (!result) {
            // 编译失败，打印错误信息
            for (diagnostic in diagnosticCollector.diagnostics) {
                compilerMessage.code = diagnostic.code
                compilerMessage.kind = diagnostic.kind.toString()
                compilerMessage.startPosition = diagnostic.startPosition
                compilerMessage.endPosition = diagnostic.endPosition
                compilerMessage.position = diagnostic.position
                compilerMessage.source = diagnostic.source
                compilerMessage.message = diagnostic.getMessage(null)
                compilerMessage.columnNumber = diagnostic.columnNumber
                compilerMessage.lineNumber = diagnostic.lineNumber
            }
        }

        // 9.关闭JavaFileManager
        fileManager.close()
        return compilerMessage
    }


    fun runTargetClass(targetMethodName: String, targetPackageName: String, targetClassName: String) {

        // 10.创建ClassLoader，并设置目录为编译时的输出目录
        val file = File(".")
        val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))

        // 11.构建测试类的Class对象
        val testClass = classLoader.loadClass("$targetPackageName.$targetClassName")

        // 12.获得测试类的方法
        val testMethod = testClass.getDeclaredMethod(targetMethodName, Int::class.java)

        // 13.创建一个测试类的实例
        val testObj = testClass.newInstance()

        // 14.运行测试函数
        testMethod.invoke(testObj, 10)

        val getMethod = testClass.getDeclaredMethod("getAge")
        val l = getMethod.invoke(testObj)
        println(l)

        classLoader.close()
    }


    /**
     * set get 方法测试
     */
    fun testGetAndSetMethod(
        propertyName: String,
        targetPackageName: String,
        targetClassName: String,
        testData: Any
    ): Boolean {

        // 创建ClassLoader，并设置目录为编译时的输出目录
        val file = File(".")
        val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()))

        // 构建测试类的Class对象
        val testClass = classLoader.loadClass("$targetPackageName.$targetClassName")
        val testObj = testClass.newInstance()

        // set 方法设置
        testObj.changePropertyValue(propertyName, testData)

        // get 方法设置
        val result = testObj.getPropertyValue(propertyName)
        var testFlag = false

        if (result == testData) {
            testFlag = true
        }
        classLoader.close()
        return testFlag
    }

    /**
     * 获取控制台输出
     */
    fun getConsoleInfo(): String? {
        val text = "test"
        println(text)

        val console: Console? = System.console() ?: throw IllegalStateException("错误")

        return console?.readLine("test-")
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