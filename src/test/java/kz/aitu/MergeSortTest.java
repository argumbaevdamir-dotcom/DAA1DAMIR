package kz.aitu;

import kz.damir.MergeSort;
import kz.damir.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeSortTest {

    @Test
    void sortsRandomArrays() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int size = 16 + random.nextInt(985);
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
        int[] actual = new int[100];
        Arrays.fill(actual, 5);

        checkSort(actual);
    }

    @Test
    void handlesSortedArray() {
        int[] actual = new int[100];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = i;
        }

        checkSort(actual);
    }

    @Test
    void handlesCutoffBoundary() {
        for (int size : new int[]{14, 15, 16}) {
            int[] actual = new int[size];

            for (int i = 0; i < size; i++) {
                actual[i] = size - i;
            }

            checkSort(actual);
        }
    }

    private void checkSort(int[] actual) {
        int[] expected = actual.clone();

        Arrays.sort(expected);
        MergeSort.sort(actual, new Metrics());

        assertArrayEquals(expected, actual);
    }
}