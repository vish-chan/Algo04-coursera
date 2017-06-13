import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    // Implements nodes Game tree
    private class Node implements Comparable<Node> {

        private Board mBoard;
        private int mNumOfMoves = 0;
        private int priority;
        private Node parent;

        public Node(Board theBoard) {
            this.mBoard = theBoard;
            this.mNumOfMoves = 0;
            this.priority = theBoard.manhattan() + mNumOfMoves;
            this.parent = null;
        }

        // using manhattan distance as heuristic (h(n))
        public Node(Board theBoard, Node theParent) {
            this.mBoard = theBoard;
            this.mNumOfMoves = theParent.getNumOfMoves() + 1; // g(n)
            this.priority = theBoard.manhattan() + this.mNumOfMoves;
            this.parent = theParent;

        }

        public int getNumOfMoves() {
            return mNumOfMoves;
        }

        // using priority g(n) + h(n) for comparison for nodes
        @Override
        public int compareTo(Node that) {
            return this.priority - that.priority;
        }
    }

    private Node lastMove;
    private Node twinlastMove;

    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.NullPointerException();
        MinPQ<Node> moves = new MinPQ<Node>();
        MinPQ<Node> twinMoves = new MinPQ<Node>();
        Node first = new Node(initial);
        moves.insert(first);
        Node firstTwin = new Node(initial.twin());
        twinMoves.insert(firstTwin);
        lastMove = null;
        twinlastMove = null;
        while (true) {
            lastMove = checkOrExpand(moves);
            if (lastMove != null)
                return;
            twinlastMove = checkOrExpand(twinMoves);
            if (twinlastMove != null)
                return;
        }
    }

    private Node checkOrExpand(MinPQ<Node> pq) {
        if (pq.isEmpty())
            return null;
        Node bestnode = pq.delMin();
        if (bestnode != null && bestnode.mBoard.isGoal())
            return bestnode;
        expand(pq, bestnode);
        return null;
    }

    private void expand(MinPQ<Node> pq, Node node) {
        if (node == null)
            return;
        Iterable<Board> nextmoves = node.mBoard.neighbors();
        for (Board nextmove : nextmoves) {
            if (nextmove == null)
                continue;
            if (node.parent != null)
                if (nextmove.equals(node.parent.mBoard))
                    continue;
            pq.insert(new Node(nextmove, node));
        }
    }

    public boolean isSolvable() {
        return lastMove != null;
    }

    public int moves() {
        if (isSolvable())
            return lastMove.getNumOfMoves();
        return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> shortestpath = new Stack<>();
        Node curr = lastMove;
        while (curr != null) {
            shortestpath.push(curr.mBoard);
            curr = curr.parent;
        }
        return shortestpath;
    }
}
