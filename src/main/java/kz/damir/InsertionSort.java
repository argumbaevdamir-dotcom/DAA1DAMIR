package kz.damir;

public class InsertionSort {

    public static void sort(int[] a, int left, int right,
                            Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;

            while (j >= left) {
                metrics.countComparison();

                if (a[j] <= key) {
                    break;
                }

                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = key;
        }
    }
}