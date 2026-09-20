package kz.aitu;

import kz.damir.Metrics;
import kz.damir.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    void sortsRandomArrays() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int size = 2 + random.nextInt(999);
            int[] actual = new int[size];

            for (int i = 0; i < size; i++) {
                actual[i] = random.nextInt(2001) - 1000;
            }

            checkSort(actual);
        }
    }

    @Test
    void handlesEmptyArray() {
        checkSort(new int[]{});
    }

    @Test
    void handlesSingleElement() {
        checkSort(new int[]{7});
    }

    @Test
    void handlesEqualElements() {
        int[] actual = new int[100_000];
        Arrays.fill(actual, 5);

        int[] expected = actual.clone();
        Metrics metrics = new Metrics();

        QuickSort.sort(actual, metrics);

        assertArrayEquals(expected, actual);
        assertEquals(1, metrics.getMaxDepth());
        assertEquals(2L * actual.length, metrics.getComparisons());
    }

    @Test
    void handlesManyDuplicates() {
        Random random = new Random(123);
        int[] actual = new int[10_000];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = random.nextInt(10);
        }

        checkSort(actual);
    }

    @Test
    void handlesReverseSortedArray() {
        int[] actual = new int[1_000];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = actual.length - i;
        }

        checkSort(actual);
    }

    @Test
    void keepsDepthBoundedOnSortedArray() {
        int n = 100_000;
        int[] actual = new int[n];

        for (int i = 0; i < n; i++) {
            actual[i] = i;
        }

        int[] expected = actual.clone();
        Metrics metrics = new Metrics();

        QuickSort.sort(actual, metrics);

        double depthLimit = 2 * (Math.log(n) / Math.log(2));

        assertArrayEquals(expected, actual);
        assertTrue(
                metrics.getMaxDepth() <= depthLimit,
                "Depth too large: " + metrics.getMaxDepth()
        );
    }

    private void checkSort(int[] actual) {
        int[] expected = actual.clone();

        Arrays.sort(expected);
        QuickSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }
}