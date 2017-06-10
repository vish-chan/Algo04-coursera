
public class Heap extends Base{
    Comparable[] mHeap;
    int N;
    int Max;
    
    Heap(Comparable[] a, int size) {
        mHeap = a;
        N = a.length;
        Max = size;
        System.out.print("Input array: ");
        print(mHeap);
        heapify();
        System.out.print("heapified array: ");
        print(mHeap);
    }
    
    Heap(int size) {
        mHeap = new Comparable[size];
        N = 0;
        Max = size;
    }
    
    private void swim(int k) {
        while(k >= 1 && greater(mHeap, k, k/2)) {
            exch(mHeap, k, k/2);
            k = k/2;
        }
    }
    
    private void sink(int k) {
        int l = 2*k;
        while(l <= N) {
            if(l <= N-1 && less(mHeap, l, l+1))
                l++;
            if(greater(mHeap, l, k))
                exch(mHeap, k, l);
            k = l;
            l = 2*k;
        }
    }
    
    private void heapify() {
        for(int k = N/2; k>=1; k--)
            sink(k);
    }
    
    public void insert(Comparable a) {
        if(N==Max)
            throw new IndexOutOfBoundsException();
        mHeap[++N] = a;
        this.swim(N);
    }
    
    public Comparable delMax() {
        Comparable max = getElement(1);
        exch(mHeap, N, 1);
        this.sink(1);
        mHeap[N--] = null;
        return max;
    }
    
    public Comparable peekMax() {
        return getElement(1);
    }
    
    public void Sort() {
        System.out.print("Heap sort trace:\n");
        print(mHeap);
        while(N>1) {
            exch(mHeap, 1, N--);
            sink(1);
            print(mHeap);
        }
    }
    
    private Comparable getElement(int k) {
        return mHeap[k-1];
    }
    protected static boolean less(Comparable[] a, int i, int j) {
        return (a[i-1].compareTo(a[j-1]) < 0);
    }

    protected static boolean greater(Comparable[] a, int i, int j) {
        return (a[i-1].compareTo(a[j-1]) > 0);
    }

    protected static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

}
