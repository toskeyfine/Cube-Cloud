package com.toskey.cube.common.log.enums;

/**
 * 日志类型枚举
 *
 * @author toskey
 * @version 1.0.0
 */
public enum LogResultType {

	/**
	 * 正常日志类型
	 */
	NORMAL("1", "正常"),

	/**
	 * 错误日志类型
	 */
	ERROR("0", "错误");

	/**
	 * 类型
	 */
	private final String value;

	/**
	 * 描述
	 */
	private final String desc;

	LogResultType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}
}