# SpringBoot Code Generator 代码生成器

## 项目背景

* 基于SpringBoot2+jpa+Freemarker
* 使用jpa，支持mysql等多种数据库
* 自定义模板成生代码
* 我们的目的是使用 `Ctrl+C` / `Ctrl+V`打码

## 使用

1. 配置数据库连接
2. 配置`/resources/template/example/config/genconfig.properties`里的`TableName`，修改成使用的数据表
3. 配置`/resources/template/example/config/genconfig.properties`里的`output.dir`，这是文件输出路径
4. 在`resources/template/example`目录下**添加**或**修改**`.ftl`文件，模板语法可参考[Freemarker手册](http://freemarker.foofun.cn/)
5. 运行项目

## 模板数据模型字段说明

### 顶层字段

| 字段名 | 说明 |
| --- | --- |
| **TableName** | 数据库中的表名<br>例：**blog\_article** |
| **EntityName** | 实体类名称<br>例：**BlogArticle** |
| **EntityInstanceName** | 实体类实例名称<br>例：**blogArticle** |
| **Date** | 当前日期<br>格式：**yyyy-MM-dd** |
| **Time** | 当前时间<br>格式：**HH:mm:ss** |
| **Columns** | 表字段集合，里面包含多个属性，请参考下表 |

### Columns

| **字段名** | 说明 |
| --- | --- |
| **column\_name** | 数据库字段名<br>例：**create\_time** |
| **ColumnName** | 大驼蜂名合的字段名<br>例：**CreateTime** |
| **columnName** | 小驼蜂名合的字段名<br>例：**createTime** |
| **TABLE\_NAME** | 表名 |
| **COLUMN\_NAME** | 例名 |
| **DATA\_TYPE** | 列的数据类型<br>`@see java.sql.Types` |
| **DATA\_TYPE** | 列的数据类型名称 |
| **REMARKS** | 列的备注 |
| ... | 更多请参考 [java.sql.DatabaseMetaData.getColumns()](https://docs.oracle.com/javase/8/docs/api/java/sql/DatabaseMetaData.html#getColumns-java.lang.String-java.lang.String-java.lang.String-java.lang.String-) |

## 如何添加自定义数据模型

在`/resources/template/example/config/genconfig.properties`文件里添加，例: Author=XiaoLin

## 如何动态添加数据模型
<br>
在Application.java里修改

```
@Configurable
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ANSIUtils.println("启动完成", ANSIUtils.GREEN);
        System.exit(0);// 退出
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    @Bean
    public CodeGeneratorService fun(CodeGeneratorService service) throws Exception {
        service.setDataModelLoader(new CodeGeneratorService.DataModelLoader() {
            /**
             * @param properties 配置
             */
            @Override
            public void load(Properties properties){
                /*这里写入自已的数据模型*/
            }
        });

        // 启动代码生成器
        service.run();
        return service;
    }
}
```
<br>
## Donate

如果你觉得这个项目帮助到了你，你可以帮作者买一杯果汁表示鼓励 :

![Donate](https://github.com/xxxpo/code-generator/blob/master/donate-alipay.jpg)

## 开源协议

[Apache License 2.0](https://github.com/xxxpo/code-generator/blob/master/LICENSE) Copyright © 2020-present PanJiaChen
