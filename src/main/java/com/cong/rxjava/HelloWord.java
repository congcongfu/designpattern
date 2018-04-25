/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: HelloWord.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年03月06日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class HelloWord {

	public static void main(String[] args) throws Exception {
	}

	public static Single<String> getDataA(){
		return Single.<String> create(o -> {
			o.onSuccess("DataA");
		}).subscribeOn(Schedulers.io());
	}

	public static Single<String> getDataB(){
		return Single.<String> create(o -> {
			o.onSuccess("DataB");
		}).subscribeOn(Schedulers.io());
	}

	public static Observable<Integer> getDataFromLocal(){
		Observable<Integer> observable = Observable.create(s->{
			for (int i =0 ; i < 25; i++){
				s.onNext(i);
			}
			s.onComplete();
		});
		return observable;
	}


	public static void mergeThread() {
		Observable<Integer> a = Observable.create(s -> {
			new Thread(() ->{
				s.onNext(1);
				s.onNext(2);

				try {
					Thread.sleep(1000);
				}catch (Exception e){

				}
				s.onNext(3);
				s.onNext(4);
				s.onComplete();
			}).start();

		});

		Observable<Integer> b = Observable.create(s -> {
			new Thread(() -> {
				s.onNext(6);
				s.onNext(7);
				s.onNext(8);
				s.onNext(9);
				s.onComplete();
			}).start();

		});

		Observable<Integer> c = Observable.merge(a,b);
		c.forEach(s -> System.out.println(s));

	}

	public static void doReactiveMap() {
		Long time = System.currentTimeMillis();
		Flowable.range(1, 10)
				.observeOn(Schedulers.computation())
				.map(v -> v * v)
				.blockingSubscribe(System.out::println);
		System.out.println("  cost " + (System.currentTimeMillis() - time) + " ms");
	}

	public static void parallel() {
		Long time = System.currentTimeMillis();
		Flowable.range(1, 10)
				.flatMap(v ->
						Flowable.just(v)
								.subscribeOn(Schedulers.computation())
								.map(w -> w * w)
				)
				.blockingSubscribe();
		System.out.println(" parallel  cost " + (System.currentTimeMillis() - time) + " ms");
	}

	public static void sequential() {
		Long time = System.currentTimeMillis();
		Flowable.range(1, 10)
				.observeOn(Schedulers.computation())
				.map(v -> v * v)
				.blockingSubscribe(System.out::println);
		System.out.println(" sequential  cost " + (System.currentTimeMillis() - time) + " ms");
	}

	public static void parallel2() {
		Long time = System.currentTimeMillis();
		Flowable.range(1, 10)
				.parallel()
				.runOn(Schedulers.computation())
				.map(v -> v * v)
				.sequential()
				.blockingSubscribe(System.out::println);
		System.out.println(" parallel2  cost " + (System.currentTimeMillis() - time) + " ms");

	}

}
