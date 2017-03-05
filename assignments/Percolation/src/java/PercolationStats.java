import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;


public class PercolationStats {
    private int gridSize;
    private final int runs;
    private double[] thresholdPct;

    public PercolationStats(int size, int runs) {
        if (size <= 0 || runs <= 0) throw new IllegalArgumentException("size and runs must be positive integers");
        this.gridSize = size;
        this.runs = runs;
        thresholdPct = new double[runs];
        Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < runs; i++) {
            thresholdPct[i] = openSiteOneAtATimeTillPercolation(size);
        }
        final double t = stopwatch.elapsedTime();
//        showTime(size, runs, t);
    }

    private void showTime(int size, int runs, double t) {
        System.out.println(String.format("%s runs for size %s took %s seconds", runs, size, t));
        System.out.println(String.format("average run time is %s seconds", t / runs));
    }

    public double mean() {
        return StdStats.mean(thresholdPct);
    }

    public double stddev() {
        return StdStats.stddev(thresholdPct);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(runs);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(runs);
    }

    private double openSiteOneAtATimeTillPercolation(int gridSize) {
        final Percolation p = new Percolation(gridSize);
        final double numberOfSites = gridSize * gridSize;

        List<Integer> closedSites = new ArrayList<>();
        for (int i = 0; i < numberOfSites; i++) {
            closedSites.add(i);
        }

        while (!p.percolates()) {
            openSiteRandomly(p, closedSites);
        }

        return p.numberOfOpenSites() / (numberOfSites);

    }

    private void openSiteRandomly(Percolation p, List<Integer> closedSites) {
        final int uniform = StdRandom.uniform(closedSites.size());
        final int siteToOpen = closedSites.get(uniform);
        p.open(siteToOpen / gridSize + 1, siteToOpen % gridSize + 1);
        closedSites.remove(uniform);
    }

    public static void main(String[] args) {
        if (args.length != 2) throw new IllegalArgumentException("missing size or run number");

        final int size = Integer.parseInt(args[0]);
        final int runs = Integer.parseInt(args[1]);

        final PercolationStats stats = new PercolationStats(size, runs);
//        showStats(stats);
    }

    private static void showStats(PercolationStats stats) {
        System.out.println(String.format("mean:            %s", stats.mean()));
        System.out.println(String.format("stddev:          %s", stats.stddev()));
        System.out.println(String.format("confidenceLo:    %s", stats.confidenceLo()));
        System.out.println(String.format("confidenceHi:    %s", stats.confidenceHi()));
    }
}