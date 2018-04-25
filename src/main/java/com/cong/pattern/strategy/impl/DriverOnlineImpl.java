/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.strategy.impl;

import com.cong.pattern.strategy.DriverOnline;

/**
 * <p>文件名称: DriverOnlineImpl.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年01月19日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class DriverOnlineImpl implements DriverOnline {

	public void online(Long driverNo) {
		System.out.println("司机开始上线.......");
		try {
			Thread.sleep(1000);

		}catch (Exception e){
			e.printStackTrace();
		}

		System.out.println("司机上线成功......");
	}
}
