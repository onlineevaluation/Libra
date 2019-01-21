[![Build Status](https://travis-ci.org/youngxhui/Libra.svg?branch=master)](https://travis-ci.org/youngxhui/Libra)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fyoungxhui%2Fevaluate.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fyoungxhui%2Fevaluate?ref=badge_shield)
[![codecov](https://codecov.io/gh/youngxhui/Libra/branch/master/graph/badge.svg)](https://codecov.io/gh/youngxhui/Libra)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ded5babc10eb47d68215d88b29872c59)](https://www.codacy.com/app/youngxhui/Libra?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=youngxhui/Libra&amp;utm_campaign=Badge_Grade)
[![GitHub count languages](https://img.shields.io/github/languages/count/youngxhui/evaluate.svg)]()
[![GitHub top language](https://img.shields.io/github/languages/top/youngxhui/evaluate.svg)]()
[![](https://img.shields.io/github/repo-size/badges/shields.svg)]()
![Gradle](https://img.shields.io/badge/gradle-4.4-brightgreen.svg)


#  Libra

![](./icon.png)

用于中北大学软件学院的考试模块系统



# :rocket: 所用技术

- 语言： Kotlin
- 框架： SpringBoot
- 消息队列: RabbitMQ
- 构建工具：Gradle

# :bulb: 主要功能

- [x] 登录 (学号)   
- [x] 权限认证  
- [x] 后台异常邮件发送 (通过 `sentry`)
- [x] 试题获取
- [x] 当前考试试题
- [x] 提交考试
- [x] 试卷评分
- [x] 试题查看 

# TODO LIST

[RoadMap](https://github.com/youngxhui/evaluate/projects/1)

# :memo: 更新日志

- 2019年1月13日

2.0.0

[:sparkles:] 添加token认证

[:white_check_mark:] 添加部分单元测试

[:lock:] 修复认证问题

[:arrow_up:] 更新 `kotlin` 版本到 1.3.10

[:arrow_up:] 更新 `springboot` 版本到 2.1.1.RELEASE

[:green_heart:] 修复ci构建，完善构建平台

[:heavy_plus_sign:] JWT


- 2018年4月3日

1.2.0

[:bug:] 更新错题返回接口信息，错题展示更加详细

- 2018年3月27日

1.1.4

[:bug:] 完善简单题评分标准

- 2018年3月17日

1.1.1

[:bug:] 修改填空题判题规则

- 2018年3月13日

1.1.0

[:white_check_mark:] 添加代码运行接口，完成部分代码测试示例

[:wrench:] 更改 banner 为中北大学校徽

[:bug:] 其他的bug修复

- 2018年3月9日

1.0.3

 [:arrow_up:] 更新 `kotlin` 版本到 1.2.30

 [:card_file_box:] 启用新的数据库

# :page_facing_up: License

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fyoungxhui%2Fevaluate.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fyoungxhui%2Fevaluate?ref=badge_large)