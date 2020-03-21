<form action="/${TableName}/save">
    <#list Columns as row>
        <div class="form-group">
            <label for="${row.columnName}Label">${row.REMARKS}</label>
            <input type="input" class="form-control" id="${row.columnName}" name="${row.columnName}" placeholder="${row.REMARKS}">
        </div>
    </#list>
    <button type="submit" class="btn btn-primary">保存</button>
</form>
