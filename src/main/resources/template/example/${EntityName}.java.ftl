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
