import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static boolean sDEBUG = false;
    private Item[] a;
    private int mNumber, mMax;

    public RandomizedQueue() {
        // TODO Auto-generated constructor stub
        this.a = (Item[]) new Object[2];
        this.mNumber = 0;
        this.mMax = 2;
    }

    public boolean isEmpty() {
        return (this.mNumber == 0);
    }

    public int size() {
        return this.mNumber;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (this.mNumber == this.mMax)
            this.resize(2 * this.mMax);
        a[mNumber++] = item;
        printDebug("Enque: " + item + " at idx " + (mNumber - 1) + ", N = " + mNumber + ", Max = " + mMax);
    }

    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int randidx = StdRandom.uniform(this.mNumber);
        Item item = this.a[randidx];
        this.a[randidx] = this.a[mNumber - 1];
        this.a[--mNumber] = null;
        if (mNumber > 0 && mNumber == mMax / 4)
            resize(mMax / 2);
        printDebug("Deque: " + item + " from idx " + randidx + ", N = " + mNumber + ", Max = " + mMax);
        return item;
    }

    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return a[StdRandom.uniform(mNumber)];
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < this.mNumber; i++)
            temp[i] = this.a[i];
        this.a = temp;
        printDebug("Resizing :" + mMax + " to " + capacity);
        this.mMax = capacity;
    }

    private void printDebug(String str) {
        if (sDEBUG)
            StdOut.println(str);
    }

    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int current;
        private Item[] randA;

        public RandomQueueIterator() {
            // TODO Auto-generated constructor stub
            this.current = 0;
            this.randA = (Item[]) new Object[mNumber];
            for (int i = 0; i < mNumber; i++)
                this.randA[i] = a[i];
            StdRandom.shuffle(randA);
        }

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return (current < mNumber);
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = randA[current++];
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++)
            rq.enqueue(i);
        Iterator<Integer> iter = rq.iterator();
        while (iter.hasNext())
            StdOut.print(iter.next() + "  ");
        StdOut.println();
        iter = rq.iterator();
        while (iter.hasNext())
            StdOut.print(iter.next() + "  ");
    }
}
