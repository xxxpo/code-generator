/**
 * 代码生成器生成
 *
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 * Date: ${Date} ${Time}
 */
package com.xxxlin.main.api.controller.${ModuleName};

import com.xxxlin.core.controller.BaseController;
import com.xxxlin.core.entity.BaseQueryParams;
import com.xxxlin.core.jpa.page.PageRange;
import com.xxxlin.core.utils.R;
import com.xxxlin.main.api.entity.${ModuleName}.${EntityName};
import com.xxxlin.main.api.repository.${ModuleName}.${EntityName}Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * Date:     ${Date} ${Time}
 * @author   ${Author}
 * @version  ${Version}
 */
@RestController
@RequestMapping("/home/api/v1/${ModuleName}/${TableName}")
public class ${EntityName}Controller extends BaseController{

	@Autowired
	private ${EntityName}Repository ${EntityInstanceName}Repository;

    /**
    * 所有数据
    */
    @GetMapping("/list")
    public R list(HttpServletRequest request){
        List<Dto${EntityName}> list = ${EntityInstanceName}Repository.list();
        return R.ok().putRows(list);
    }

    /**
     * 列表
     *
     * @param params
     */
    @GetMapping("/page")
    public R dataList(HttpServletRequest request, BaseQueryParams params){
        PageRange pageable = params.getPageRange();
        Page<${EntityName}> page = ${EntityInstanceName}Repository.page(params.getSearch(), pageable);
        return R.ok().put(page);
    }

    /**
    * 新增
    */
    @PostMapping("/create")
    public R add(HttpServletRequest request, @Valid ${EntityName} o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        ${EntityInstanceName}Repository.create(o);
        return R.ok();
    }

    /**
    * 详情
    */
    @GetMapping("/get/{id}")
    public R get(@PathVariable("id") String id){
        ${EntityName} o = ${EntityInstanceName}Repository.findOne(id);
        return R.ok().putRow(o);
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    public R update(HttpServletRequest request, @Valid ${EntityName} o, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return R.error(bindingResult);
        }
        ${EntityInstanceName}Repository.update(o);
        return R.ok();
    }

    /**
    * 删除单条数据
    *
    * @param id ID
    */
    @DeleteMapping("/del/{id}")
    public R delete(@PathVariable("id") String id){
        ${EntityInstanceName}Repository.deleteById(id);
        return R.ok();
    }

    /**
     * 批量删除数据
     *
     * @param ids ID集合
     */
    @PostMapping("/del")
    public R deletes(@RequestParam("ids") List<String> ids) {
        List<${EntityName}> list = ${EntityInstanceName}Repository.findAllById(ids);
        ${EntityInstanceName}Repository.deleteAll(list);
        return R.ok();
    }

}
