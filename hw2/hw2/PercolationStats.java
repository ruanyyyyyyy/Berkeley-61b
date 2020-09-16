package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import java.lang.Math;

public class PercolationStats {

    int N, T;
    PercolationFactory pf;
    double[] perThres;

    /* perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("N and T should be larger than 0");
        }

        this.N = N;
        this.T = T;
        perThres = new double[T];
        Percolation per;

        for(int i = 0; i < T; i += 1) {
            per = pf.make(N);
            int row, col;
            while (!per.percolates()) {
                do {
                    row = StdRandom.uniform(N);
                    col = StdRandom.uniform(N);
                } while (per.isOpen(row, col));
                per.open(row, col);
            }

            if (per.percolates()) {
                perThres[i] = (double)per.numberOfOpenSites() / (N * N);
            }

        }

    }

    /* sample mean of percolation threshold */
    public double mean() {
        return StdStats.mean(perThres);
    }

    /* sample standard deviation of percolation threshold */
    public double stddev() {
        return StdStats.stddev(perThres);

    }

    /* low endpoint of 95% confidence interval */
    public double confidenceLow() {
        double mu = mean();
        double sigma = stddev();
        return mu - (1.96 * sigma) / Math.sqrt(T);
    }

    /* high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        double mu = mean();
        double sigma = stddev();
        return mu + (1.96 * sigma) / Math.sqrt(T);

    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats pls = new PercolationStats(20, 50, pf);
        System.out.println(pls.mean());
        System.out.println(pls.confidenceLow());
        System.out.println(pls.confidenceHigh());
    }


}
