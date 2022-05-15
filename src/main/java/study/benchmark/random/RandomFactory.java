package study.benchmark.random;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wangdengwu
 */
@State(Scope.Benchmark)
public class RandomFactory {
    Random random;
    ThreadLocalRandom threadLocalRandom;

    public Random getRandom() {
        return random;
    }

    public ThreadLocalRandom getThreadLocalRandom() {
        return threadLocalRandom;
    }

    @Setup
    public void init() {
        random = new Random();
        threadLocalRandom = ThreadLocalRandom.current();
    }
}
