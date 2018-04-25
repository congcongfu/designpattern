package com.cong.pattern.strategy;

/**
 * <p>文件名称: DriverStopBill.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年01月19日</p>
 *
 * @since 1.0
 * @author by fu.cong@geely.com
 */
public interface DriverStopBill {

	void stopBill(Long driverNo,Long orderNo);
}
