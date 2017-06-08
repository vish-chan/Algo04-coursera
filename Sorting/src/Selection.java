
public class Selection extends Base {

    public void Sort(Comparable[] a) {
        System.out.print("Selection sort trace:\n");
        print(a);
        int min;
        for (int i = 0; i < a.length; i++) {
            min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }
            exch(a, i, min);
            print(a);
        }
        print(a);
        assert isSorted(a);
    }
}
