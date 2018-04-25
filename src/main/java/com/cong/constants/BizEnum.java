package com.cong.constants;

/**
 * <p>文件名称: BizEnum.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月23日</p>
 *
 * @since 1.0
 * @author by fu.cong@geely.com
 */
public enum BizEnum {

	ZHUANCHE(1, "专车"),
	HERMES(2, "绿色公务"),
	TRAVEL(3, "旅游");

	Integer value;

	String name;

	BizEnum(Integer value, String name) {
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public static boolean contains(Integer value) {
		BizEnum[] enums = BizEnum.values();
		for (BizEnum bizEnum : enums) {
			if (bizEnum.value.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public static BizEnum getEnumByValue(Integer value) {
		for (BizEnum bizEnum : BizEnum.values()) {
			if (bizEnum.getValue().equals(value)) {
				return bizEnum;
			}
		}
		return null;
	}
}
