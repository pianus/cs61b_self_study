package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;


public class Percolation {
    private int N;
    private int size; //total number of grids
    private WeightedQuickUnionUF m; //stores grids that are clusted
    private WeightedQuickUnionUF o; //stores grids that are open
    private WeightedQuickUnionUF f; //stores grids that area full
    private WeightedQuickUnionUF b; //stores grids that are "bottom full" (connected to the bottom line)
    private int numberOfOpenSites = 0;
    private boolean percolates = false;

    public Percolation(int N) {                // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            this.N = N;
            size = N * N;
            m = new WeightedQuickUnionUF(size);
            o = new WeightedQuickUnionUF(size + 1); //anything added to index N*N is opened
            f = new WeightedQuickUnionUF(size + 1); //anything added to index N*N is full
            b = new WeightedQuickUnionUF(size + 1); //anything added to index N*N is "bottom full"
        }
    }

    public void open(int row, int col) {      // open the site (row, col) if it is not open already
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        //find left, right, up, and down grid indexes (ignore edge condition)
        List<Integer> neighborIndexes = new ArrayList<Integer>();
        //find all exist neighbor grids
        if (inGrid(row - 1, col)) {
            neighborIndexes.add(findIndex(row - 1, col));
        }
        if (inGrid(row + 1, col)) {
            neighborIndexes.add(findIndex(row + 1, col));
        }
        if (inGrid(row, col - 1)) {
            neighborIndexes.add(findIndex(row, col - 1));
        }
        if (inGrid(row, col + 1)) {
            neighborIndexes.add(findIndex(row, col + 1));
        }


        int selfIndex = findIndex(row, col);
        //compute open sites
        //&& 1. operation to o: add the grid to the open group
        if (!o.connected(selfIndex, size)) {
            numberOfOpenSites += 1;
            o.union(selfIndex, size);
        }

        //2. if itself is the first row, add itself to full group
        if (selfIndex < N) {
            f.union(selfIndex, size);
        }

        //3. symmetric operation to b similar to f
        if (selfIndex >= N * (N - 1)) {
            b.union(selfIndex, size);
        }

        for (int i = 0; i < neighborIndexes.size(); i += 1) {
            //for each neighbor
            //1. operations to m: if this neighbor is also open, cluster them and note it in neighborOpen
            if (o.connected(neighborIndexes.get(i), size)) {
                m.union(selfIndex, neighborIndexes.get(i));
                o.union(selfIndex, neighborIndexes.get(i));
                f.union(selfIndex, neighborIndexes.get(i));
                b.union(selfIndex, neighborIndexes.get(i));
            }

            //2. operation to f: if the neighbor is full, add the self to the
            //     full group (with the clusted group)
            if (f.connected(neighborIndexes.get(i), size)) {
                f.union(selfIndex, neighborIndexes.get(i));
            }

            //3. symmetric operation as to f: operation to b: if the neighbor
            //   is "bottom full", add self to the "bottom full" group
            if (b.connected(neighborIndexes.get(i), size)) {
                b.union(selfIndex, neighborIndexes.get(i));
            }
        }


        if (f.connected(selfIndex, size) && b.connected(selfIndex, size)) {
            percolates = true;
        }

    }

    public boolean isOpen(int row, int col) { // is the site (row, col) open?
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return o.connected(findIndex(row, col), size);
    }

    public boolean isFull(int row, int col) { // is the site (row, col) full?
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return f.connected(findIndex(row,col), size);
    }

    public int numberOfOpenSites() {          // number of open sites (omega(1))
        return numberOfOpenSites;
    }

    public boolean percolates() {             // does the system percolate?
        return percolates;
    }

    public static void main(String[] args) {  // use for unit testing (not required)

    }

    private int findIndex(int row, int col) {
        if (!inGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return N * row + col;
    }

    private boolean inGrid(int row, int col) {
        if (row >= N || row < 0 || col >= N || col < 0) {
            return false;
        }
        return true;
    }
}
