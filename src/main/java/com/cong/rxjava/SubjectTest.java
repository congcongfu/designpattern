/**
 * Company
 * Copyright (C) 2004-2018 All Rights Reserved.
 */
package com.cong.rxjava;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

/**
 * <p>文件名称: SubjectTest.java</p>
 * <p>文件描述: </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期: @Date 2018年05月09日</p>
 *
 * @since 3.0
 * 
 */
public class SubjectTest {

	public static void main(String[] args) {
	}

	public static void  testSubject(){
		Subject subject = AsyncSubject.create().toSerialized();
	}

	public static void testAsyncSubject() {
		AsyncSubject subject = AsyncSubject.create();
		// // TODO: 18/5/9
	}

	public static void testBehaviorSubject() {
		BehaviorSubject subject = BehaviorSubject.create();
		// // TODO: 18/5/9

	}

	public static void testReplaySubject(){
		ReplaySubject relaySubject = ReplaySubject.create();
		// // TODO: 18/5/9

	}
}
