package com.nuc.libra.service.impl;

import com.nuc.libra.vo.Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 杨晓辉 2019/2/28 15:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeServiceImplTest {


    @Autowired
    private CodeServiceImpl codeService = new CodeServiceImpl();

    /**
     * 代码运行测试
     */
    @Test
    public void runCodeTest() {

        Code code = new Code(1L, "#include <stdio.h>\n" +
                "\n" +
                "int main() {\n" +
                "    int a;\n" +
                "    int b;\n" +
                "    scanf(\"%d%d\",&a,&b);\n" +
                "    printf(\"%d\",a+b);\n" +
                "    return 0;\n" +
                "}", "cpp");

        codeService.runCode(code, code.getLanguage(), 1L, 1514010101);
    }

}