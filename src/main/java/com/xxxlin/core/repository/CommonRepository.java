package com.xxxlin.core.repository;

import com.xxxlin.core.entity.XColumn;
import com.xxxlin.core.entity.XTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by qyf on 2017/11/28.
 * 南宁一家公司的经目经理
 * <p>
 * date 18/08/19 14:36
 */
@NoRepositoryBean
public interface CommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * 查找单个对象,适配springboot从1.x升级到2.X后findOne(id)接口删除的问题
     */
    T findOne(ID id);

    /**
     * 执行原生sql
     */
    int updateBySql(String sql, Object... values) throws Exception;

    T create(T o) throws Exception;

    T update(T o) throws Exception;

    /**
     * 所有库名
     *
     * @return
     */
    Set<String> getCatalogs() throws SQLException;

    /**
     * 获取当前数据库名称
     *
     * @return
     * @throws SQLException
     */
    String getCurrentDatabaseName() throws SQLException;

    /**
     * 获取表名
     *
     * @param catalog          null:不过滤
     * @param schemaPattern    null:不过滤
     * @param tableNamePattern 过滤表名，null:不过滤
     * @param types            表的类型, 有 "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL  TEMPORARY", "ALIAS", "SYNONYM".
     *                         null:不过滤
     * @return
     */
    List<XTable> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException;

    /**
     * 获取字段信息
     *
     * @param catalog           null:不过滤
     * @param catalog           null:不过滤
     * @param tableNamePattern  null:不过滤
     * @param columnNamePattern null:不过滤
     * @return
     */
    List<XColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException;


}
