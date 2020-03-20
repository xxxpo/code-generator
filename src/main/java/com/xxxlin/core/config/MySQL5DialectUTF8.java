package com.xxxlin.core.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 无功能说明
 * Date:    2018年10月01日 12:43 PM
 *
 * @author xiaolin
 * @version 0.1
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}
