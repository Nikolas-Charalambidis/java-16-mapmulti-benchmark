package com.nichar.java16.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 * Benchmark of Stream::mapMulti(BiConsumer) against Stream::flatMap(Function)
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(MapMulti_FlatMap.COUNT)
public class MapMulti_FlatMap {

	public static final int COUNT = 10_000;
	static List<Integer> LIST = new ArrayList<>();

	// This generates a sequence of 0, 1, 2, 3, 4, 0, 1...
	static {
		for (int i = 0; i < COUNT; i++) {
			LIST.add(i % 5);
		}
	}

	/**
	 * The method generates and flattens a list of size of the same number of elements as the input number itself
	 * It uses Stream::flatMap(Function) as of Java 8
	 * @return result flattened list
	 */
	@Benchmark
	public List<Integer> flatMap() {
		return LIST.stream()
			.flatMap(i -> IntStream.range(0, i).mapToObj(j -> i))
			.collect(Collectors.toList());
	}

	/**
	 * The method generates and flattens a list of size of the same number of elements as the input number itself
	 * It uses Stream::mapMulti(BiConsumer) as of Java 16
	 * @return result flattened list
	 */
	@Benchmark
	public List<Integer> mapMulti() {
		return LIST.stream()
			.<Integer>mapMulti((integer, consumer) -> {
				for (int i = 0; i < integer; i++) {
					consumer.accept(integer);
				}
			})
			.collect(Collectors.toList());
	}
}
