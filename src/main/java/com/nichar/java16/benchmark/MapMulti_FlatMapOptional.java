package com.nichar.java16.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;

/**
 * Benchmark of Stream::mapMulti(BiConsumer) against Stream::flatMap(Function) with Optional::stream()
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@OperationsPerInvocation(MapMulti_FlatMapOptional.COUNT)
public class MapMulti_FlatMapOptional {

	public static final int COUNT = 10_000;
	static List<Optional<Integer>> LIST = new ArrayList<>();

	// This generates a sequence of 0, 1, 2, 3, 4, 0, 1... where instead of '0' is Optional.empty()
	// and instead of the remaining numbers is an Optional.of(..) that number
	static {
		for (int i = 0; i < COUNT; i++) {
			if (i % 5 == 0) {
				LIST.add(Optional.empty());
			} else {
				LIST.add(Optional.of(i % 5));
			}
		}
	}

	/**
	 * The method generates a flattened list from Optional of integers
	 * It uses Stream::flatMap(Function) with Optional::stream() as of Java 9
	 * @return result list
	 */
	@Benchmark
	public List<Integer> flatMapOptional() {
		return LIST.stream()
			.flatMap(Optional::stream)
			.collect(Collectors.toList());
	}

	/**
	 * The method generates a flattened list from Optional of integers
	 * It uses Stream::mapMulti(BiConsumer) as of Java 16
	 * @return result list
	 */
	@Benchmark
	public List<Integer> mapMultiOptional() {
		return LIST.stream()
			.<Integer>mapMulti(Optional::ifPresent)
			.collect(Collectors.toList());
	}
}
