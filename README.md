# evaluate

![](./icon.png)

用于中北大学软件学院的考试模块系统

服务 | 状态  
:---|:---:  
Travis CI | [![Build Status](https://travis-ci.org/youngxhui/evaluate.svg?branch=master)](https://travis-ci.org/youngxhui/evaluate)
Appveyor CI | ![Build status](https://ci.appveyor.com/api/projects/status/ohh6mcfiumv4sxno?svg=true)
 codecov | [![codecov](https://codecov.io/gh/youngxhui/evaluate/branch/master/graph/badge.svg)](https://codecov.io/gh/youngxhui/evaluate)
 codacy |[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d78fd339c610402f867eb437fca47e82)](https://www.codacy.com/app/youngxhui/evaluate?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=youngxhui/evaluate&amp;utm_campaign=Badge_Grade)
 
[![GitHub count languages](https://img.shields.io/github/languages/count/youngxhui/evaluate.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/youngxhui/evaluate.svg)]()
[![](https://img.shields.io/github/repo-size/badges/shields.svg)]()

![Gradle](https://img.shields.io/badge/gradle-4.4-brightgreen.svg)

[![LICENSE](https://img.shields.io/github/license/mashape/apistatus.svg)](./LICENSE)

# 所用技术

- 语言： Kotlin
- 框架： SpringBoot
- 消息队列: RabbitMQ
- 构建工具：Gradle

# 主要功能

- [x] 登录 (学号)   
- [ ] 权限认证  
- [x] 后台异常邮件发送 (通过 `sentry`)
- [x] 试题获取
- [x] 当前考试试题
- [x] 提交考试
- [x] 试卷评分
- [x] 试题查看 

# 更新日志

- 2018年3月9日  
1.0.3  
更新 `kotlin` 版本到 1.2.30  
启用新的数据库

- 2018年3月13日  
1.1.0  
添加代码运行接口，完成部分代码测试示例  
更改 banner 为中北大学校徽  
其他的bug修复

- 2018年3月17日  
1.1.1  
修改填空题判题规则  

**项目闭源** 

- 2018年3月27日  
1.1.4  
完善简单题评分标准


# License

[MIT](./LICENSE)
