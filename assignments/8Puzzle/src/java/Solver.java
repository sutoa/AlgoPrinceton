import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static edu.princeton.cs.algs4.StdOut.println;

public class Solver {

    private Set<Board> processedBoards = new HashSet<>();
    private Set<Board> twinProcessedBoards = new HashSet<>();
    private SearchNode lastNode;
    private boolean solvable = false;
    private MinPQ<SearchNode> nodes = new MinPQ<>();


    public Solver(Board initial) {
        nodes.insert(new SearchNode(initial, null));
        MinPQ<SearchNode> twinNodes = new MinPQ<>();
        twinNodes.insert(new SearchNode(initial.twin(), null));

        while (true){
            lastNode = nodes.delMin();
            processedBoards.add(lastNode.board);
            if(targetNode(lastNode)) {
                solvable = true;
                break;
            }
            process(lastNode, nodes, processedBoards);

            final SearchNode twinNode = twinNodes.delMin();
            twinProcessedBoards.add(twinNode.board);
            if(targetNode(twinNode)){
                break;
            }
            process(twinNode, twinNodes, twinProcessedBoards);
        }
    }

    private void process(SearchNode node, MinPQ<SearchNode> nodes, Set<Board> processedBoards) {
        final Iterator<Board> neighbors = node.board.neighbors().iterator();
        while (neighbors.hasNext()){
            final Board next = neighbors.next();
            if(!processedBoards.contains(next))
                nodes.insert(new SearchNode(next, node));
            else
                System.out.println("already processed " + next);
        }
    }

    private boolean targetNode(SearchNode node) {
        return node.board.hamming() == 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if(solvable) return lastNode.moveCount;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    private Board[] solution() {
        return new Board[0];
    }

    public boolean isSolvable() {
        return false;
    }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            println("No solution possible");
        else {
            println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                println(board);
        }
    }

    protected class SearchNode implements Comparable<SearchNode>{
        private final SearchNode parent;
        private final int moveCount;
        private final Board board;

        SearchNode(Board board, SearchNode parent) {
            this.board = board;
            this.moveCount = parent == null ? 0 : parent.moveCount + 1;
            this.parent = parent;
        }

        int priority(){
            return board.manhattan() + moveCount;
        }

        @Override
        public int compareTo(SearchNode o) {
            return priority() - o.priority();
        }
    }
}