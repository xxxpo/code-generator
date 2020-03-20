/**
* 代码生成器生成
*
* Copyright (c) 2018, xxxlin.com All Rights Reserved.
* Date: ${Date} ${Time}
*/

import request from '@/utils/request'
import { stringify } from 'qs'

/**
 * 列表
 */
export function getPage(params) {
  let {limit, offset, sort, search} = params
  return request({
    url: '/${ModuleName}/${TableName}/page',
    method: 'get',
    params: { limit, offset, sort, search }
  })
}

/**
 * 详情
 */
export function g(id) {
  return request({
    url: '/${ModuleName}/${TableName}/'+id,
    method: 'get'
  })
}

/**
 * 新建
 * @param data
 */
export function create(data) {
  let {
<#list Columns as row>
    ${row.columnName},
</#list>
  } = data
  return request({
    url: '/${ModuleName}/${TableName}/create',
    method: 'post',
    data: stringify({
<#list Columns as row>
    ${row.columnName},
</#list>
    })
  })
}

/**
 * 修改
 * @param data
 */
export function update(data) {
  let {
<#list Columns as row>
    ${row.columnName},
</#list>
} = data
  return request({
    url: '/${ModuleName}/${TableName}/update',
    method: 'post',
    data: stringify({
<#list Columns as row>
      ${row.columnName},
</#list>
    })
  })
}

/**
* 删除单条数据
* @param id
*/
export function delById(id) {
  return request({
    url: '/${ModuleName}/${TableName}/'+id,
    method: 'delete'
  })
}

/**
 * 批量删除
 *
 * @param data
 */
export function del(data) {
  const { ids } = data
  return request({
    url: '/${ModuleName}/${TableName}/del',
    method: 'post',
    data: stringify({
      ids
    }, { indices: false })
  })
}
