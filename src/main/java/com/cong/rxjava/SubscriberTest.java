/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;


import io.reactivex.Observable;
import io.reactivex.Observer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * <p>文件名称: SubscriberTest.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月22日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class SubscriberTest {

	public static void test(){
		Subscriber<Integer> subscriber = new Subscriber<Integer>() {
			@Override
			public void onSubscribe(Subscription s) {

			}

			@Override
			public void onNext(Integer integer) {
				if (integer > 0){
					onComplete();
				}
			}

			@Override
			public void onError(Throwable t) {

			}

			@Override
			public void onComplete() {

			}
		};
		Observable<Integer> source = Observable.just(1);
	}
}
