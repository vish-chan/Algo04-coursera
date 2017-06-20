import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {
        private Key key;
        private Value value;
        private int size;
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
    
    public Node floor(Key k) {
        return floor(root, k);
    }
    
    private Node floor(Node x, Key k) {
        Node fk = null;
        if(x == null)
            return null;
        if(k.compareTo(x.key) < 0)
            fk = floor(x.left, k);
        else if (k.compareTo(x.key) == 0)
            fk = x;
        else if(k.compareTo(x.key) > 0) {
            Node t = floor(x.right, k);
            if(t!=null)
                fk = t;
            else
                fk = x;              
        }
        return fk;
    }
    
    public Node ceil(Key k) {
        return ceil(root, k);
    }
    
    private Node ceil(Node x, Key k) {
        Node ck = null;
        if(x == null)
            return null;
        if(k.compareTo(x.key) > 0)
            ck = ceil(x.right, k);
        else if (k.compareTo(x.key) == 0)
            ck = x;
        else if(k.compareTo(x.key) < 0) {
            Node t = ceil(x.left, k);
            if(t!=null)
                ck = t;
            else
                ck = x;              
        }
        return ck;
    }
    
    public String toString() {
        this.printNode();
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
    
    //code to print a tree: START
    public void printNode() {
        int maxLevel = this.height();

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }
    private  void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || this.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        this.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.key);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            this.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                this.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    this.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    this.printWhitespaces(1);

                this.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    this.printWhitespaces(1);

                this.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }
    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }
    
    private boolean isAllElementsNull(List<Node> list) {
        for (Node object : list) {
            if (object != null)
                return false;
        }
        return true;
    }
    //code to print a tree: END

    
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
        System.out.println("Height: " + bst.height());
        System.out.println("Floor:" + bst.floor(9).key);
        System.out.println("Ceil: "+bst.ceil(9).key);
    }
    
}
