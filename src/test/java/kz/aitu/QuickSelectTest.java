package kz.aitu;

import kz.damir.Metrics;
import kz.damir.QuickSelect;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSelectTest {

    @Test
    void selectsFromRandomArrays() {
        Random random = new Random(42);

        for (int test = 0; test < 100; test++) {
            int size = 1 + random.nextInt(1000);
            int[] original = new int[size];

            for (int i = 0; i < size; i++) {
                original[i] = random.nextInt(2001) - 1000;
            }

            int[] sorted = original.clone();
            Arrays.sort(sorted);

            int[] positions = {
                    0,
                    size / 2,
                    size - 1,
                    random.nextInt(size)
            };

            for (int k : positions) {
                int result = QuickSelect.select(
                        original.clone(), k, new Metrics()
                );

                assertEquals(sorted[k], result,
                        "Wrong result for k = " + k);
            }
        }
    }

    @Test
    void handlesSingleElement() {
        int result = QuickSelect.select(new int[]{7}, 0);

        assertEquals(7, result);
    }

    @Test
    void handlesEqualElements() {
        int[] numbers = new int[100];
        Arrays.fill(numbers, 5);

        int result = QuickSelect.select(numbers, 50);

        assertEquals(5, result);
    }

    @Test
    void handlesSortedArray() {
        int[] numbers = new int[100];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        assertEquals(37, QuickSelect.select(numbers, 37));
    }

    @Test
    void rejectsEmptyArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0)
        );
    }

    @Test
    void rejectsNegativeK() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{3, 1, 2}, -1)
        );
    }

    @Test
    void rejectsKEqualToLength() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{3, 1, 2}, 3)
        );
    }

    @Test
    void rejectsNullArray() {
        assertThrows(
                IllegalArgumentException.class,
                () -> QuickSelect.select(null, 0)
        );
    }
}