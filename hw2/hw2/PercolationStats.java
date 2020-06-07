package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;
    private double[] threshold;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        threshold = new double[T]; //stores
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int index = StdRandom.uniform(N * N);
                int row = index / N;
                int col = index % N;
                p.open(row, col);
            }
            threshold[i] = (double) p.numberOfOpenSites() / (N * N);
        }
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
        confidenceLow = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHigh = mean + 1.96 * stddev / Math.sqrt(T);
    }

    public double mean() { // sample mean of percolation threshold
        return mean;
    }

    public double stddev() { // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLow() { // low endpoint of 95% confidence interval
        return confidenceLow;
    }

    public double confidenceHigh() { // high endpoint of 95% confidence interval
        return confidenceHigh;
    }

    /*
    public static void main(String[] args) {
        PercolationStats p = new PercolationStats(50, 100, new PercolationFactory());
        StdOut.println(p.mean());
        StdOut.println(p.stddev());
        StdOut.println(p.confidenceLow());
        StdOut.println(p.confidenceHigh());
        StdOut.println(p.threshold[99]);
    }
*/
}
