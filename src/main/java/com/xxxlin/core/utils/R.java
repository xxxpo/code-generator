package com.xxxlin.core.utils;

import com.xxxlin.core.config.ResultStatus;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class R extends HashMap<String, Object>{

	private static final String KEY_CODE = "code";
	private static final String KEY_MSG = "msg";
	private static final String KEY_ROWS = "rows";
	private static final String KEY_ROW = "row";
	private static final String KEY_TOTAL = "total";

	public static R create(){
		return new R();
	}
	public static R ok(){
		return new R(ResultStatus.成功);
	}
	public static R error(ResultStatus r){
		return new R(r);
	}
	public static R error(Exception e){
		String msg = e.getMessage();
		if(TextUtils.isEmpty(msg)){
			return R.error(ResultStatus.未知错误);
		}
		return new R(-1, msg);
	}
	public static R error(String msg){
		return new R(-1, msg);
	}

	public static R error(BindingResult bindingResult){
		return new R(-1, bindingResult.getFieldError().getDefaultMessage());
	}

	public R(){}
	public R(ResultStatus r){
		super.put(KEY_CODE, r.getCode());
		super.put(KEY_MSG, r.getMsg());
	}

	public R(int code, String msg){
		super.put(KEY_CODE, code);
		super.put(KEY_MSG, msg);
	}

	public R put(String key, Object value){
		super.put(key, value);
		return this;
	}

	public R put(ResultStatus r){
		super.put(KEY_CODE, r.getCode());
		super.put(KEY_MSG, r.getMsg());
		return this;
	}

	public <T> R put(Page<T> page){
		List<T> rows = new ArrayList<T>();
		for(T item: page){
			rows.add(item);
		}
		super.put(KEY_TOTAL, page.getTotalElements());
		super.put(KEY_ROWS, rows);
		return this;
	}

	public <T> R putRows(Collection<T> list){
		super.put(KEY_ROWS, list);
		super.put(KEY_TOTAL, list==null ? 0 : list.size());
		return this;
	}

	public R putRow(Object obj){
		super.put(KEY_ROW, obj);
		return this;
	}

}
