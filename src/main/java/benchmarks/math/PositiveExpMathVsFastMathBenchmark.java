package benchmarks.math;

import org.apache.commons.math3.util.FastMath;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PositiveExpMathVsFastMathBenchmark {

    /*
     * Benchmark control. We expect the benchmark costs to grow linearly with increased task size.
     * If it doesn't happen, then something is wrong.
     */
    @Param({"1", "10", "100", "1000"})
    int size;
    int[] arguments;

    @Setup
    public void setup() {
        arguments = new int[size];
        for (int i = 0; i < size; i++) {
            arguments[i] = i;
        }
    }

    @Benchmark
    public void mathExp(Blackhole bh) {
        for (int x : arguments) {
            bh.consume(Math.exp(x));
        }
    }

    @Benchmark
    public void fastMathExp(Blackhole bh) {
        for (int x : arguments) {
            bh.consume(FastMath.exp(x));
        }
    }

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================

     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar PositiveExpMathVsFastMathBenchmark
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PositiveExpMathVsFastMathBenchmark.class.getSimpleName())
                .forks(3)
                .build();
        new Runner(opt).run();
    }

/*
Benchmark                                       (size)  Mode  Cnt      Score    Error  Units
PositiveExpMathVsFastMathBenchmark.fastMathExp       1  avgt   15     22.223 ±  0.025  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp      10  avgt   15    224.585 ±  0.332  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp     100  avgt   15   2245.563 ±  1.811  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp    1000  avgt   15  17168.866 ± 32.334  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp           1  avgt   15      7.316 ±  0.260  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp          10  avgt   15    164.232 ±  0.203  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp         100  avgt   15   1783.495 ±  1.845  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp        1000  avgt   15  19127.850 ± 16.860  ns/op
*/

}
