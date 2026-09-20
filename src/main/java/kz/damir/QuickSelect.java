package kz.damir;

public class QuickSelect {

    public static int select(int[] a, int k) {
        return select(a, k, new Metrics());
    }

    public static int select(int[] a, int k, Metrics metrics) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException(
                    "Array must not be null or empty"
            );
        }

        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k must be between 0 and " + (a.length - 1)
            );
        }

        int left = 0;
        int right = a.length - 1;

        // Iterative selection: one active algorithm level.
        metrics.recordDepth(1);

        while (left < right) {
            int[] bounds = QuickSort.partition(
                    a, left, right, metrics
            );

            int lt = bounds[0];
            int gt = bounds[1];

            if (k < lt) {
                right = lt - 1;
            } else if (k > gt) {
                left = gt + 1;
            } else {
                return a[k];
            }
        }

        return a[left];
    }
}