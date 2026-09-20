package kz.damir;

import java.util.Arrays;

public class main {

    public static void main(String[] args) {
        int[] numbers = {7, 2, 9, 4};
        int k = 2;

        Metrics metrics = new Metrics();

        System.out.println("Before: " + Arrays.toString(numbers));

        long start = System.nanoTime();

        int result = QuickSelect.select(numbers, k, metrics);

        metrics.setElapsedNanos(System.nanoTime() - start);

        System.out.println("k: " + k);
        System.out.println("Selected element: " + result);
        System.out.println("Comparisons: " + metrics.getComparisons());
        System.out.println("Max depth: " + metrics.getMaxDepth());
        System.out.println("Time (ms): " + metrics.getTimeMillis());
    }
}