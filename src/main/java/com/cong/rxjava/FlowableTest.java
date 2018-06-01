/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.schedulers.Schedulers;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * <p>文件名称: FlowableTest.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年03月13日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class FlowableTest {

	public static void main(String[] args) throws Exception {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		Integer[] d = (Integer[]) list.toArray();
		Observable<Integer> observable = Observable.fromArray(d);
	}

	public static List<Integer> getAllList(Integer index) {
		List<Integer> integerList = new ArrayList<>();
		for (int i = 0; i < index; i++) {
			integerList.add(i);
		}
		return integerList;
	}

	public static void testFromCallable() {
		AtomicInteger count = new AtomicInteger();

		Observable.range(1, 10)
				.doOnNext(ignored -> count.incrementAndGet())
				.ignoreElements()
				.andThen(Single.fromCallable(() -> count.get()))
				.subscribe(System.out::println)
		;

	}

	public static void testFlowable1() throws Exception {
		Flowable<String> source = Flowable.fromCallable(() -> {

			System.out.println("come on");
			return "fuck yourself";
		});
		Flowable<String> runBackGround = source.subscribeOn(Schedulers.io());
		Flowable<String> showForeground = runBackGround.observeOn(Schedulers.single());
		runBackGround.subscribe(System.out::println, Throwable::printStackTrace);
		showForeground.subscribe(System.out::println, Throwable::printStackTrace);
		System.out.println("baby");

		Thread.sleep(2000);
	}

	private static void testGetList() {
		int index = 10;
		Flowable<List<Integer>> listFlowable = Flowable.fromCallable(() -> {
			List<Integer> integerList = getList(index);
			Thread.sleep(1000);
			return integerList;
		});

		Flowable<List<Integer>> runBackGround = listFlowable.subscribeOn(Schedulers.io());
		Flowable<List<Integer>> showForeground = runBackGround.observeOn(Schedulers.single());
		runBackGround.subscribe(s -> {
			System.out.println(s + "come on");
		});
		showForeground.subscribe(s -> {
			System.out.println(s);
		});
		System.out.println("fuck ");

	}

	private static List<Integer> getList(Integer index) throws Exception {
		List<Integer> integerList = new ArrayList<>();
		for (int i = 0; i < index; i++) {
			Thread.sleep(100);
			integerList.add(i * i);
		}
		return integerList;
	}

	/**
	 * 网络请求io密集型操作
	 * @throws Exception
	 */
	public static void testFlowable() throws Exception {
		Flowable.fromCallable(() -> {
			Thread.sleep(1000);
			return "fuck";
		}).subscribeOn(Schedulers.io())
				.observeOn(Schedulers.single())
				.subscribe(System.out::println, Throwable::printStackTrace);
		Thread.sleep(2000);
	}

	public static void testObservable() {
		Observable<Integer> o = Observable.create(s -> {
			s.onNext(1);
			s.onNext(2);
			s.onNext(3);
			s.onNext(3);
			s.onComplete();
		});

		o.toMap(i -> "Number" + i)
				.subscribe(s -> System.out.println(s));
	}
}






























