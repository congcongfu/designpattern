/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: ObservableTest.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年04月05日</p>
 *
 * @since 3.0
 * @author by fu.cong@geely.com
 */
public class ObservableTest {

	private static Long start = System.currentTimeMillis();

	public static Boolean isSlowTickTime() {
		return (System.currentTimeMillis() - start) % 30_000 >= 15_000;
	}

	public static void main(String[] args) throws Exception {
		testScan();
		Thread.sleep(10000);
	}

	private static Observable<String> stream(int initiaDelay, int interval, String name) {
		return Observable
				.interval(initiaDelay, interval, TimeUnit.MILLISECONDS)
				.map(x -> name + x)
				.doOnSubscribe((s) ->
						System.out.println("Subscribe to " + name));
	}

	public static void doSleep(Long driverNo) throws Exception {
		String log = Thread.currentThread() + " " + driverNo + " ";
		System.out.println(log);
		Thread.sleep(60L);
	}

	public static void testget() {
		List<Integer> integerList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			integerList.add(i);
		}
		Observable<Integer> observable = Observable.<Integer>create(s -> {
			integerList.parallelStream().forEach(i -> {
				s.onNext(getNextInteger(i));
			});
		});
		Long start = System.currentTimeMillis();
		observable.subscribe(System.out::println);
		System.out.println("1 耗时 " + (System.currentTimeMillis() - start) + " ms");
		start = System.currentTimeMillis();

		observable.subscribe(System.out::println);
		System.out.println("2 耗时 " + (System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		integerList.parallelStream().forEach(s -> System.out.println(getNextInteger(s)));
		System.out.println("3 耗时 " + (System.currentTimeMillis() - start) + " ms");

	}

	private static Integer getNextInteger(Integer i) {
		try {
			Thread.sleep(100L);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return i * i;
	}

	public static void testOnError() {

		Observable<Integer> observable = Observable.create(s -> {
			for (int i = 0; i < 10; i++) {
				s.onNext(getInt(i));
			}
		});
		observable.subscribe(s -> System.out.println(s),
				error -> System.out.println("测试异常 " + error));
	}

	public static Integer getInt(Integer number) {

		if (number == 8) throw new RuntimeException("test exception");
		return number * number;
	}

	public void test() throws Exception {
		Observable<Long> fast = Observable.interval(1, TimeUnit.SECONDS);
		Observable<Long> slow = Observable.interval(3, TimeUnit.SECONDS);
		Observable<Long> clock = Observable.merge(
				slow.filter(tick -> isSlowTickTime()),
				fast.filter(tick -> !isSlowTickTime())
		);

		clock.subscribe(tick -> System.out.println(new Date()));

		Thread.sleep(60000);
	}

	public static void testMap() {
		Observable.just(8, 9, 10)
				.doOnNext(i -> System.out.println("A: " + i))
				.filter(i -> i % 3 > 0)
				.doOnNext(i -> System.out.println("B: " + i))
				.map(i -> "#" + i * 10)
				.doOnNext(s -> System.out.println("C: " + s))
				.filter(s -> s.length() < 4)
				.subscribe(s -> System.out.println("D: " + s));
	}

	/**
	 * 转换操做
	 */
	public static void testFlatMap() {
		Observable<Integer> source = Observable.just(8, 9, 10);
		source.flatMap(s -> Observable.just(s * 2)).subscribe(System.out::println);// 等同于map
		source.flatMap(s -> (s != 10) ? Observable.just(s * 2) : Observable.empty()) //等同于 filter
				.subscribe(System.out::println);
	}

	public static void testFloatMapConcurrent() {
		Observable<Integer> source = Observable.create(s -> {
			for (int i = 0; i < 1000; i++) {
				s.onNext(i);
			}
			s.onComplete();
		});
		source.flatMap(s -> Observable.just(s + "fucker"), 10)
				.delay(1, TimeUnit.MILLISECONDS)
				.subscribe(System.out::println);
	}

	/**
	 * 延迟操作
	 * @throws Exception
	 */
	public static void testDelay() throws Exception { //并不保证有序
		Observable.just(10L, 9L, 8L, 7L, 6L, 5L, 3L, 4L, 2L, 1L)
				.flatMap(x -> Observable.just(x).delay(x, TimeUnit.SECONDS))
				.subscribe(System.out::println);
		System.out.println("fuck");
		Thread.sleep(20000);
	}

	/**
	 * 合并一次发射一次结果
	 */
	public static void testScan() {
		Observable<Integer> source = Observable.range(10, 20);
		source.subscribe(System.out::println);
		source.scan((item1, item2) -> item1 + item2)
				.subscribe(s -> System.out.println("scan " + s));
	}

	/**
	 * 所有结果处理完一次输出
	 */
	public static void testReduce() {
		Observable<Integer> source = Observable.range(10, 20);
		source.reduce((item1, item2) -> item1 + item2)
				.subscribe(System.out::println);

	}

	public static void testCollect() {
	}

	/**
	 * 控制采样
	 */
	public static void testSample() {
		Observable.create(e -> {
			for (long i = 0; ; i++)
				e.onNext(i); //元素发射器
		})
				.sample(1, TimeUnit.SECONDS)
				.subscribe(System.out::println);

	}

	/**
	 * 指定获取前多少个
	 * @param number
	 */
	public static void testTakeSome(Integer number) {
		Observable.create(s -> {
			for (long i = 0; ; i++) {
				s.onNext(i);
			}
		})
				.take(number)
				.subscribe(s -> {
					System.out.println(s);
				});
	}

	/**
	 * 去重复
	 * @param number
	 */
	public static void testDistinct(Integer number) {
		Observable.create(s -> {
			for (int i = 0; i < 100; i++) {
				s.onNext(i);
			}
		})
				.take(number)
				.repeat(3)
				.distinct()
				.subscribe(s -> {
					System.out.println(s);
				});
	}

	public static void dosomth() {
		Observable.range(0, 10).subscribe(
				data -> {
					System.out.println(data);
				},
				error -> {
					error.printStackTrace();
				},
				() -> {
					System.out.println("no more data");
				}
		);

		Flowable.just("Rxjava 2.x Flowable").subscribe(System.out::println);
	}

	public static void testOnError1() {
		Observable<Long> source = Observable.create(e -> {
			while (!e.isDisposed()) {
				long time = System.currentTimeMillis();
				e.onNext(time);
				if (time % 2 != 0) {
					e.onError(new IllegalArgumentException("Odd millisecond!"));
					break;
				}
			}
		});
		source.subscribe();

	}

	/**
	 * If you want an Observable to emit a specific sequence of items
	 * before it begins emitting the items normally expected from it, apply the StartWith operator to it
	 *
	 * 把某个元素插到第几个位置
	 */
	public static void testStartWith() {
		List<Integer> startList = new ArrayList<>();
		startList.add(3);
		startList.add(6);
		Observable<Integer> source = Observable.create(s -> {
			for (int i = 16; i < 30; i++) {
				s.onNext(i);
			}
			s.onComplete();
		});
		source
				.startWith(1)
				.startWith(startList)
				.subscribe(System.out::println);
	}

	public static void testMerge() {
		Observable<Integer> source1 = Observable.create(s -> {
			for (int i = 1; i < 15; i++) {
				s.onNext(i * 2);
			}
			s.onComplete();
		});

		Observable<Integer> source2 = Observable.create(s -> {
			for (int i = 1; i < 15; i++) {
				s.onNext(i * 2 + 1);
			}
			s.onComplete();
		});

		Observable.merge(source2, source1)
				.sorted()
				.subscribe(System.out::println)
		;

		Observable.mergeDelayError(source1, source2)
				.sorted()
				.subscribe(System.out::println);

	}

	/**
	 * 组装元素
	 */
	public static void testZip() {
		Observable<Integer> source = Observable.range(0, 5);
		Observable<String> source1 = Observable.create(s -> {
			for (int i = 1; i < 6; i++) {
				s.onNext(" " + i + "data  ");
			}
		});
		Observable<Long> source2 = Observable.rangeLong(6, 10);

		Observable.zip(source, source1, source2, new Function3<Integer, String, Long, String>() {
			@Override
			public String apply(@NonNull Integer integer, @NonNull String s, @NonNull Long aLong) throws Exception {
				return integer + s + aLong;
			}
		})
				.subscribe(System.out::println);

	}

	public static void testZipWith() {
		Observable<Integer> source = Observable.range(0, 5);
		Observable<String> source1 = Observable.create(s -> {
			for (int i = 1; i < 6; i++) {
				s.onNext(" " + i + "data  ");
			}
		});
		Observable<Long> source2 = Observable.rangeLong(6, 10);
		source.zipWith(source1, (s, s2) -> s + "_" + s2)
				.zipWith(source2, (zips, s3) -> zips + "_" + s3)
				.subscribe(System.out::println);
	}

	/**
	 * 组合最近的元素
	 */
	public static void testCombineLatest() {

		Observable<Long> source = Observable.intervalRange(0, 4, 1, 1, TimeUnit.SECONDS);
		Observable<Long> source2 = Observable.intervalRange(0, 4, 1, 1, TimeUnit.SECONDS);
		Observable
				.combineLatest(source, source2, (s, s2) -> (" combineLatest " + s + "+" + s2 + " =" + (s + s2)))
				.subscribe(System.out::println);
	}

	/**
	 * 组合最近的元素
	 */
	public static void testWithLatestFrom() {

		Observable<Long> source = Observable.intervalRange(0, 5, 1, 1, TimeUnit.SECONDS);
		Observable<Long> source2 = Observable.intervalRange(0, 5, 1, 1, TimeUnit.SECONDS);
		source.withLatestFrom(source2, (s, s2) -> (" withLatestFrom" + s + " +" + s2 + "= " + (s + s2)))
				.subscribe(System.out::println);
	}

	/**
	 * 待完善
	 */
	public static void testJoin() {
		Observable<String> source = Observable.just("join1-", "join2-", "join3-", "join4");
		Observable<Integer> source1 = Observable.create(s -> {
			for (int i = 0; i < 4; i++) {
				s.onNext(i);
			}
		});
	}

	public static void testGroupJoin() {
	}

	/**
	 * 把一组Observable 转换成一个Observable, 如果同一时间内存在两个或者多个Observable 提交的结果
	 * 只取最后一个Observable 提交的结果给订阅者
	 */
	public static void testSwitchOnNext() {
		Observable<Observable<Long>> observable = Observable.create(s -> {
			for (int i = 0; i < 4; i++) {
				Observable<Long> source = Observable.interval(i * 100, TimeUnit.MILLISECONDS);
				s.onNext(source);
			}
		});
		Observable.switchOnNext(observable)
				.subscribe(System.out::println);
	}

	/**
	 * 不要这么写
	 */
	public static void testddd() {
		Observable<Integer> source = Observable.create(s -> {
			for (int i = 0; i < 200; i++) {
				final int data = i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(100);
							s.onNext(data);
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}).start();
			}
		});
		source.doOnNext(s -> System.out.println(Thread.currentThread()))
				.filter(i -> (i % 2 == 0))
				.map(i -> "value " + i + " process on " + Thread.currentThread())
				.subscribe(s -> System.out.println("SOME VALUE =>" + s));
		System.out.println("Will print BEFORE values are emitted");

	}


	/**
	 *订阅多次
	 */
	public static void testSubscribeMoreThenOnce() {
		List<String> stringList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			stringList.add(i + "");
		}
		Observable<String> ss = Observable.fromIterable(stringList);
		ss
				.subscribe(s -> System.out.println("subscribe 1 " + s));
		ss
				.subscribe(s -> System.out.println("subscribe 2 " + s));
	}

	public static void testErrorResumeNext() {
		Observable<String> fallBack = Observable.just("fallBack");
		Observable<String> source = Observable.create(s -> {
			for (int i = 0; i < 100; i++) {
				s.onNext(i + "");
				if (i == 20) {
					s.onError(new RuntimeException("出错了"));
				}
			}
			s.onComplete();
		});
		source.onErrorResumeNext(fallBack)
				.subscribe(s -> System.out.println("subscribe 3 " + s));
	}

}















