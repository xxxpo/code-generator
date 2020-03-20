package com.xxxlin.main.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

/**
 * Created by qyf on 2016/6/29.
 * Freemaker处理工具
 */
@Component
public class FreemarkerService {

    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Bean
    private FreeMarkerConfigurer initFreeMarkerConfiguration() throws Exception {
        freeMarkerConfigurer = new FreeMarkerConfigurer();
        Configuration configuration = freeMarkerConfigurer.createConfiguration();
        // 加载模板目录
        configuration.setClassForTemplateLoading(FreemarkerService.class, File.separator);
        configuration.setDefaultEncoding("utf-8");
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }

    /**
     * 返回字符串格式的模板文件内容
     *
     * @param tempName
     * @param params
     */
    public String toString(String tempName, Map<String, Object> params) throws IOException, TemplateException {
        Template temp = freeMarkerConfigurer.getConfiguration().getTemplate(tempName);
        StringWriter writer = new StringWriter();
        temp.process(params, writer);
        String ret = writer.toString();
        writer.close();
        return ret;
    }

    /**
     * 解析字符串模板,通用方法
     *
     * @param template 字符串模板
     * @param model;   数据
     * @return 解析后内容
     */
    public String process(String template, Object model) throws IOException, TemplateException {
        if (template == null) {
            return null;
        }

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        StringWriter out = new StringWriter();
        new Template("template", new StringReader(template), configuration).process(model, out);
        return out.toString();
    }

    /**
     * 控制台输出模板文件内容
     *
     * @param tempName
     * @param dataModel
     */
    public void toSystemOut(String tempName, Object dataModel) throws IOException, TemplateException {
        // 通过Template可以将模板文件输出到相应的流
        Template temp = freeMarkerConfigurer.getConfiguration().getTemplate(tempName);
        temp.process(dataModel, new PrintWriter(System.out));
    }

    /**
     * 输出HTML文件到本地磁盘
     *
     * @param tempName
     * @param dataModel
     * @param outPath
     */
    public void toFile(String tempName, Object dataModel, String outPath) {
        OutputStreamWriter out = null;
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            File file = new File(outPath);
            if (file.getParent() != null && !new File(file.getParent()).exists()) {
                new File(file.getParent()).mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            Template temp = freeMarkerConfigurer.getConfiguration().getTemplate(tempName);
            temp.process(dataModel, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
