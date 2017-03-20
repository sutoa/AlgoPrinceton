import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

import static edu.princeton.cs.algs4.StdOut.println;

public class Solver {

    private SearchNode lastNode;
    private boolean solvable = false;


    public Solver(Board initial) {
        MinPQ<SearchNode> nodes = new MinPQ<>();
        nodes.insert(new SearchNode(initial, null));
        MinPQ<SearchNode> twinNodes = new MinPQ<>();
        twinNodes.insert(new SearchNode(initial.twin(), null));

        while (true){
            lastNode = nodes.delMin();
            Set<Board> processedBoards = new HashSet<>();
            processedBoards.add(lastNode.board);
            if(targetNode(lastNode)) {
                solvable = true;
                break;
            }
            process(lastNode, nodes, processedBoards);

            final SearchNode twinNode = twinNodes.delMin();
            Set<Board> twinProcessedBoards = new HashSet<>();
            twinProcessedBoards.add(twinNode.board);
            if(targetNode(twinNode)){
                break;
            }
            process(twinNode, twinNodes, twinProcessedBoards);
        }
    }

    private void process(SearchNode node, MinPQ<SearchNode> nodes, Set<Board> processedBoards) {
        for (Board next : node.board.neighbors()) {
            if (!processedBoards.contains(next))
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
    protected Board[] solution() {
        List<Board> boards = new ArrayList<>();
        if(solvable){
             while(lastNode != null){
                 boards.add(lastNode.board);
                 lastNode = lastNode.parent;
             }
        }
        Collections.reverse(boards);
        Board[] b = new Board[boards.size()];
        boards.toArray(b);
        return b;
    }

    public boolean isSolvable() {
        return solvable;
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

}
