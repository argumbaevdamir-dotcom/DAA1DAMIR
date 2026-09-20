package kz.damir;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] a, Metrics metrics) {
        if (a.length == 0) {
            return;
        }

        sort(a, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int left, int right,
                             Metrics metrics, int depth) {
        metrics.recordDepth(depth);

        while (left < right) {
            int[] bounds = partition(a, left, right, metrics);

            int lt = bounds[0];
            int gt = bounds[1];

            int leftSize = lt - left;
            int rightSize = right - gt;

            if (leftSize < rightSize) {
                if (leftSize > 1) {
                    sort(a, left, lt - 1, metrics, depth + 1);
                }

                left = gt + 1;
            } else {
                if (rightSize > 1) {
                    sort(a, gt + 1, right, metrics, depth + 1);
                }

                right = lt - 1;
            }
        }
    }

    static int[] partition(int[] a, int left, int right,
                           Metrics metrics) {
        int pivotIndex = ThreadLocalRandom.current()
                .nextInt(left, right + 1);

        int pivot = a[pivotIndex];

        int lt = left;
        int i = left;
        int gt = right;

        while (i <= gt) {
            metrics.countComparison();

            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else {
                metrics.countComparison();

                if (a[i] > pivot) {
                    swap(a, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }
        }

        return new int[]{lt, gt};
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}