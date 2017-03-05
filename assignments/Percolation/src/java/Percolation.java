import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {
    private final WeightedQuickUnionUF withTopBottomVirtualSites;
    private final WeightedQuickUnionUF withTopVirtualSite;
    private final int size;
    private final int virtualTopSiteIndex;
    private final int virtualBottomSiteIndex;
    private boolean[] openStatus;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        final int gridPlus2VirtualSites = size * size + 2;
        withTopBottomVirtualSites = new WeightedQuickUnionUF(gridPlus2VirtualSites);
        withTopVirtualSite = new WeightedQuickUnionUF(gridPlus2VirtualSites);
        openStatus = new boolean[gridPlus2VirtualSites];
        Arrays.fill(openStatus, false);
        numberOfOpenSites = 0;
        virtualTopSiteIndex = size * size;
        virtualBottomSiteIndex = size * size + 1;

        for (int i = 0; i < size; i++) {
            withTopBottomVirtualSites.union(virtualTopSiteIndex, i);
            withTopBottomVirtualSites.union(virtualBottomSiteIndex, index(size, i + 1));
            withTopVirtualSite.union(virtualTopSiteIndex, i);
        }
    }

    public void open(int row, int col) {
        checkSitePosition(row, col);
        if (isOpen(row, col)) return;

        final int index = index(row, col);

        openStatus[index] = true;
        numberOfOpenSites++;

        if (row > 1)
            connectIfBothSitesAreOpen(index - size, index);
        if (row < size)
            connectIfBothSitesAreOpen(index + size, index);
        if (col > 1)
            connectIfBothSitesAreOpen(index - 1, index);
        if (col < size)
            connectIfBothSitesAreOpen(index + 1, index);

    }

    private void connectIfBothSitesAreOpen(int site1, int site2) {
        if (openStatus[site1] && openStatus[site2]) {
            withTopBottomVirtualSites.union(site1, site2);
            withTopVirtualSite.union(site1, site2);
        }

    }

    public boolean isOpen(int row, int col) {
        checkSitePosition(row, col);
        return openStatus[index(row, col)];
    }

    private void checkSitePosition(int row, int col) {
        if (row <= 0 || row > size) throw new ArrayIndexOutOfBoundsException();
        if (col <= 0 || col > size) throw new ArrayIndexOutOfBoundsException();
    }

    public boolean percolates() {
//        for (int i = 1; i <= size; i++) {
//             if(isOpen(size, i) && withTopBottomVirtualSites.connected(virtualTopSiteIndex, index(size, i))) return true;
//        }
//        return false;
        return withTopBottomVirtualSites.connected(virtualTopSiteIndex, virtualBottomSiteIndex);
    }

    public boolean isFull(int row, int col) {
        checkSitePosition(row, col);
        if (!isOpen(row, col)) return false;

        return row == 1 || withTopVirtualSite.connected(index(row, col), virtualTopSiteIndex);

    }


    private int index(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

}