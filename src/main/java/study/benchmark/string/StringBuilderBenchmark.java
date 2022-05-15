package study.benchmark.string;

import org.openjdk.jmh.annotations.*;
import study.benchmark.random.RandomFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author wangdengwu
 */
@BenchmarkMode({Mode.SingleShotTime})
@Fork(1)
@Warmup(iterations = 1, batchSize = 10)
@Measurement(iterations = 10, batchSize = 50000)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBuilderBenchmark {

    @Benchmark
    @Measurement(iterations = 10, batchSize = 50000)
    public void plus(AbstractStringBuilderFactory factory, RandomFactory randomFactory) {
        String string = factory.getString();
        string += randomFactory.getThreadLocalRandom().nextInt(10);
        factory.setString(string);
    }

    @Benchmark
    public StringBuilder appendWithBuilder(AbstractStringBuilderFactory factory, RandomFactory randomFactory) {
        return factory.getStringBuilder().append(randomFactory.getThreadLocalRandom().nextInt(10));
    }

    @Benchmark
    public StringBuilder appendWithSynchronized(AbstractStringBuilderFactory factory, RandomFactory randomFactory) {
        StringBuilder stringBuilder = factory.getStringBuilder();
        synchronized (stringBuilder) {
            return stringBuilder.append(randomFactory.getThreadLocalRandom().nextInt(10));
        }
    }

    @Benchmark
    public StringBuffer appendWithBuffer(AbstractStringBuilderFactory factory, RandomFactory randomFactory) {
        return factory.getStringBuffer().append(randomFactory.getThreadLocalRandom().nextInt(10));
    }
}
