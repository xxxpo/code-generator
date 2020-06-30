package com.xxxlin.main.service;

import com.xxxlin.core.entity.XColumn;
import com.xxxlin.core.entity.XTable;
import com.xxxlin.core.utils.BeanUtils;
import com.xxxlin.core.utils.TextUtils;
import com.xxxlin.main.api.repository.ETRepository;
import com.xxxlin.main.util.FileUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 无功能说明
 * Date:    2020年03月19日 1:35 上午
 *
 * @author xiaolin
 * @version 0.1
 */
@Component
public class CodeGeneratorService {

    /**
     * 配置文件路径
     */
    private static final String PROPERTIES = "/config/genconfig.properties";

    @Autowired
    private FreemarkerService freemarkerService;
    @Autowired
    private ETRepository etRepository;

    /**
     * 模板文件夹
     */
    @Value("${template.path}")
    private String TEMPLATE_PATH;

    // 数据模型加载器
    private DataModelLoader dataModelLoader;

    /**
     * 运行代码生成器
     */
    public void run() throws Exception {
        System.out.println("开始生成模板");

        // 加载配置
        Properties properties = getProperties();
        // 表信息
        loadDatabaseTables(properties);

        if (dataModelLoader != null) {
            dataModelLoader.load(properties);
        }

        // 取出模板模块下模板文件
        File[] templateFiles = getTemplateFiles();
        String tableNames = properties.getProperty("TableNames");
        if(tableNames.contains(",")){// 多表
            String[] tableAry = tableNames.split(",");
            for(String tableName: tableAry){
                properties.put("TableName", tableName);
                // 加载一些动态的设置
                loadExtProperties(properties);
                // 加载数据库字段信息
                loadDatabaseFields(tableName, properties);
                // 生成代码
                generateCode(templateFiles, properties);
            }
        } else {
            properties.put("TableName", tableNames);
            // 加载一些动态的设置
            loadExtProperties(properties);
            // 加载数据库字段信息
            loadDatabaseFields(tableNames, properties);
            // 生成代码
            generateCode(templateFiles, properties);
        }

        // test single file
        //freemarkerUtil.toSystemOut(TEMPLATE_WEB_XXXLIN + '/' + "index.vue.ftl", params);
    }

    /**
     * 加载配置
     *
     * @return 配置
     */
    private Properties getProperties() throws Exception {
        return FileUtil.loadPropertiesFile(TEMPLATE_PATH + PROPERTIES);
    }

    /**
     * 加载动态模板变量
     *
     * @param properties 配置
     */
    private void loadExtProperties(Properties properties) {
        // 当前日期
        properties.put("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // 当前时间
        properties.put("Time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        // 当前时间戳
        properties.put("CurrentTimeMillis", System.currentTimeMillis());

        // 取出表名
        String tableName = properties.getProperty("TableName");
        String entityName = TextUtils.toEntityName(tableName);
        String entityInstanceName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);

        properties.put("EntityName", entityName);
        properties.put("EntityInstanceName", entityInstanceName);
    }

    /**
     * 加载数据库字段信息
     *
     * @param tableName 表名
     * @param properties 配置
     */
    private void loadDatabaseFields(String tableName, Properties properties) throws Exception {
        String catalog = etRepository.getCatalog();
        String schema = etRepository.getSchema();

        /*表详情*/
        List<XTable> tables = etRepository.getTables(catalog, schema, tableName, null);
        properties.put("TableComment", tables.get(0).REMARKS);

        /*字段详情*/
        List<XColumn> columns = etRepository.getColumns(catalog, schema, tableName, null);
        List<Map<String, Object>> newColumnMaps = new ArrayList<>();
        for (XColumn column : columns) {
            // 列名
            String column_name = column.COLUMN_NAME;
            // 小驼峰
            String camelCaseName = TextUtils.camelCaseName(column_name);

            Map<String, Object> newMap = BeanUtils.javaBeanToMap(column);
            // 字段名
            newMap.put("column_name", column_name);
            // 小骆峰名
            newMap.put("columnName", camelCaseName);
            // 大骆峰名
            newMap.put("ColumnName", TextUtils.toFirstUp(camelCaseName));
            newColumnMaps.add(newMap);
        }
        properties.put("Columns", newColumnMaps);
    }

    private void loadDatabaseTables(Properties properties) throws Exception {
        String catalog = etRepository.getCatalog();
        String schema = etRepository.getSchema();
        List<XTable> tables = etRepository.getTables(catalog, schema, null, null);
        properties.put("Tables", tables);
    }

    /**
     * 取出模板文件
     *
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private File[] getTemplateFiles() throws UnsupportedEncodingException, FileNotFoundException {
        URL url = getClass().getClassLoader().getResource(TEMPLATE_PATH);
        if (url == null) {
            throw new FileNotFoundException("路径不存在:" + TEMPLATE_PATH);
        }
        String dirPath = url.getFile();
        // 路径解码
        dirPath = URLDecoder.decode(dirPath, "utf-8");
        File dir = new File(dirPath);
        return dir.listFiles();
    }

    /**
     * 生成代码
     *
     * @param templateFiles
     * @param properties
     * @throws IOException
     * @throws TemplateException
     */
    private void generateCode(File[] templateFiles, Properties properties) throws IOException, TemplateException {
        String tableName = properties.getProperty("TableName");
        for (File f : templateFiles) {
            System.out.println();
            System.out.println(f.getName());
            System.out.println();

            if (f.getName().endsWith(".ftl") && f.isFile()) {
                String outFileName = freemarkerService.process(f.getName(), properties);
                outFileName = outFileName.substring(0, outFileName.length() - 4);
                System.out.println(outFileName);
                // 打印在控制台
                freemarkerService.toSystemOut(TEMPLATE_PATH +'/'+ f.getName(), properties);
                String outDir = properties.get("output.dir") + "/" + tableName;
                freemarkerService.toFile(TEMPLATE_PATH + '/' + f.getName(), properties, outDir + "/" + outFileName);
            }
        }
    }

    public void setDataModelLoader(DataModelLoader loader) {
        this.dataModelLoader = loader;
    }

    /**
     * 数据模型加载器
     */
    public interface DataModelLoader {
        void load(Properties properties) throws InvocationTargetException, SQLException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException;
    }

}
