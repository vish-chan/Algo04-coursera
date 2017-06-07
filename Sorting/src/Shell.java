
public class Shell extends Base {
    public void Sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while(h<N) h = h*3+1;
        h = h/3;
        System.out.print("Shell sort trace:\n");
        print(a);
        System.out.print('\n');
        while (h>=1) {
            for (int i = h; i<N;i++) {
                int j = i;
                while(j>=h && less(a[j], a[j-h])) {
                        exch(a, j, j-h);
                        j-=h;
                }
                System.out.println(h+": ");
                print(a);
                System.out.print('\n');
            }
            h = h/3;
        }
        print(a);
        System.out.print('\n');
        assert isSorter(a);
    }
}
