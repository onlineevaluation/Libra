package com.nuc.evaluate;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author 杨晓辉 2018-03-09 11:17
 */
public class Cmder {

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 获取系统的java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // 指定要编译的java文件
        String fileToCompile = "C:/Users/young/Desktop/Hello.java";
        // 执行编译方法
        int compilationResult = compiler.run(null, null, new FileOutputStream("error.txt"), fileToCompile);
        // 返回0表示编译成功
        if (compilationResult == 0) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }


}
