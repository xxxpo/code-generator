<!--
  代码生成器生成

  Copyright (c) 2018, xxxlin.com All Rights Reserved.
  Date: ${Date} ${Time}
-->
<template>
  <el-form ref="editForm"
           :rules="editRules"
           :model="editForm"
           label-position="right"
           label-width="80px">
    <#list Columns as row>
      <el-form-item prop="${row.columnName}" label="${row.REMARKS}">
        <el-input type="text" v-model="editForm.${row.columnName}"></el-input>
      </el-form-item>
    </#list>
  </el-form>
</template>

<script>

  export default {
    data() {
      return {
        editForm: {
          <#list Columns as row>
          ${row.columnName}: '',
          </#list>
        },
        // 表单验证规则
        editRules: {
          <#list Columns as row>
            ${row.columnName}: [
              { required: true, message: '必填项', trigger: 'blur' }
            ],
          </#list>
        }
      }
    },
    methods: {},
    filters: {},
    mounted() {},
    beforeDestroy() {}
  }
</script>

<style scoped>

</style>
