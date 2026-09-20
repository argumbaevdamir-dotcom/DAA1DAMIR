package kz.damir;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            1_000, 10_000, 100_000, 1_000_000
    };

    private static final String[] INPUT_TYPES = {
            "random", "sorted", "duplicates"
    };

    private static final String[] ALGORITHMS = {
            "MergeSort", "QuickSort", "QuickSelect"
    };

    private static final int RUNS = 5;
    private static final int WARMUP_RUNS = 2;

    public static void main(String[] args) throws IOException {
        Path output = Path.of("results.csv");

        try (PrintWriter writer = new PrintWriter(
                Files.newBufferedWriter(output, StandardCharsets.UTF_8))) {

            writer.println(
                    "algorithm,input,n,time_ms,comparisons,max_depth"
            );

            for (int n : SIZES) {
                for (String input : INPUT_TYPES) {
                    int[] original = createInput(n, input);

                    int[] expected = original.clone();
                    Arrays.sort(expected);

                    for (String algorithm : ALGORITHMS) {

                        for (int run = 0; run < WARMUP_RUNS; run++) {
                            measure(algorithm, original, expected);
                        }

                        Metrics[] measurements = new Metrics[RUNS];

                        for (int run = 0; run < RUNS; run++) {
                            measurements[run] = measure(
                                    algorithm, original, expected
                            );
                        }

                        Arrays.sort(
                                measurements,
                                Comparator.comparingDouble(
                                        Metrics::getTimeMillis
                                )
                        );

                        Metrics median = measurements[RUNS / 2];

                        writer.printf(
                                Locale.US,
                                "%s,%s,%d,%.6f,%d,%d%n",
                                algorithm,
                                input,
                                n,
                                median.getTimeMillis(),
                                median.getComparisons(),
                                median.getMaxDepth()
                        );

                        System.out.printf(
                                Locale.US,
                                "%s | %s | n=%d | %.3f ms%n",
                                algorithm,
                                input,
                                n,
                                median.getTimeMillis()
                        );
                    }
                }
            }

            if (writer.checkError()) {
                throw new IOException("Failed to write results.csv");
            }
        }

        System.out.println("Saved to: " + output.toAbsolutePath());
    }

    private static int[] createInput(int n, String input) {
        int[] numbers = new int[n];
        Random random = new Random(42L + n);

        for (int i = 0; i < n; i++) {
            switch (input) {
                case "random":
                    numbers[i] = random.nextInt();
                    break;

                case "sorted":
                    numbers[i] = i;
                    break;

                case "duplicates":
                    numbers[i] = random.nextInt(10);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unknown input type: " + input
                    );
            }
        }

        return numbers;
    }

    private static Metrics measure(String algorithm,
                                   int[] original,
                                   int[] expected) {
        int[] numbers = original.clone();
        Metrics metrics = new Metrics();

        int k = numbers.length / 2;
        int selected = 0;

        long start = System.nanoTime();

        switch (algorithm) {
            case "MergeSort":
                MergeSort.sort(numbers, metrics);
                break;

            case "QuickSort":
                QuickSort.sort(numbers, metrics);
                break;

            case "QuickSelect":
                selected = QuickSelect.select(numbers, k, metrics);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown algorithm: " + algorithm
                );
        }

        metrics.setElapsedNanos(System.nanoTime() - start);

        if (algorithm.equals("QuickSelect")) {
            if (selected != expected[k]) {
                throw new IllegalStateException(
                        "QuickSelect returned an incorrect result"
                );
            }
        } else if (!Arrays.equals(expected, numbers)) {
            throw new IllegalStateException(
                    algorithm + " returned an incorrectly sorted array"
            );
        }

        return metrics;
    }
}