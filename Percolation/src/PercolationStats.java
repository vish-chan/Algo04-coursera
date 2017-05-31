import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int mTrials;
    private int mN;
    private double[] mFractionOfOpenSites;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException("Wrong arguments");
        else {
            mN = n;
            mTrials = trials;
            mFractionOfOpenSites = new double[trials];
            for (int i = 0; i < mTrials; i++) {
                Percolation mP = new Percolation(mN);
                while (!mP.percolates()) {
                    openRandomSite(mP);
                }
                this.mFractionOfOpenSites[i] = (mP.numberOfOpenSites() * 1.0) / (mN * mN);
            }
        }
    }

    private void openRandomSite(Percolation theP) {
        int randRow = StdRandom.uniform(mN) + 1;
        int randCol = StdRandom.uniform(mN) + 1;
        theP.open(randRow, randCol);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mFractionOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(mFractionOfOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double sd = this.stddev();
        return (mean - (1.96 * sd) / Math.sqrt(mTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double sd = this.stddev();
        return (mean + (1.96 * sd) / Math.sqrt(mTrials));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new java.lang.IllegalArgumentException("Less arguments");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats mPS = new PercolationStats(n, t);

        System.out.println("mean = " + mPS.mean());
        System.out.println("stdev = " + mPS.stddev());
        System.out.println("95% confidence interval = [" + mPS.confidenceLo() + "," + mPS.confidenceHi() + "]");
    }
}
