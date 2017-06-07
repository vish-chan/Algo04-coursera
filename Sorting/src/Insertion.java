
public class Insertion extends Base {

    public void Sort(Comparable[] a) {
        System.out.print("Insertion sort trace:\n");
        print(a);
        System.out.print('\n');
        for (int i = 0; i < a.length; i++) {
            int j = i;
            while (j > 0 && less(a[j], a[j - 1])) {
                exch(a, j, j - 1);
                j--;
            }
            print(a);
            System.out.print('\n');
        }
        print(a);
        System.out.print('\n');
        assert isSorter(a);
    }

}
