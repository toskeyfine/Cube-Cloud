package com.toskey.cube.common.log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 日志类型枚举
 *
 * @author lis
 * @date 2023/6/13 16:52
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