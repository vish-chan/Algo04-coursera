package Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    
    private int mNumber;
    private Node mPre = new Node(), mPost = new Node();
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    
    public Deque() {
        // TODO Auto-generated constructor stub
        this.mNumber = 0;
        this.mPre.next = this.mPost;
        this.mPost.prev = this.mPre;
        
    }
    
    public int size() {
        return this.mNumber;
    }
    
    public boolean isEmpty() {
        return this.mNumber==0;
    }
    
    public void addFirst(Item theItem) {
        if (theItem == null)
            throw new java.lang.NullPointerException();
        Node oldfirst = this.mPre.next;
        Node first = new Node();
        first.item = theItem;
        first.next = oldfirst;
        first.prev = this.mPre;
        oldfirst.prev = first;
        this.mPre.next = first;
        this.mNumber++;
    }
    
    public void addLast(Item theItem) {
        Node oldLast = this.mPost.prev;
        Node last = new Node();
        last.item = theItem;
        last.next = this.mPost;
        last.prev = oldLast;
        oldLast.next = last;
        this.mPost.prev = last;
        this.mNumber++;
    }
    
    public Item removeFirst() {
        if (this.isEmpty())
            throw new NoSuchElementException("Empty queue");
        Node oldfirst = this.mPre.next;
        Node first = oldfirst.next;
        Item item = oldfirst.item;
        this.mPre.next = first;
        first.prev = this.mPre;
        mNumber--;
        oldfirst = null;
        return item;
    }
    
    public Item removeLast() {
        if (this.isEmpty())
            throw new NoSuchElementException("Empty queue");
        Node oldLast = this.mPost.prev;
        Node last = oldLast.prev;
        Item item = oldLast.item;
        last.next = this.mPost;
        this.mPost.prev = last;
        mNumber--;
        oldLast = null;
        return item;
    }
   
    @Override
    public Iterator<Item> iterator() {
        // TODO Auto-generated method stub
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = mPre.next;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            return (current!=mPost);
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = this.current.item;
            current = current.next;
            return item;
        }
        
        public void remove() {
            throw new  java.lang.UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<Integer>();
        for (int i=0;i<10;i++)
            dq.addLast(i);
        for (int i = 0;i<10;i++)
            StdOut.println(dq.removeFirst()+"\n");
        Iterator<Integer> iter = dq.iterator();
        while(iter.hasNext()) {
            StdOut.println(iter.next() + "\n");
        }
    }

}
