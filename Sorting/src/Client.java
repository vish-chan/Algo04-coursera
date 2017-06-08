import java.util.Random;

public class Client {
    public static void main(String[] args) {
        int N = 100;
        Comparable[] a  = new Comparable[N];
        createInput(a, N);
        /*
        Insertion insertion = new Insertion();
        insertion.Sort(a);
        Selection selection = new Selection();
        selection.Sort(a);
        Shell shell = new Shell();
        shell.Sort(a);
        */
        MergeSort mergeSort = new MergeSort();
        mergeSort.BottomUpSort(a);
    }
    
    public static void createInput(Comparable[] a, int N) {
        for(int i = 0; i < N ; i++)
            a[i] = i;
        shuffle(a);
    }
    
    public static void shuffle(Comparable[] a) {
        int r;
        Comparable temp;
        Random rand = new Random();
        for (int i = 1; i<a.length;i++) {
            r = rand.nextInt(i);
            temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}
