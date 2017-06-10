
public class Base {
    protected static boolean isSorted(Comparable[] a) {
        for (int i = 0; i < a.length - 1; i++)
            if (greater(a[i], a[i + 1]))
                return false;
        return true;
    }

    protected static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    protected static boolean greater(Comparable v, Comparable w) {
        return (v.compareTo(w) > 0);
    }

    protected static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    protected static int max(Comparable[] a, int i, int j) {
        if(less(a[i], a[j]))
            return j;
        return i;
    }

    protected static void print(Comparable[] A) {
        for (Comparable a : A)
            System.out.print(a + " ");
        System.out.print('\n');
    }
}
