import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static boolean sDEBUG = false;
    private WeightedQuickUnionUF mUFGrid = null;
    private WeightedQuickUnionUF mUFGridForFind = null;
    private int[][] mGrid = null;
    private int mSize;
    private int mNumOfOpenSites;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Illegal Grid Size");
        this.mSize = n;
        this.mNumOfOpenSites = 0;
        int gridSize = n * n;
        this.mUFGrid = new WeightedQuickUnionUF(gridSize + 2); // virtual top
                                                               // and bottom
        this.mUFGridForFind = new WeightedQuickUnionUF(gridSize + 1); // virtual
                                                                      // top
        this.mGrid = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.mGrid[i][j] = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            Percolation.sAddDebugPrint("Open failed: Already open");
            return;
        }
        int n = this.mSize;
        int index = calcIndex(row, col);
        int prevRow = row - 1;
        int nextRow = row + 1;
        int prevCol = col - 1;
        int nextCol = col + 1;
        this.mGrid[row - 1][col - 1] = 1;
        Percolation.sAddDebugPrint("Opened: " + row + "," + col);
        if (prevRow != 0) {
            if (isOpen(prevRow, col)) {
                mUFGrid.union(calcIndex(prevRow, col), index);
                mUFGridForFind.union(calcIndex(prevRow, col), index);
                Percolation.sAddDebugPrint("Union: " + "[" + prevRow + "," + col + "] and [" + row + "," + col + "]");
            }
        }
        if (nextRow != (n + 1)) {
            if (isOpen(nextRow, col)) {
                mUFGrid.union(calcIndex(nextRow, col), index);
                mUFGridForFind.union(calcIndex(nextRow, col), index);
                Percolation.sAddDebugPrint("Union: " + "[" + nextRow + "," + col + "] and [" + row + "," + col + "]");
            }
        }
        if (prevCol != 0) {
            if (isOpen(row, prevCol)) {
                mUFGrid.union(calcIndex(row, prevCol), index);
                mUFGridForFind.union(calcIndex(row, prevCol), index);
                Percolation.sAddDebugPrint("Union: " + "[" + row + "," + prevCol + "] and [" + row + "," + col + "]");
            }
        }
        if (nextCol != (n + 1)) {
            if (isOpen(row, nextCol)) {
                mUFGrid.union(calcIndex(row, nextCol), index);
                mUFGridForFind.union(calcIndex(row, nextCol), index);
                Percolation.sAddDebugPrint("Union: " + "[" + row + "," + nextCol + "] and [" + row + "," + col + "]");
            }
        }
        if (row == 1) {
            mUFGrid.union(0, index);
            mUFGridForFind.union(0, index);
            Percolation.sAddDebugPrint("Union with Virtual TOP");
        }
        if (row == n) {
            mUFGrid.union(n * n + 1, index);
            Percolation.sAddDebugPrint("Union with Virtual BOTTOM");
        }
        mNumOfOpenSites += 1;
    }

    private static void sAddDebugPrint(String line) {
        if (sDEBUG)
            System.out.println(line);
    }

    private int calcIndex(int row, int col) {
        return (row - 1) * mSize + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > this.mSize) || (col < 1 || col > this.mSize))
            throw new java.lang.IndexOutOfBoundsException("Index out of bound");
        else {
            return (mGrid[row - 1][col - 1] == 1);
        }
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col))
            return false;
        return mUFGridForFind.connected(0, calcIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.mNumOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int n = this.mSize;
        return (mUFGrid.connected(0, n * n + 1));
    }

    // TEST CODE for TEST INPUTS
    public static void main(String[] args) {
        runAllTestInputs("percolation");
        //runOneTestInput("percolation/input3.txt");
        //runCustomTest("percolation/input3.txt", 4);
    }

    private static void runAllTestInputs(String dirname) {
        File dir = new File(dirname);
        File[] filelist = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });
        if (filelist != null) {
            for (int i = 0; i < filelist.length; i++)
                runOneTestInput("percolation/" + filelist[i].getName());
        }
    }

    private static void runOneTestInput(String filepath) {
        Percolation mP = null;
        File file = new File(filepath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            text = reader.readLine();
            if (text != null) {
                int n = Integer.parseInt(text);
                mP = new Percolation(n);
                String[] site = new String[2];
                while ((text = reader.readLine()) != null) {
                    text = text.trim().replaceAll(" +", " ");
                    site = text.split(" ");
                    if (site.length == 2) {
                        mP.open(Integer.parseInt(site[0]), Integer.parseInt(site[1]));
                    }
                }
            }
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } 
            catch (IOException e) {
            }
        }
        if (mP != null) {
            if (mP.percolates())
                System.out.println(filepath + "    Percolates");
            else
                System.out.println(filepath + "      Doesn't Percolate");
        }
    }

    private static void runCustomTest(String filepath, int numcommands) {
        Percolation mP = null;
        File file = new File(filepath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            text = reader.readLine();
            if (text != null) {
                int n = Integer.parseInt(text);
                mP = new Percolation(n);
                String[] site = new String[2];
                while (((text = reader.readLine()) != null) && numcommands > 0) {
                    text = text.trim().replaceAll(" +", " ");
                    site = text.split(" ");
                    if (site.length == 2) {
                        mP.open(Integer.parseInt(site[0]), Integer.parseInt(site[1]));
                        numcommands--;
                    }
                }
            }
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } 
            catch (IOException e) {
            }
        }
        if (mP != null) {
            if (mP.isFull(3, 1))
                System.out.println("ISFULL");
            else
                System.out.println("NOT ISFULL");
        }
    }
}