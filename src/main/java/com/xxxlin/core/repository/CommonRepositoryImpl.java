package com.xxxlin.core.repository;

import com.xxxlin.core.entity.BaseEntity;
import com.xxxlin.core.entity.XColumn;
import com.xxxlin.core.entity.XTable;
import com.xxxlin.core.utils.UUID19Util;
import org.hibernate.internal.SessionImpl;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * date 2018/08/19 14:36
 */
@SuppressWarnings("SpringJavaConstructorAutowiringInspection")
public class CommonRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CommonRepository<T, ID> {

    private final EntityManager entityManager;

    public CommonRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public CommonRepositoryImpl(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T findOne(ID id) {
        return findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBySql(String sql, Object... values) throws Exception {
        Query query = entityManager.createNativeQuery(sql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i + 1, values[i]);
        }
        return query.executeUpdate();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T create(T o) throws Exception {
        if (o instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) o;
            Object id = entity.getId();
            if (id == null) {
                entity.setId(UUID19Util.uuid());
            }
            entity.setCreateTime(System.currentTimeMillis());
            entity.setUpdateTime(System.currentTimeMillis());
        }
        return save(o);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public T update(T o) throws Exception {
        if (o instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) o;
            entity.setUpdateTime(System.currentTimeMillis());
        }
        return save(o);
    }

    @Override
    public String getSchema() throws SQLException {
        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        return conn.getSchema();
    }

    @Override
    public String getCatalog() throws SQLException {
        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        return conn.getCatalog();
    }

    @Override
    public Set<String> getCatalogs() throws SQLException {
        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getCatalogs();

        Set<String> result = new HashSet<>();

        while (rs.next()) {
            String catalog = rs.getString(1);
            result.add(catalog);
        }
        return result;
    }

    /**
     * 获取表信息
     *
     * @param catalog          null:不过滤
     * @param schemaPattern    null:不过滤
     * @param tableNamePattern 过滤表名，null:不过滤
     * @param types            表的类型, 有 "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL  TEMPORARY", "ALIAS", "SYNONYM".
     *                         null:不过滤
     * @return
     * @throws SQLException
     */
    @Override
    public List<XTable> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        DatabaseMetaData metaData = conn.getMetaData();

        List<XTable> result = new ArrayList<>();

        ResultSet rs = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
        while (rs.next()) {
            XTable o = new XTable();
            o.TABLE_CAT = rs.getString("TABLE_CAT");
            o.TABLE_SCHEM = rs.getString("TABLE_SCHEM");
            o.TABLE_NAME = rs.getString("TABLE_NAME");
            o.TABLE_TYPE = rs.getString("TABLE_TYPE");
            o.REMARKS = rs.getString("REMARKS");
            o.TYPE_CAT = rs.getString("TYPE_CAT");
            o.TYPE_SCHEM = rs.getString("TYPE_SCHEM");
            o.TYPE_NAME = rs.getString("TYPE_NAME");
            o.SELF_REFERENCING_COL_NAME = rs.getString("SELF_REFERENCING_COL_NAME");
            o.REF_GENERATION = rs.getString("REF_GENERATION");
            result.add(o);
        }
        return result;
    }

    @Override
    public List<XColumn> getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        Connection conn = entityManager.unwrap(SessionImpl.class).connection();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);

        List<XColumn> result = new ArrayList<>();

        while (rs.next()) {
            XColumn o = new XColumn();
            o.TABLE_CAT = rs.getString("TABLE_CAT");
            o.TABLE_SCHEM = rs.getString("TABLE_SCHEM");
            o.TABLE_NAME = rs.getString("TABLE_NAME");
            o.COLUMN_NAME = rs.getString("COLUMN_NAME");
            o.DATA_TYPE = rs.getInt("DATA_TYPE");
            o.TYPE_NAME = rs.getString("TYPE_NAME");
            o.COLUMN_SIZE = rs.getInt("COLUMN_SIZE");
            o.BUFFER_LENGTH = rs.getInt("BUFFER_LENGTH");
            o.DECIMAL_DIGITS = rs.getInt("DECIMAL_DIGITS");
            o.NUM_PREC_RADIX = rs.getInt("NUM_PREC_RADIX");
            o.NULLABLE = rs.getInt("NULLABLE");
            o.REMARKS = rs.getString("REMARKS");
            o.COLUMN_DEF = rs.getString("COLUMN_DEF");
            o.SQL_DATA_TYPE = rs.getInt("SQL_DATA_TYPE");
            o.SQL_DATETIME_SUB = rs.getInt("SQL_DATETIME_SUB");
            o.CHAR_OCTET_LENGTH = rs.getInt("CHAR_OCTET_LENGTH");
            o.ORDINAL_POSITION = rs.getInt("ORDINAL_POSITION");
            o.IS_NULLABLE = rs.getString("IS_NULLABLE");
            //o.SCOPE_CATLOG = rs.getString("SCOPE_CATLOG");
            o.SCOPE_SCHEMA = rs.getString("SCOPE_SCHEMA");
            o.SCOPE_TABLE = rs.getString("SCOPE_TABLE");
            o.SOURCE_DATA_TYPE = rs.getString("SOURCE_DATA_TYPE");
            o.IS_AUTOINCREMENT = rs.getString("IS_AUTOINCREMENT");

            result.add(o);
        }
        return result;
    }
}
