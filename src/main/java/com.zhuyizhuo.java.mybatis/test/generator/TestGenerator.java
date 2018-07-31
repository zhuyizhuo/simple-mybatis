package com.zhuyizhuo.java.mybatis.test.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * class: TestGenerator <br>
 * description: mybatis生成工具使用 <br>
 * time: 2018/7/30 16:29
 *  1.引用generator的包及对应数据库驱动包
 *  2.配置文件generatorConfig.xml
 *  3.启动代码如本类所示
 * @author yizhuo <br>
 * @version 1.0
 */
public class TestGenerator {
    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = false;
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            if (warnings.size() > 0) {
                System.out.println("-----错误信息start-----");
                for (int i = 0; i < warnings.size(); i++) {
                    String s = warnings.get(i);
                    System.out.println(s);
                }
                System.out.println("-----错误信息end-----");
            }
            System.out.println("执行完毕..");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
