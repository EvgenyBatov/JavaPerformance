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
public class NegativeExpMathVsFastMathBenchmark {

    /*
     * Benchmark control. We expect the benchmark costs to grow linearly with increased task size.
     * If it doesn't happen, then something is wrong.
     */
    @Param({"1", "10", "100", "1000"})
    int size;
    double[] arguments;

    @Setup
    public void setup() {
        arguments = new double[size];
        for (int i = 0; i < size; i++) {
            arguments[i] = -i;
        }
    }

    @Benchmark
    public void mathExp(Blackhole bh) {
        for (double x : arguments) {
            bh.consume(Math.exp(x));
        }
    }

    @Benchmark
    public void fastMathExp(Blackhole bh) {
        for (double x : arguments) {
            bh.consume(FastMath.exp(x));
        }
    }

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar NegativeExpMathVsFastMathBenchmark
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NegativeExpMathVsFastMathBenchmark.class.getSimpleName())
                .forks(3)
                .build();
        new Runner(opt).run();
    }

/*
Benchmark                                       (size)  Mode  Cnt      Score     Error  Units
NegativeExpMathVsFastMathBenchmark.fastMathExp       1  avgt   15     13.328 ±   0.124  ns/op
NegativeExpMathVsFastMathBenchmark.fastMathExp      10  avgt   15    123.769 ±   0.189  ns/op
NegativeExpMathVsFastMathBenchmark.fastMathExp     100  avgt   15   1253.024 ±  19.199  ns/op
NegativeExpMathVsFastMathBenchmark.fastMathExp    1000  avgt   15  21264.737 ±  42.074  ns/op
NegativeExpMathVsFastMathBenchmark.mathExp           1  avgt   15      7.080 ±   0.303  ns/op
NegativeExpMathVsFastMathBenchmark.mathExp          10  avgt   15     95.867 ±   3.158  ns/op
NegativeExpMathVsFastMathBenchmark.mathExp         100  avgt   15    988.940 ±  12.531  ns/op
NegativeExpMathVsFastMathBenchmark.mathExp        1000  avgt   15  15229.322 ± 131.025  ns/op
*/

}
