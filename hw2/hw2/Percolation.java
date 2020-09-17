package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] sites;
    private WeightedQuickUnionUF qunion;
    private WeightedQuickUnionUF connectunion;
    private int superSource;
    private int superDes;
    private int size = 0;
    private int n;
    /* create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = N;
        sites = new boolean[N][N];
        for (boolean[] ints : sites) {
            for (boolean i : ints) {
                i = false;
            }
        }

        qunion = new WeightedQuickUnionUF(N * N + 2);
        connectunion = new WeightedQuickUnionUF(N * N + 2);
        superSource = N * N;
        superDes = N * N + 1;

    }

    private int xyTo1D(int row, int col) {
        return row * sites[0].length + col;
    }

    /* open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {

            if (!sites[row][col]) {
                sites[row][col] = true;
                size += 1;
            }

            if (row == 0) {
                qunion.union(superSource, xyTo1D(row, col));
                connectunion.union(superSource, xyTo1D(row, col));
            }
            if (row == n - 1) {
                connectunion.union(superDes, xyTo1D(row, col));
            }

            if (row - 1 >= 0 && sites[row - 1][col] == true) {
                qunion.union(xyTo1D(row - 1, col), xyTo1D(row, col));
                connectunion.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            }
            if (row <= n - 2 && sites[row + 1][col] == true) {
                qunion.union(xyTo1D(row + 1, col), xyTo1D(row, col));
                connectunion.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            }
            if (col - 1 >= 0 && sites[row][col - 1] == true) {
                qunion.union(xyTo1D(row, col - 1), xyTo1D(row, col));
                connectunion.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            }
            if (col <= n - 2 && sites[row][col + 1] == true) {
                qunion.union(xyTo1D(row, col + 1), xyTo1D(row, col));
                connectunion.union(xyTo1D(row, col + 1), xyTo1D(row, col));
            }
        }
    }

    /* is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        int N = sites[0].length;
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return sites[row][col];
        }
    }

    /* is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        int N = sites[0].length;
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        } else {
            return qunion.connected(xyTo1D(row, col), superSource);
        }
    }

    /* number of open sites */
    public int numberOfOpenSites() {
        return size;
    }

    /* does the system percolate? */
    public boolean percolates() {
        return connectunion.connected(superSource, superDes);
    }

    /* use for unit testing (not required) */
    public static void main(String[] args) {
        Percolation per = new Percolation(6);
        per.open(0, 5);
        System.out.println(per.isFull(0, 5));
        System.out.println(per.numberOfOpenSites());
        System.out.println(per.percolates());

        per.open(4, 4);
        System.out.println(per.percolates());
        System.out.println(per.isFull(2, 2));

    }

}
