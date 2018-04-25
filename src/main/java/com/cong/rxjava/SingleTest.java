/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: SingleTest.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月21日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class SingleTest {

	public static Single<String> getDataA() {
		return Single.<String>create(o -> {
			o.onSuccess("DataA");
		}).subscribeOn(Schedulers.io());
	}

	public static Single<String> getDataB() {
		return Single.just("DataB")
				.subscribeOn(Schedulers.io());
	}

	public static void merge1() {
		Flowable<String> aMergeB = getDataA().mergeWith(getDataB());
		aMergeB.forEach(s -> System.out.println(s));
	}

	public static void merge2() {
		Single.merge(getDataA(), getDataB()).forEach(s -> System.out.println("merge " + s));
	}

	public static void main(String[] args) {
		merge1();
		merge2();
	}
}
