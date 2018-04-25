/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.strategy;

import com.cong.pattern.strategy.impl.DriverConfirmOrderImpl;
import com.cong.pattern.strategy.impl.DriverOfflineImpl;
import com.cong.pattern.strategy.impl.DriverOnlineImpl;
import com.cong.pattern.strategy.impl.DriverStartBillImpl;
import com.cong.pattern.strategy.impl.DriverStopBillImpl;
import com.cong.pattern.strategy.impl.ZhuacheDriverActor;

/**
 * <p>文件名称: DriverActorTest.java</p>
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
public class DriverActorTest {

	public static void main(String[] args) {
		Long driverNo= 1003019L;
		Long orderNo = 1224242324536543L;
		ZhuacheDriverActor zhuacheDriverActor = new ZhuacheDriverActor("Zhuanche");

		zhuacheDriverActor.setDriverOnline(new DriverOnlineImpl())
				.setDriverOffline(new DriverOfflineImpl())
				.setDriverConfirmOrder(new DriverConfirmOrderImpl())
				.setDriverStartBill(new DriverStartBillImpl())
				.setDriverStopBill(new DriverStopBillImpl());

		zhuacheDriverActor.online(driverNo);
		zhuacheDriverActor.confirmOrder(driverNo,orderNo);
		zhuacheDriverActor.startBill(driverNo,orderNo);
		zhuacheDriverActor.stopBill(driverNo,orderNo);
	}
}
