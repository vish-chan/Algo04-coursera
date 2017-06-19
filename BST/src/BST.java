import java.io.IOException;
import java.util.Scanner;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {
        private Key key;
        private Value value;
        Node left, right;

        public Node(Key key, Value value) {
            // TODO Auto-generated constructor stub
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // search a BST
    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else
                return x.value;
        }
        return null;
    }
    
    public void put(Key key, Value value) {
        this.root = put(this.root, key, value);
    }
    
    private Node put(Node x, Key key, Value value) {
        if(x == null)
            return new Node(key, value);
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = put(x.left, key, value);
        else if(cmp > 0)
            x.right = put(x.right, key, value);
        else
            x.value = value;
        return x;
    }
    
    public int height() {
        return height(root);
    }
    
    private int height(Node x) {
        if(x==null)
            return 0;
        return 1 + max(height(x.left), height(x.right));
    }
    
    public String toString() {
        inorder(root);
        return null;
        
    }
    
    private void inorder(Node x) {
        if(x==null)
            return;
        inorder(x.left);
        System.out.print(x.key + ":" + x.value + " ");
        inorder(x.right);
    }
    
    private int max(int i, int j) {
        if(j>i)
            return j;
        return i;
    }
    
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), key, value;
        BST<Integer, Integer> bst = new BST<>();
        while(n-- > 0){
            key = scan.nextInt();
            value = scan.nextInt();
            bst.put(key, value);
        }
        bst.toString();
        System.out.println();
        System.out.print(bst.height());
        System.out.println();
    }
    
}
