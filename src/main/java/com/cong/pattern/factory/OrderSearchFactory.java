/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


import com.cong.constants.BizEnum;
import com.cong.domain.OrderDetailDTO;
import com.cong.domain.UnfinishOrderDTO;
import com.cong.pattern.factory.impl.HermesOrderSearchImpl;
import com.cong.pattern.factory.impl.TravelOrderSearchImpl;
import com.cong.pattern.factory.impl.ZhuacheOrderSearchImpl;
import com.google.common.collect.Lists;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * <p>文件名称: OrderSearchFactory.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月23日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class OrderSearchFactory {

	private static ConcurrentHashMap<BizEnum, OrderSearch> searchFactory = new ConcurrentHashMap();

	/**
	 * 这一块可用包扫描
	 */
	static {
		searchFactory.put(BizEnum.ZHUANCHE, new ZhuacheOrderSearchImpl());
		searchFactory.put(BizEnum.HERMES, new HermesOrderSearchImpl());
		searchFactory.put(BizEnum.TRAVEL, new TravelOrderSearchImpl());
	}

	public OrderDetailDTO searchOrderDetail(Integer biz, Long orderNo) {
		if (!BizEnum.contains(biz)) throw new RuntimeException("业务线不存在 biz=" + biz);
		return searchFactory.get(BizEnum.getEnumByValue(biz)).queryDetailByOrderNo(orderNo);
	}

	public UnfinishOrderDTO unfinishOrder(Long driverNo) {
		Observable<UnfinishOrderDTO> source = Observable.empty();
		UnfinishOrderDTO result = new UnfinishOrderDTO();
		return result;
	}

	public List<OrderDetailDTO> queryAllOrderListByDriverNo(Long driverNo) {
		List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
		List<BizEnum> bizEnumLists = Lists.newArrayList(BizEnum.values());
		bizEnumLists.parallelStream().forEach(
				bizEnum -> {
					Observable<List<OrderDetailDTO>> observable = searchFactory.get(bizEnum).queryOrderList(driverNo);
					observable.subscribe(s -> orderDetailDTOList.addAll(s));
				}
		);
		return orderDetailDTOList;
	}


	public static void main(String[] args) {
		OrderSearchFactory orderSearchFactory = new OrderSearchFactory();
		List<OrderDetailDTO> orderDetailDTOList = orderSearchFactory.queryAllOrderListByDriverNo(1003019L);
		System.out.println(orderDetailDTOList.size());
		System.out.println(orderDetailDTOList);
	}

}
