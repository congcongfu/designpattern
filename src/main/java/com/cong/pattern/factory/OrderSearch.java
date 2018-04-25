package com.cong.pattern.factory;

import java.util.List;

import com.cong.domain.OrderDetailDTO;
import com.cong.domain.UnfinishOrderDTO;
import io.reactivex.Observable;

/**
 * <p>文件名称: OrderSearch.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月23日</p>
 *
 * @since 1.0
 * @author by fu.cong@geely.com
 */
public interface OrderSearch {

	OrderDetailDTO queryDetailByOrderNo(Long orderNo);

	Observable<UnfinishOrderDTO> searchUnfinishByDriverNo(Long driverNo);

	Observable<List<OrderDetailDTO>> queryOrderList(Long driverNo);
}
