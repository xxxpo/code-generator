package com.xxxlin.main.config.ftlmehtod;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库类型转Java类型
 * Date:    2020年03月19日 11:35 上午
 *
 * @author xiaolin
 * @version 0.1
 */
@Configuration
public class FtlSqlType2JavaType implements TemplateMethodModelEx {

    private static final Map<Integer, Class<?>> reversePrimitiveMap = new HashMap<Integer, Class<?>>() {
        {
            put(Types.BIT, Boolean.class);
            put(Types.TINYINT, Byte.class);
            put(Types.SMALLINT, Short.class);
            put(Types.INTEGER, Integer.class);
            put(Types.BIGINT, Long.class);
            put(Types.FLOAT, Double.class);
            put(Types.DOUBLE, Double.class);
            put(Types.REAL, Float.class);

            put(Types.NUMERIC, BigDecimal.class);
            put(Types.DECIMAL, BigDecimal.class);

            put(Types.CHAR, String.class);
            put(Types.VARCHAR, String.class);
            put(Types.LONGVARCHAR, String.class);

            put(Types.DATE, java.sql.Date.class);
            put(Types.TIME, java.sql.Time.class);
            put(Types.TIMESTAMP, java.sql.Timestamp.class);

            put(Types.BINARY, byte[].class);
            put(Types.VARBINARY, byte[].class);
            put(Types.LONGVARBINARY, byte[].class);
            put(Types.BLOB, byte[].class);
        }
    };

    /**
     * @param list 第一个参数为int
     * @return
     * @throws TemplateModelException
     * @see java.sql.Types
     */
    @Override
    public Object exec(List list) throws TemplateModelException {
        int dataType = Integer.parseInt(list.get(0).toString());
        if (reversePrimitiveMap.containsKey(dataType)) {
            return reversePrimitiveMap.get(dataType).getSimpleName();
        }
        return "";
    }
}
