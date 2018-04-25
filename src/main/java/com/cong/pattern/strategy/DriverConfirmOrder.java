package com.cong.pattern.strategy;

/**
 * <p>文件名称: DriverConfirmOrder.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年01月19日</p>
 *
 * @since 1.0
 * @author by fu.cong@geely.com
 */
public interface DriverConfirmOrder {

	void orderConfirm(Long driverNo,Long orderNo);
}
