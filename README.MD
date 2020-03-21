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

## 编写.ftl模板文件

这是你的数据库表
```sql
create table user
(
    id       varchar(32) not null comment 'ID',
    nick     varchar(64) not null comment '昵称',
    username varchar(32) not null comment '用户名',
    psw      varchar(64) not null comment '用户密码',
    age      int         not null comment '年龄'
) engine=InnoDB default charset=utf8 comment='用户信息';
```

这是你的模板
```ftl
/**
* 代码生成器生成
*
* Copyright (c) 2018, xxxlin.com All Rights Reserved.
* Date: ${Date} ${Time}
*/
package com.xxxlin.main.api.entity.${ModuleName};

import java.io.Serializable;
import java.util.Objects;

/**
 * ${TableComment}
 *
 * Date:    ${Date} ${Time}
 * @author  ${Author}
 * @version ${Version}
 */
public class ${EntityName} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list Columns as row>
    /**
     * ${row.REMARKS}
     */
    private ${sqlType2JavaType(row.DATA_TYPE)} ${row.columnName};

</#list>

<#list Columns as row>
    public ${sqlType2JavaType(row.DATA_TYPE)} get${row.ColumnName}(){
        return this.${row.columnName};
    }

    public void set${row.ColumnName}(${sqlType2JavaType(row.DATA_TYPE)} ${row.columnName}) {
        this.${row.columnName} = ${row.columnName};
    }

</#list>
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ${EntityName} that = (${EntityName}) o;

        return
    <#list Columns as row>
        Objects.equals(${row.columnName}, that.${row.columnName})<#if row_index<Columns?size-1>&&<#else>;</#if>
    </#list>
    }

    @Override
    public int hashCode() {
        return Objects.hash(<#list Columns as row>${row.columnName}<#if row_index<Columns?size-1>, </#if></#list>);
    }
}
```

这是你生成的代码
```java
/**
* 代码生成器生成
*
* Copyright (c) 2018, xxxlin.com All Rights Reserved.
* Date: 2020-03-21 20:48:33
*/
package com.xxxlin.main.api.entity.blog;

import java.io.Serializable;
import java.util.Objects;

/**
 * 用户信息
 *
 * Date:    2020-03-21 20:48:33
 * @author  XiaoLin
 * @version 0.1
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 昵称
     */
    private String nick;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String psw;

    /**
     * 年龄
     */
    private Integer age;


    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick(){
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw(){
        return this.psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;

        return
        Objects.equals(id, that.id)&&
        Objects.equals(nick, that.nick)&&
        Objects.equals(username, that.username)&&
        Objects.equals(psw, that.psw)&&
        Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nick, username, psw, age);
    }
}
```

这是你的模板
```ftl
<form action="/${TableName}/save">
    <#list Columns as row>
        <div class="form-group">
            <label for="${row.columnName}Label">${row.REMARKS}</label>
            <input type="input" class="form-control" id="${row.columnName}" name="${row.columnName}" placeholder="${row.REMARKS}">
        </div>
    </#list>
    <button type="submit" class="btn btn-primary">保存</button>
</form>
```

这是你生成的代码
```html
<form action="/user/save">
    <div class="form-group">
        <label for="idLabel">ID</label>
        <input type="input" class="form-control" id="id" name="id" placeholder="ID">
    </div>
    <div class="form-group">
        <label for="nickLabel">昵称</label>
        <input type="input" class="form-control" id="nick" name="nick" placeholder="昵称">
    </div>
    <div class="form-group">
        <label for="usernameLabel">用户名</label>
        <input type="input" class="form-control" id="username" name="username" placeholder="用户名">
    </div>
    <div class="form-group">
        <label for="pswLabel">用户密码</label>
        <input type="input" class="form-control" id="psw" name="psw" placeholder="用户密码">
    </div>
    <div class="form-group">
        <label for="ageLabel">年龄</label>
        <input type="input" class="form-control" id="age" name="age" placeholder="年龄">
    </div>
    <button type="submit" class="btn btn-primary">保存</button>
</form>
```

这是你的模板
```ftl
/**
 * 代码生成器生成
 *
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 * Date: ${Date} ${Time}
 */
package com.xxxlin.main.api.controller.${ModuleName};

import com.xxxlin.core.controller.BaseController;
import com.xxxlin.core.entity.BaseQueryParams;
import com.xxxlin.core.jpa.page.PageRange;
import com.xxxlin.core.utils.R;
import com.xxxlin.main.api.entity.${ModuleName}.${EntityName};
import com.xxxlin.main.api.repository.${ModuleName}.${EntityName}Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * ${TableComment}
 *
 * Date:     ${Date} ${Time}
 * @author   ${Author}
 * @version  ${Version}
 */
@RestController
@RequestMapping("/home/api/v1/${ModuleName}/${TableName}")
public class ${EntityName}Controller extends BaseController{

	@Autowired
	private ${EntityName}Repository ${EntityInstanceName}Repository;

    /**
    * 所有数据
    */
    @GetMapping("/list")
    public R list(HttpServletRequest request){
        List<Dto${EntityName}> list = ${EntityInstanceName}Repository.list();
        return R.ok().putRows(list);
    }

    /**
     * 列表
     *
     * @param params
     */
    @GetMapping("/page")
    public R dataList(HttpServletRequest request, BaseQueryParams params){
        PageRange pageable = params.getPageRange();
        Page<${EntityName}> page = ${EntityInstanceName}Repository.page(params.getSearch(), pageable);
        return R.ok().put(page);
    }

    /**
    * 新增
    */
    @PostMapping("/create")
    public R add(HttpServletRequest request, @Valid ${EntityName} o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        ${EntityInstanceName}Repository.create(o);
        return R.ok();
    }

    /**
    * 详情
    */
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id){
        ${EntityName} o = ${EntityInstanceName}Repository.findOne(id);
        return R.ok().putRow(o);
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    public R update(HttpServletRequest request, @Valid ${EntityName} o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        ${EntityInstanceName}Repository.update(o);
        return R.ok();
    }

    /**
    * 删除单条数据
    *
    * @param id ID
    */
    @DeleteMapping("/del/{id}")
    public R delete(@PathVariable("id") String id){
        ${EntityInstanceName}Repository.deleteById(id);
        return R.ok();
    }

    /**
     * 批量删除数据
     *
     * @param ids ID集合
     */
    @PostMapping("/del")
    public R deletes(@RequestParam("ids") List<String> ids) {
        List<${EntityName}> list = ${EntityInstanceName}Repository.findAllById(ids);
        ${EntityInstanceName}Repository.deleteAll(list);
        return R.ok();
    }
}
```

这是你生成的代码
```java
/**
 * 代码生成器生成
 *
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 * Date: 2020-03-20 20:48:54
 */
package com.xxxlin.main.api.controller.blog;

import com.xxxlin.core.controller.BaseController;
import com.xxxlin.core.entity.BaseQueryParams;
import com.xxxlin.core.jpa.page.PageRange;
import com.xxxlin.core.utils.R;
import com.xxxlin.main.api.entity.blog.BlogArticle;
import com.xxxlin.main.api.repository.blog.BlogArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * Date:     2020-03-20 20:48:54
 * @author   XiaoLin
 * @version  0.1
 */
@RestController
@RequestMapping("/home/api/v1/blog/blog_article")
public class BlogArticleController extends BaseController{

	@Autowired
	private BlogArticleRepository blogArticleRepository;

    /**
    * 所有数据
    */
    @GetMapping("/list")
    public R list(HttpServletRequest request){
        List<DtoBlogArticle> list = blogArticleRepository.list();
        return R.ok().putRows(list);
    }

    /**
     * 列表
     *
     * @param params
     */
    @GetMapping("/page")
    public R dataList(HttpServletRequest request, BaseQueryParams params){
        PageRange pageable = params.getPageRange();
        Page<BlogArticle> page = blogArticleRepository.page(params.getSearch(), pageable);
        return R.ok().put(page);
    }

    /**
    * 新增
    */
    @PostMapping("/create")
    public R add(HttpServletRequest request, @Valid BlogArticle o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        blogArticleRepository.create(o);
        return R.ok();
    }

    /**
    * 详情
    */
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id){
        BlogArticle o = blogArticleRepository.findOne(id);
        return R.ok().putRow(o);
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    public R update(HttpServletRequest request, @Valid BlogArticle o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        blogArticleRepository.update(o);
        return R.ok();
    }

    /**
    * 删除单条数据
    *
    * @param id ID
    */
    @DeleteMapping("/del/{id}")
    public R delete(@PathVariable("id") String id){
        blogArticleRepository.deleteById(id);
        return R.ok();
    }

    /**
     * 批量删除数据
     *
     * @param ids ID集合
     */
    @PostMapping("/del")
    public R deletes(@RequestParam("ids") List<String> ids) {
        List<BlogArticle> list = blogArticleRepository.findAllById(ids);
        blogArticleRepository.deleteAll(list);
        return R.ok();
    }
}
```


## 模板数据模型字段说明

### 顶层字段

| 字段名 | 说明 |
| --- | --- |
| TableName | 数据库中的表名<br>例：**blog\_article** |
| TableComment | 表注释 |
| EntityName | 实体类名称<br>例：**BlogArticle** |
| EntityInstanceName | 实体类实例名称<br>例：**blogArticle** |
| Date | 当前日期<br>格式：**yyyy-MM-dd** |
| Time | 当前时间<br>格式：**HH:mm:ss** |
| Columns | 表字段集合，里面包含多个属性，请参考下表 |

### Columns

| **字段名** | 说明 |
| --- | --- |
| column\_name | 数据库字段名<br>例：**create\_time** |
| ColumnName | 大驼蜂名合的字段名<br>例：**CreateTime** |
| columnName | 小驼蜂名合的字段名<br>例：**createTime** |
| TABLE\_NAME | 表名 |
| COLUMN\_NAME | 例名 |
| DATA\_TYPE | 列的数据类型<br>`@see java.sql.Types` |
| DATA\_TYPE | 列的数据类型名称 |
| REMARKS | 列的备注 |
| ... | 更多请参考 [java.sql.DatabaseMetaData.getColumns()](https://docs.oracle.com/javase/8/docs/api/java/sql/DatabaseMetaData.html#getColumns-java.lang.String-java.lang.String-java.lang.String-java.lang.String-) |

## 如何添加自定义数据模型

在`/resources/template/example/config/genconfig.properties`文件里添加，例: Author=XiaoLin

## 如何动态添加数据模型

在项目里找到这个文件Application.java

```java
.....

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

......
```

## 其它
如果你有更好的板模，欢迎留言

## Donate

如果你觉得这个项目帮助到了你，你可以帮作者买一杯果汁表示鼓励 :

![Donate](https://github.com/xxxpo/code-generator/blob/master/images/donate-alipay.jpeg)

## 开源协议

[Apache License 2.0](https://github.com/xxxpo/code-generator/blob/master/LICENSE) Copyright © 2020-present xxxlin.com
