
public class QuickSort extends Base {

    private int partition(Comparable[] a, int lo, int hi) {
        int pivot = lo;
        int i = lo + 1, j = hi;
        while (true) {
            while (i < hi && less(a[i], a[pivot]))
                i++;
            while (j > lo && greater(a[j], a[pivot]))
                j--;
            if (i >= j)
                break;
            exch(a, i, j);
        }
        exch(a, pivot, j);
        return j;
    }

    private void Sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int j = partition(a, lo, hi);
        print(a);
        Sort(a, lo, j - 1);
        Sort(a, j + 1, hi);
    }

    public void Sort(Comparable[] a) {
        System.out.print("Quick sort trace:\n");
        print(a);
        // SHUFFLE ARRAY
        Sort(a, 0, a.length - 1);
    }
    
    public Comparable QuickSelect(Comparable[] a, int k) {
        return QuickSelect(a, 0, a.length - 1, k - 1);
    }

    private Comparable QuickSelect(Comparable[] a, int lo, int hi, int k) {
        if (hi <= lo)
            return a[lo];
        int j = partition(a, lo, hi);
        if (k < j)
            return QuickSelect(a, lo, j - 1, k);
        if (k > j)
            return QuickSelect(a, j + 1, hi, k);
        return a[j];
    }
    
    public void ThreeWaySort(Comparable[] a) {
        System.out.print("3 way Quick sort trace:\n");
        print(a);
        // SHUFFLE ARRAY
        ThreeWaySort(a, 0, a.length - 1);
    }


    private void ThreeWaySort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        Comparable pivot = a[lo];
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        /*3 way partitioning using lt, i and gt
         * lt always points to the first pivot, all elements at idx < lt are < pivot
         * i points to current elements, elemts from i to gt are not seen
         * all elements at idx > gt are > pivot
         */
        while(i<=gt) {
            if(less(a[i], pivot)) {
                exch(a, lt, i);
                lt++;
                i++;
            }
            else if(greater(a[i], pivot)) {
                exch(a, i, gt);
                gt--;
            }
            else {
                i++;
            }   
        }
        print(a);
        ThreeWaySort(a, lo, lt-1);
        ThreeWaySort(a, gt+1, hi);
    }
}
