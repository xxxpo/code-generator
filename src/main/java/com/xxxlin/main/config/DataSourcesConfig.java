package com.xxxlin.main.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 * <p>
 * date      2018/08/19 14:36
 *
 * @author XiaoLin
 * @version 1.0
 */
@Configuration
public class DataSourcesConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        /*
         * 添加连接配置，不然获取不到Table REMARKS
         */
        dataSource.addDataSourceProperty("remarks", "true");
        dataSource.addDataSourceProperty("useInformationSchema", "true");

        return dataSource;
    }

}
