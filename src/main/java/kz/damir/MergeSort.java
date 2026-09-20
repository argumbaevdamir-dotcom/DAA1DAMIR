package kz.damir;

public class MergeSort {

    private static final int  = 15;

    public static void sort(int[] a, Metrics metrics) {
        if (a.length == 0) {
            return;
        }

        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, metrics, 1);
    }

    private static void sort(int[] a, int[] buffer,
                             int left, int right,
                             Metrics metrics, int depth) {
        metrics.recordDepth(depth);

        if (right - left + 1 <= CUTOFF) {
            InsertionSort.sort(a, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        sort(a, buffer, left, mid, metrics, depth + 1);
        sort(a, buffer, mid + 1, right, metrics, depth + 1);

        merge(a, buffer, left, mid, right, metrics);
    }

    private static void merge(int[] a, int[] buffer,
                              int left, int mid, int right,
                              Metrics metrics) {
        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.countComparison();

            if (buffer[i] <= buffer[j]) {
                a[k] = buffer[i];
                i++;
            } else {
                a[k] = buffer[j];
                j++;
            }

            k++;
        }

        while (i <= mid) {
            a[k] = buffer[i];
            i++;
            k++;
        }

        while (j <= right) {
            a[k] = buffer[j];
            j++;
            k++;
        }
    }
}