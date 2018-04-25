/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>文件名称: OrderDetailDTO.java</p>
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO implements Serializable {

	private static final long serialVersionUID = 666L;

	private Long driverNo;

	private Long orderNo;

	private String customerName;

	private Integer biz;
}
