import org.junit.Test;

public class SolverTest {
    @Test
    public void findsSearchNodesWithLowestPriority() throws Exception {
        Board board1 = new Board(new int[]{1,0,3,4,2,5,7,8,6});
        Board board2 = new Board(new int[]{4,1,3,0,2,5,7,8,6});
        Solver.SearchNode node1;
        node1 = new Solver.SearchNode(board1, null);

    }
}
