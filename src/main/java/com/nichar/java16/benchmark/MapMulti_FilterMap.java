package com.nichar.java16.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 * Benchmark of Stream::mapMulti(BiConsumer) against Stream::filter(Predicate).map(Function)
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(MapMulti_FilterMap.COUNT)
public class MapMulti_FilterMap {

	public static final int COUNT = 10_000;
	static List<Integer> LIST = new ArrayList<>();

	// This generates a sequence of 0, 1, 2, 3, 4, 0, 1...
	static {
		for (int i = 0; i < COUNT; i++) {
			LIST.add(i % 5);
		}
	}

	/**
	 * The method generates a list with filtered each 3rd value and mapped an increment by 1
	 * It uses Stream::filter(Predicate).map(Function) as of Java 8
	 * @return result list
	 */
	@Benchmark
	public List<Integer> filterMap() {
		return LIST.stream()
			.filter(i -> i % 3 == 0)
			.map(i -> i + 1)
			.collect(java.util.stream.Collectors.toList());
	}

	/**
	 * The method generates a list with filtered each 3rd value and mapped an increment by 1
	 * It uses Stream::mapMulti(BiConsumer) as of Java 16
	 * @return result list
	 */
	@Benchmark
	public List<Integer> mapMultiAsFilterMap() {
		return LIST.stream()
			.<Integer>mapMulti((integer, consumer) -> {
				if (integer % 3 == 0) {
					consumer.accept(integer + 1);
				}
			})
			.collect(Collectors.toList());
	}
}
