import edu.princeton.cs.algs4.Queue;

public class Board {

    private static final int SPACE = 0;
    private final int[][] mtiles;
    private int mN;

    public Board(int[][] blocks) {
        mN = blocks.length;
        mtiles = blocks;
    }

    private int[][] copy(int[][] blocks) {
        int[][] tiles = new int[mN][mN];
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++) {
                tiles[i][j] = blocks[i][j];
            }
        return tiles;
    }

    private int valueof(int i, int j) {
        return (i * mN + j) + 1;
    }

    private boolean isSpace(int i, int j) {
        return this.mtiles[i][j] == SPACE;
    }

    public int dimension() {
        return mN;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++) {
                if (isSpace(i, j))
                    continue;
                if (mtiles[i][j] != valueof(i, j))
                    hamming++;
            }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++) {
                int v = valueof(i, j);
                if (isSpace(i, j) || mtiles[i][j] == v)
                    continue;
                int row = row(mtiles[i][j]);
                int col = col(mtiles[i][j]);
                manhattan += Math.abs(row - i) + Math.abs(col - j);
            }
        return manhattan;
    }

    private int row(int v) {
        return (v - 1) / mN;
    }

    private int col(int v) {
        return (v - 1) % mN;
    }

    public boolean isGoal() {
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++) {
                if (mtiles[i][j] != (valueof(i, j)) % (mN * mN))
                    return false;
            }
        return true;
    }

    public Board twin() {
        int[][] twintiles = copy(this.mtiles);
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN - 1; j++) {
                if (!isSpace(i, j) && !isSpace(i, j + 1)) {
                    exch(twintiles, i, j, i, j + 1);
                    return new Board(twintiles);
                }
            }
        return null;
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (this.mN != that.mN)
            return false;
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++)
                if (this.mtiles[i][j] != that.mtiles[i][j])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<>();
        for (int i = 0; i < mN; i++)
            for (int j = 0; j < mN; j++) {
                if (isSpace(i, j)) {
                    if (inRange(i - 1, j))
                        neighbors.enqueue(new Board(this.exchAndBuild(mtiles, i, j, i - 1, j)));
                    if (inRange(i, j + 1))
                        neighbors.enqueue(new Board(this.exchAndBuild(mtiles, i, j, i, j + 1)));
                    if (inRange(i + 1, j))
                        neighbors.enqueue(new Board(this.exchAndBuild(mtiles, i, j, i + 1, j)));
                    if (inRange(i, j - 1))
                        neighbors.enqueue(new Board(this.exchAndBuild(mtiles, i, j, i, j - 1)));
                    break;
                }
            }
        return neighbors;
    }

    private boolean inRange(int i, int j) {
        return (i >= 0 && i < mN) && (j >= 0 && j < mN);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(mN + "\n");
        for (int i = 0; i < mN; i++) {
            for (int j = 0; j < mN; j++) {
                s.append(String.format("%2d ", mtiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private static void exch(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    private int[][] exchAndBuild(int[][] o1, int i1, int j1, int i2, int j2) {
        int[][] o2 = copy(o1);
        int temp = o2[i1][j1];
        o2[i1][j1] = o2[i2][j2];
        o2[i2][j2] = temp;
        return o2;
    }
}
