package com.xxxlin.main.config;

import com.xxxlin.main.config.ftlmehtod.FtlSqlType2JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 无功能说明
 * Date:    2020年03月19日 11:36 上午
 *
 * @author xiaolin
 * @version 0.1
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private FtlSqlType2JavaType ftlSqlType2JavaType;

    @PostConstruct
    public void setConfigure() throws Exception {
        // jdbc类型转Java类型
        configuration.setSharedVariable("sqlType2JavaType", ftlSqlType2JavaType);
    }


}
