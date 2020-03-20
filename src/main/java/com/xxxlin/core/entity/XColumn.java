package com.xxxlin.core.entity;

/**
 * 无功能说明
 * Date:    2020年03月19日 10:04 上午
 *
 * @author xiaolin
 * @version 0.1
 */
public class XColumn {

    // 1. table catalog (may be null)
    public String TABLE_CAT;
    // 2. table schema (may be null)
    public String TABLE_SCHEM;
    // 3. table name (表名称)
    public String TABLE_NAME;
    // 4. column name(列名)
    public String COLUMN_NAME;
    /**
     * 5. SQL type (列的数据类型)
     * @see java.sql.Types
     */
    public int DATA_TYPE;
    // 6. Data source dependent type name, for a UDT the type name is fully qualified
    public String TYPE_NAME;
    // 7. column size.
    public int COLUMN_SIZE;
    // 8. is not used.
    public int BUFFER_LENGTH;
    // 9. the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
    public int DECIMAL_DIGITS;
    // 10. Radix (typically either 10 or 2)
    public int NUM_PREC_RADIX;
    // 11. is NULL allowed. 1为可空，0为不可空
    public int NULLABLE;
    // 12. comment describing column (may be null)
    public String REMARKS;
    // 13. default value for the column, (may be null)
    public String COLUMN_DEF;
    // 14. unused
    public int SQL_DATA_TYPE;
    // 15. unused
    public int SQL_DATETIME_SUB;
    // 16. for char types the maximum number of bytes in the column
    public int CHAR_OCTET_LENGTH;
    // 17. index of column in table (starting at 1)
    public int ORDINAL_POSITION;
    // 18. ISO rules are used to determine the nullability for a column.
    public String IS_NULLABLE;
    // 19. catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
    public String SCOPE_CATLOG;
    // 20. schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
    public String SCOPE_SCHEMA;
    // 21. table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF)
    public String SCOPE_TABLE;
    // 22. source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types
    public String SOURCE_DATA_TYPE;
    // 23. Indicates whether this column is auto incremented
    public String IS_AUTOINCREMENT;

    public String getTABLE_CAT() {
        return TABLE_CAT;
    }

    public void setTABLE_CAT(String TABLE_CAT) {
        this.TABLE_CAT = TABLE_CAT;
    }

    public String getTABLE_SCHEM() {
        return TABLE_SCHEM;
    }

    public void setTABLE_SCHEM(String TABLE_SCHEM) {
        this.TABLE_SCHEM = TABLE_SCHEM;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public int getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(int DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public int getCOLUMN_SIZE() {
        return COLUMN_SIZE;
    }

    public void setCOLUMN_SIZE(int COLUMN_SIZE) {
        this.COLUMN_SIZE = COLUMN_SIZE;
    }

    public int getBUFFER_LENGTH() {
        return BUFFER_LENGTH;
    }

    public void setBUFFER_LENGTH(int BUFFER_LENGTH) {
        this.BUFFER_LENGTH = BUFFER_LENGTH;
    }

    public int getDECIMAL_DIGITS() {
        return DECIMAL_DIGITS;
    }

    public void setDECIMAL_DIGITS(int DECIMAL_DIGITS) {
        this.DECIMAL_DIGITS = DECIMAL_DIGITS;
    }

    public int getNUM_PREC_RADIX() {
        return NUM_PREC_RADIX;
    }

    public void setNUM_PREC_RADIX(int NUM_PREC_RADIX) {
        this.NUM_PREC_RADIX = NUM_PREC_RADIX;
    }

    public int getNULLABLE() {
        return NULLABLE;
    }

    public void setNULLABLE(int NULLABLE) {
        this.NULLABLE = NULLABLE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getCOLUMN_DEF() {
        return COLUMN_DEF;
    }

    public void setCOLUMN_DEF(String COLUMN_DEF) {
        this.COLUMN_DEF = COLUMN_DEF;
    }

    public int getSQL_DATA_TYPE() {
        return SQL_DATA_TYPE;
    }

    public void setSQL_DATA_TYPE(int SQL_DATA_TYPE) {
        this.SQL_DATA_TYPE = SQL_DATA_TYPE;
    }

    public int getSQL_DATETIME_SUB() {
        return SQL_DATETIME_SUB;
    }

    public void setSQL_DATETIME_SUB(int SQL_DATETIME_SUB) {
        this.SQL_DATETIME_SUB = SQL_DATETIME_SUB;
    }

    public int getCHAR_OCTET_LENGTH() {
        return CHAR_OCTET_LENGTH;
    }

    public void setCHAR_OCTET_LENGTH(int CHAR_OCTET_LENGTH) {
        this.CHAR_OCTET_LENGTH = CHAR_OCTET_LENGTH;
    }

    public int getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }

    public void setORDINAL_POSITION(int ORDINAL_POSITION) {
        this.ORDINAL_POSITION = ORDINAL_POSITION;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getSCOPE_CATLOG() {
        return SCOPE_CATLOG;
    }

    public void setSCOPE_CATLOG(String SCOPE_CATLOG) {
        this.SCOPE_CATLOG = SCOPE_CATLOG;
    }

    public String getSCOPE_SCHEMA() {
        return SCOPE_SCHEMA;
    }

    public void setSCOPE_SCHEMA(String SCOPE_SCHEMA) {
        this.SCOPE_SCHEMA = SCOPE_SCHEMA;
    }

    public String getSCOPE_TABLE() {
        return SCOPE_TABLE;
    }

    public void setSCOPE_TABLE(String SCOPE_TABLE) {
        this.SCOPE_TABLE = SCOPE_TABLE;
    }

    public String getSOURCE_DATA_TYPE() {
        return SOURCE_DATA_TYPE;
    }

    public void setSOURCE_DATA_TYPE(String SOURCE_DATA_TYPE) {
        this.SOURCE_DATA_TYPE = SOURCE_DATA_TYPE;
    }

    public String getIS_AUTOINCREMENT() {
        return IS_AUTOINCREMENT;
    }

    public void setIS_AUTOINCREMENT(String IS_AUTOINCREMENT) {
        this.IS_AUTOINCREMENT = IS_AUTOINCREMENT;
    }

    @Override
    public String toString() {
        return "XColumn{" +
                "TABLE_CAT='" + TABLE_CAT + '\'' +
                ", TABLE_SCHEM='" + TABLE_SCHEM + '\'' +
                ", TABLE_NAME='" + TABLE_NAME + '\'' +
                ", COLUMN_NAME='" + COLUMN_NAME + '\'' +
                ", DATA_TYPE=" + DATA_TYPE +
                ", TYPE_NAME='" + TYPE_NAME + '\'' +
                ", COLUMN_SIZE=" + COLUMN_SIZE +
                ", BUFFER_LENGTH=" + BUFFER_LENGTH +
                ", DECIMAL_DIGITS=" + DECIMAL_DIGITS +
                ", NUM_PREC_RADIX=" + NUM_PREC_RADIX +
                ", NULLABLE=" + NULLABLE +
                ", REMARKS='" + REMARKS + '\'' +
                ", COLUMN_DEF='" + COLUMN_DEF + '\'' +
                ", SQL_DATA_TYPE=" + SQL_DATA_TYPE +
                ", SQL_DATETIME_SUB=" + SQL_DATETIME_SUB +
                ", CHAR_OCTET_LENGTH=" + CHAR_OCTET_LENGTH +
                ", ORDINAL_POSITION=" + ORDINAL_POSITION +
                ", IS_NULLABLE='" + IS_NULLABLE + '\'' +
                ", SCOPE_CATLOG='" + SCOPE_CATLOG + '\'' +
                ", SCOPE_SCHEMA='" + SCOPE_SCHEMA + '\'' +
                ", SCOPE_TABLE='" + SCOPE_TABLE + '\'' +
                ", SOURCE_DATA_TYPE='" + SOURCE_DATA_TYPE + '\'' +
                ", IS_AUTOINCREMENT='" + IS_AUTOINCREMENT + '\'' +
                '}';
    }
}
