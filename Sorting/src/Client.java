
public class Client {
    public static void main(String[] args) {
        Comparable[] a = {10,8,9,7,5,6,4,3,2,1};
        Comparable[] b = {10,8,9,7,5,6,4,3,2,1};
        Comparable[] c = {10,8,9,7,5,6,4,3,2,1};
        Insertion insertion = new Insertion();
        //insertion.Sort(a);
        Selection selection = new Selection();
        //selection.Sort(b);
        Shell shell = new Shell();
        shell.Sort(c);
    }
}
