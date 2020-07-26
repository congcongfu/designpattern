 /**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * <p>文件名称: NaturalNumbersIterator.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月21日</p>
 *
 * @since 3.0
 * 
 */
public class NaturalNumbersIterator implements Iterator<BigInteger> {

	private BigInteger current = BigInteger.ZERO;

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public BigInteger next() {
		current = current.add(BigInteger.ONE);
		return current;
	}
}
