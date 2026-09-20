# DAA Assignment 1: Divide and Conquer

Java implementations of MergeSort, QuickSort, and QuickSelect
with tests and performance measurements.

## Requirements

- JDK 25
- Apache Maven
- Python 3 and matplotlib, only for regenerating plots

## Algorithms

- MergeSort: reusable auxiliary array and insertion-sort cutoff of 15.
- QuickSort: random pivot, three-way partitioning, recursion on
  the smaller partition, and a loop for the larger partition.
- QuickSelect: iterative selection using shared partitioning.
- Metrics: element comparisons, maximum call depth, and execution time.

QuickSelect uses a zero-based index: k = 0 selects the smallest element.

## Build

Run commands from the project directory containing pom.xml.

```bash
mvn clean compile
```

## Run Tests

```bash
mvn test
```

The tests check sorting against Arrays.sort, edge cases,
QuickSort call depth, and QuickSelect results.

## Run Benchmark

```bash
mvn compile exec:java
```

This runs kz.damir.Benchmark and writes results.csv.

The benchmark includes:
- Sizes: 1,000; 10,000; 100,000; 1,000,000
- Inputs: random, sorted, and duplicates
- Two warm-up runs and five measured runs per case
- Median execution time, with comparisons and depth from that run

Running the benchmark again overwrites results.csv.
Timings and randomized pivot results may differ between runs.

## Generate Plots

The generated PNG plots are already included in plots/.

To regenerate them, install matplotlib and run:

```bash
python -m pip install matplotlib
python plot_results.py
```

The script reads results.csv and updates the three images in plots/.

## Project Files

- src/main/java/kz/aitu/ — algorithms, metrics, and benchmark
- src/test/java/kz/aitu/ — JUnit tests
- results.csv — benchmark measurements
- plots/ — benchmark graphs
- plot_results.py — plotting script
- REPORT.md — theoretical analysis and experimental results
- pom.xml — Maven configuration