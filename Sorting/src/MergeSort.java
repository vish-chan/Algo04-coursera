
public class MergeSort extends Base {
    private void Merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int N = hi - lo + 1;
        for (int i = lo; i <= hi; i++)
            aux[i] = a[i];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    private void Sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        Sort(a, aux, lo, mid);
        Sort(a, aux, mid + 1, hi);
        Merge(a, aux, lo, mid, hi);
        print(a);
    }

    public void Sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        System.out.print("Merge sort trace:\n");
        print(a);
        Sort(a, aux, 0, a.length - 1);
    }

    public void BottomUpSort(Comparable[] a) {
        System.out.print("BottomUP Merge sort trace:\n");
        print(a);
        int N = a.length;
        int lo, mid, hi;
        Comparable[] aux = new Comparable[N + 1];
        for (int i = 0; i < a.length; i++)
            aux[i] = a[i];
        for (int size = 1; size < N; size = size + size) {
            for (int i = 0; i < N - size; i = i + size + size) {
                lo = i;
                mid = lo + size-1;
                hi = Math.min(lo + size + size - 1, N-1);
                Merge(a, aux, lo, mid, hi);
            }
            print(a);
        }
    }

}
