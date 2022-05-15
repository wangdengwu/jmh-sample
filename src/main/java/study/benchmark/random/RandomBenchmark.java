package study.benchmark.random;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * @author wangdengwu
 */
@BenchmarkMode({Mode.AverageTime})
@Fork(1)
@Warmup(iterations = 1, time = 1)
@Measurement(iterations = 5, time = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class RandomBenchmark {

    @Benchmark
    public int nextInt(RandomFactory factory) {
        return factory.getRandom().nextInt(10);
    }

    @Benchmark
    public int nextIntWithThreadLocal(RandomFactory factory) {
        return factory.getThreadLocalRandom().nextInt(10);
    }
}
