package study.benchmark.main;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import study.benchmark.random.RandomBenchmark;
import study.benchmark.string.StringBuilderBenchmark;

/**
 * @author wangdengwu
 */
public class BenchmarkMain {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-Xmx1024m")
                .threads(Runtime.getRuntime().availableProcessors())
                .include(RandomBenchmark.class.getSimpleName())
                .include(StringBuilderBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
