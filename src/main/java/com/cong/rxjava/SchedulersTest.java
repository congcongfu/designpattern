/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: SchedulersTest.java</p>
 * <p>文件描述: </p>
 * <p>版权所有: 版权所有(C)2016-2099</p>
 * <p>公   司: 优行科技</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年06月03日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class SchedulersTest {

	public static void main(String[] args) throws Exception {
		testGroupby();
		Thread.sleep(1000);
	}

	public static void testGroupby() {

	}

	public static void testFlatMapConcurrent() {
		Observable
				.just("bread", "butter", "milk", "tomato", "chesses")
				.flatMap(pro -> purchase(pro, 1)
						.subscribeOn(Schedulers.io()))
				.reduce(BigDecimal::add)
				.toSingle()
				.subscribe(data -> System.out.println(Thread.currentThread().getName() + " result " + data));
	}

	private static Observable<BigDecimal> purchase(String productName, int quantity) {
		return Observable.fromCallable(() -> doPurchase(productName, quantity));
	}

	private static BigDecimal doPurchase(String productName, int quantity) throws Exception {
		printlnLog("Purchasing " + quantity + " " + productName);
		printlnLog("Done " + quantity + " " + productName);
		Thread.sleep(50);
		Random random = new Random(6);
		return new BigDecimal(random.nextInt(5) + quantity);
	}

	static void printlnLog(String msg) {
		System.out.println(Thread.currentThread().getName() + " " + msg);
	}

	private static Observable<String> simple() {
		return Observable.create(s -> {
			System.out.println(Thread.currentThread().getName() + " Subscribed");
			s.onNext("A");
			s.onNext("B");
			s.onComplete();
		});
	}

	public static Scheduler schedulerA() {
		ExecutorService poolA = Executors.newFixedThreadPool(10, threadFactory("Sched-A-%d"));
		return Schedulers.from(poolA);
	}

	public static Scheduler schedulerB() {
		ExecutorService poolB = Executors.newFixedThreadPool(10, threadFactory("Sched-B-%d"));
		return Schedulers.from(poolB);
	}

	private static ThreadFactory threadFactory(String pattern) {
		return new ThreadFactoryBuilder().setNameFormat(pattern).build();
	}

	public static void testTrampoline() {
		Schedulers.shutdown();
		Schedulers.single();
		Scheduler scheduler = Schedulers.trampoline();
		Scheduler.Worker worker = scheduler.createWorker();
		System.out.println("Main start");
		worker.schedule(() -> {
			System.out.println(" Outer start ");
			sleepOneSecond();
			worker.schedule(() -> {
				System.out.println("Inner start");
				sleepOneSecond();
				System.out.println("Inner end");
			});
			System.out.println(" Outer end");
		});
		System.out.println(" Main end");
	}

	private static void sleepOneSecond() {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
	}
}
