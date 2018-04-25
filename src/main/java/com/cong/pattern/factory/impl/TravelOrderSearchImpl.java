/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.pattern.factory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cong.constants.BizEnum;
import com.cong.domain.OrderDetailDTO;
import com.cong.domain.UnfinishOrderDTO;
import com.cong.pattern.factory.OrderSearch;
import io.reactivex.Observable;

/**
 * <p>文件名称: TravelOrderSearchImpl.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月23日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class TravelOrderSearchImpl implements OrderSearch {

	@Override
	public OrderDetailDTO queryDetailByOrderNo(Long OrderNo) {
		return null;
	}

	@Override
	public Observable<UnfinishOrderDTO> searchUnfinishByDriverNo(Long driverNo) {
		return Observable.create(s -> {
			s.onNext(UnfinishOrderDTO.builder()
					.orderNo(new Random(100).nextLong())
					.driverNo(driverNo)
					.biz(BizEnum.TRAVEL.getValue())
					.build());
			s.onComplete();
		});
	}

	@Override
	public Observable<List<OrderDetailDTO>> queryOrderList(Long driverNo) {
		return Observable.<List<OrderDetailDTO>>create(s -> {
			//search
			List<OrderDetailDTO> orderDetailDTOList = new ArrayList<OrderDetailDTO>();
			for (int i = 1; i < 5; i++) {
				orderDetailDTOList.add(OrderDetailDTO.builder()
						.orderNo(new Random(i).nextLong())
						.biz(BizEnum.TRAVEL.getValue())
						.driverNo(driverNo)
						.customerName("乘客" + i)
						.build());
			}
			s.onNext(orderDetailDTOList);
			s.onComplete();
		});
	}
}
