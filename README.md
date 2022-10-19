## simple-mybatis
##### 此项目为编程式mybatis项目,包含以下内容

###### 配置部分

* xml文件resultMap映射

* 自动注册bean

* 嵌套查询

  * 1:1嵌套
  * 1:N嵌套
  * 验证N+1问题

* 嵌套结果

  * 1:1嵌套
  * 1:N嵌套

###### mybatis缓存

* 验证一级缓存的存在性及什么情况下命中一级缓存
* 二级缓存不建议使用,本项目并未做验证

###### mybatis 官方代码生成器
- TestGenerator

###### 扩展部分

* 自定义TypeHandler

* 自定义plugin

* 手写简单版的mybatis用来理解框架原理

  * 简单版v1

  * 进阶版v2(待开发)

###### 问题答疑

* mybatis一些问题引出与答疑

###### 未完善部分:

  * 分页插件使用

  * mybatis日志使用

##### 项目环境

* jdk1.8 +
* mysql 5.x +
* maven

##### 下载项目
- git clone https://github.com/zhuyizhuo/simple-mybatis.git

##### 运行 mybatis 单测
- 创建数据库，并执行 src/main/resources/sql 下的 sql 文件
- 修改 mybatis-config.xml 中的数据库配置
- 依次执行单元测试中的方法

##### 运行手写 mybatis
- TODO

```
本项目为本人整理mybatis相关知识点所创建.
欢迎各位补充或issue留言交流.
```

##### 个人联系方式,添加请注明:来自gitHub

##### 微信:

![二维码](assets/1559619424122.jpg)
