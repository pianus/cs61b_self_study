package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdOut;


public class PercolationStats {
    double mean;
    double stddev;
    double confidenceLow;
    double confidenceHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {// perform T independent experiments on an N-by-N grid
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }

        double[] threshold = new double[T]; //stores
        for (int i=0; i<T; i+=1) {
            Percolation p = pf.make(N);
            while(!p.percolates()) {
                int index = StdRandom.uniform(N * N);
                int row = index / N;
                int col = index % N;
                p.open(row, col);
            }
            threshold[i] = (double)p.numberOfOpenSites / (N*N);
        }
        mean = StdStats.mean(threshold);
        stddev = StdStats.stddev(threshold);
        confidenceLow = mean - 1.96*stddev/Math.sqrt(T);
        confidenceHigh = mean + 1.96*stddev/Math.sqrt(T);
    }

    public double mean() { // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {// sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLow() {// low endpoint of 95% confidence interval
        return confidenceLow;
    }

    public double confidenceHigh() {// high endpoint of 95% confidence interval
        return confidenceHigh;
    }

    public static void main(String args[]) {
        PercolationStats p = new PercolationStats(30, 1000000, new PercolationFactory());
        StdOut.println(p.mean());
        StdOut.println(p.stddev());
        StdOut.println(p.confidenceLow());
        StdOut.println(p.confidenceHigh());

    }

}