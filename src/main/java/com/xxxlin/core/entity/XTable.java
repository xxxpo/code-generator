package com.xxxlin.core.entity;

/**
 * 无功能说明
 * Date:    2020年03月19日 9:47 上午
 *
 * @author xiaolin
 * @version 0.1
 */
public class XTable {

    // 1. 表所在的编目(可能为空)
    public String TABLE_CAT;
    // 2. 表所在的模式(可能为空)
    public String TABLE_SCHEM;
    // 3. 表的名称
    public String TABLE_NAME;
    // 4. 表的类型, 有 "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL  TEMPORARY", "ALIAS", "SYNONYM".
    public String TABLE_TYPE;
    // 5. 解释性的备注
    public String REMARKS;
    // 6. 编目类型(may be null)
    public String TYPE_CAT;
    // 7. 模式类型(may be null)
    public String TYPE_SCHEM;
    // 8. 类型名称(may be null)
    public String TYPE_NAME;
    // 9. name of the designated "identifier" column of a typed table (may be null)
    public String SELF_REFERENCING_COL_NAME;
    // 10. specifies how values in SELF_REFERENCING_COL_NAME are created.
    public String REF_GENERATION;

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

    public String getTABLE_TYPE() {
        return TABLE_TYPE;
    }

    public void setTABLE_TYPE(String TABLE_TYPE) {
        this.TABLE_TYPE = TABLE_TYPE;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getTYPE_CAT() {
        return TYPE_CAT;
    }

    public void setTYPE_CAT(String TYPE_CAT) {
        this.TYPE_CAT = TYPE_CAT;
    }

    public String getTYPE_SCHEM() {
        return TYPE_SCHEM;
    }

    public void setTYPE_SCHEM(String TYPE_SCHEM) {
        this.TYPE_SCHEM = TYPE_SCHEM;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public String getSELF_REFERENCING_COL_NAME() {
        return SELF_REFERENCING_COL_NAME;
    }

    public void setSELF_REFERENCING_COL_NAME(String SELF_REFERENCING_COL_NAME) {
        this.SELF_REFERENCING_COL_NAME = SELF_REFERENCING_COL_NAME;
    }

    public String getREF_GENERATION() {
        return REF_GENERATION;
    }

    public void setREF_GENERATION(String REF_GENERATION) {
        this.REF_GENERATION = REF_GENERATION;
    }
}
