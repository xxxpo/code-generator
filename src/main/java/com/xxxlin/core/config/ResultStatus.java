package com.xxxlin.core.config;

/**
 *
 * @author  	XiaoLin
 * date:       	2018年1月16日
 */
public enum ResultStatus {

	ok(0, "成功"),
	成功(0, "成功"),

	/**
	 * 常用错误
	 */
	未知错误(1, "未知错误"),
	请求错误(2, "请求错误"),
	参数错误(3, "参数错误"),
	参数为空(4, "参数为空"),
	未登录(5, "未登录"),
	服务器错误(6, "想不到吧, 服务器错误了"),
	客户端版本过低(7, "客户端版本过低"),
	无此权限(8, "无此权限"),
	缺少appKey(9, "缺少appKey"),
	缺少签名(10, "缺少签名"),
	缺少参数(11, "缺少参数"),
	服务器出错(12, "服务器出错"),
	服务不可用(13, "服务不可用"),
	服务器正在重启(14, "服务器正在重启"),
	数据已存在(15, "数据已存在"),
	非法参数异常(16, "非法参数异常");


	private int code;
	private String msg;

	ResultStatus(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
