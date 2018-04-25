/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.strategy.impl;

import com.cong.pattern.strategy.DriverConfirmOrder;

/**
 * <p>文件名称: DriverConfirmOrderImpl.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年01月19日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class DriverConfirmOrderImpl implements DriverConfirmOrder {

	public void orderConfirm(Long driverNo,Long orderNo) {
		System.out.println("司机开始接单.......");
		try {
			Thread.sleep(1000);

		}catch (Exception e){
			e.printStackTrace();
		}

		System.out.println("司机接单成功......");
	}
}
