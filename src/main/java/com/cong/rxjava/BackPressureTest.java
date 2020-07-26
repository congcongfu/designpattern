/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: BackPressureTest.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月11日</p>
 *
 * @since 3.0
 * 
 */
public class BackPressureTest {

	public static void main(String[] args) {
		testPublishProcess();
	}

	public static void testPublishProcess() {
		PublishProcessor<Integer> source = PublishProcessor.create();
		source
				.subscribeOn(Schedulers.computation())
				.subscribe(data -> {
					Thread.sleep(100L);
					System.out.println(data);
				});
		for (int i = 0; i < 10000; i++) {
			source.onNext(i);
		}
		source.onComplete();
	}
}
