import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SolverTest {
//    @Test
//    public void findsSearchNodesWithLowestPriority() throws Exception {
//        Board board3 = new Board(new int[]{1,2,3,4,5,6,7,0,8});
//
//        MinPQ<Solver.SearchNode> pq = new MinPQ<>();
//        pq.insert(new Solver.SearchNode(new Board(new int[]{1,0,3,4,2,5,7,8,6}), null));
//        pq.insert(new Solver.SearchNode(new Board(new int[]{4,1,3,0,2,5,7,8,6}), null));
//        pq.insert(new Solver.SearchNode(board3, null));
//
//        final Solver.SearchNode expected = pq.delMin();
//        assertEquals(expected, board3);
//
//    }

    @Test
    public void negativeOneMoveForUnsolvableBoard() throws Exception {
        final Solver solver = new Solver(new Board(new int[][]{{1, 0}, {2, 3}}));
        assertEquals(-1, solver.moves());
    }
}
