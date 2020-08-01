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
    double[] arguments;

    @Setup
    public void setup() {
        arguments = new double[size];
        for (int i = 0; i < size; i++) {
            arguments[i] = i;
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
Java 8
Benchmark                                       (size)  Mode  Cnt      Score      Error  Units
PositiveExpMathVsFastMathBenchmark.fastMathExp       1  avgt   15     14.325 ±    0.060  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp      10  avgt   15    124.719 ±    2.895  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp     100  avgt   15   1175.901 ±    6.113  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp    1000  avgt   15   9775.396 ±  142.988  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp           1  avgt   15     50.401 ±    1.852  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp          10  avgt   15    485.312 ±    0.832  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp         100  avgt   15   4852.641 ±    6.968  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp        1000  avgt   15  84379.939 ± 1143.242  ns/op

Java 14
Benchmark                                       (size)  Mode  Cnt      Score     Error  Units
PositiveExpMathVsFastMathBenchmark.fastMathExp       1  avgt   15     13.263 ±   0.046  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp      10  avgt   15    120.013 ±   0.716  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp     100  avgt   15   1196.223 ±  21.637  ns/op
PositiveExpMathVsFastMathBenchmark.fastMathExp    1000  avgt   15   9747.397 ±  24.198  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp           1  avgt   15      6.982 ±   0.225  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp          10  avgt   15     94.861 ±   2.076  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp         100  avgt   15    972.328 ±  25.959  ns/op
PositiveExpMathVsFastMathBenchmark.mathExp        1000  avgt   15  10851.855 ± 131.236  ns/op
*/

}
