package com.xxxlin.main.config;

import com.xxxlin.core.repository.CommonRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 默认使用的数据库配置
 * <p>
 * date      2018/08/19 14:38
 *
 * @author XiaoLin
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryPrimary",
        transactionManagerRef = "transactionManagerPrimary",
        basePackages = {//设置Repository所在位置
                "com.xxxlin.main.api.repository",
        },
        //includeFilters = {
        //        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)
        //},
        excludeFilters = {// 不包含的过滤器
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
        }, repositoryFactoryBeanClass = CommonRepositoryFactoryBean.class//指定自己的工厂类
)
public class DataSourcesPrimaryConfig {

    //spring隐式命名策略--对属性名做下划线连接处理，并将大写改为小写
    private static final String DEFAULT_IMPLICIT_STRATEGY = "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy";

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired(required = false)
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "entityManagerPrimary")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryPrimary(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryPrimary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(primaryDataSource)
                .properties(getVendorProperties())
                .packages(//设置实体类所在位置
                        "com.xxxlin.main.api.entity"
                )
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManagerPrimary(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryPrimary(builder).getObject());
    }

    private Map<String, Object> getVendorProperties() {
        /*
         * 驼蜂转下划线规则配置，多数据源在配置文件里设置无效
         */
        hibernateProperties.getNaming().setPhysicalStrategy(DEFAULT_IMPLICIT_STRATEGY);
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

}
