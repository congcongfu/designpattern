/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.strategy;

/**
 * <p>文件名称: AbstractDriverActor.java</p>
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
public abstract class AbstractDriverActor {

	protected String name;

	protected DriverOnline driverOnline;

	protected DriverOffline driverOffline;

	protected DriverConfirmOrder driverConfirmOrder;

	protected DriverStartBill driverStartBill;

	protected DriverStopBill driverStopBill;

	public AbstractDriverActor setDriverOnline(DriverOnline driverOnline) {
		this.driverOnline = driverOnline;
		return this;
	}

	public AbstractDriverActor setDriverOffline(DriverOffline driverOffline){
		this.driverOffline= driverOffline;
		return this;
	}

	public AbstractDriverActor setDriverConfirmOrder(DriverConfirmOrder driverConfirmOrder) {
		this.driverConfirmOrder = driverConfirmOrder;
		return this;
	}

	public AbstractDriverActor setDriverStartBill(DriverStartBill driverStartBill) {
		this.driverStartBill = driverStartBill;
		return this;
	}

	public AbstractDriverActor setDriverStopBill(DriverStopBill driverStopBill) {
		this.driverStopBill = driverStopBill;
		return this;
	}

	protected void online(Long driverNo) {
		driverOnline.online(driverNo);
	}

	protected void offline(Long driverNo){
		driverOffline.offline(driverNo);
	}

	protected void confirmOrder(Long driverNo,Long orderNo){
		driverConfirmOrder.orderConfirm(driverNo,orderNo);
	}

	protected void startBill(Long driverNo,Long orderNo){
		driverStartBill.startBill(driverNo,orderNo);
	}

	protected void stopBill(Long driverNo,Long orderNo){
		driverStopBill.stopBill(driverNo,orderNo);
	}



}
