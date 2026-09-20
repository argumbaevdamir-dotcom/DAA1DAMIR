package kz.damir;

public class Metrics {

    private long comparisons;
    private int maxDepth;
    private long elapsedNanos;

    public void countComparison() {
        comparisons++;
    }

    public void recordDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public void setElapsedNanos(long elapsedNanos) {
        this.elapsedNanos = elapsedNanos;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getTimeMillis() {
        return elapsedNanos / 1_000_000.0;
    }
}