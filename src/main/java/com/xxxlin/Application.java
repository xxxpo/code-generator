package com.xxxlin;

import com.xxxlin.core.utils.ANSIUtils;
import com.xxxlin.main.service.CodeGeneratorService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.Properties;

/**
 * 无功能说明
 * <p>
 * date      18/07/28 22:15
 *
 * @author XiaoLin
 * @version 1.0
 */
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
            public void load(Properties properties) throws SQLException {
                /*这里写入自已的数据模型*/
            }
        });

        // 启动代码生成器
        service.run();
        return service;
    }

}
