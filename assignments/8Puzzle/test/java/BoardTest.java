import org.junit.Test;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class BoardTest {

    private int[][] blocksInPosition = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private int[][] blocksInPositionNeighbor1 = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};
    private int[][] blocksInPositionNeighbor2 = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
    private int[][] blocksOutOfPosition = new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};

    @Test
    public void hammingScoreIsTheNumberOfBlocksOutOfPositionFromFinalTarget() throws Exception {
        assertEquals(0, new Board(blocksInPosition).hamming());
        assertEquals(5, new Board(blocksOutOfPosition).hamming());
    }

    @Test
    public void manhattanScoreIsTheXYDistanceFromGoal() throws Exception {
        assertEquals(0, new Board(blocksInPosition).manhattan());
        assertEquals(10, new Board(blocksOutOfPosition).manhattan());
    }

    @Test
    public void isEqualIfBlocksAreTheSame() throws Exception {
        final int[][] blockCopy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blockCopy[i][j] = blocksOutOfPosition[i][j];
            }
        }
        assertEquals(new Board(blocksOutOfPosition), new Board(blockCopy));
        assertNotEquals(new Board(blocksInPosition), new Board(blockCopy));
    }

    @Test
    public void cornerEmptyBlockHas2NextSteps() throws Exception {
        final Board board = new Board(blocksInPosition);

        final List<Board> neighbors = StreamSupport.stream(board.neighbors().spliterator(), false)
                .collect(toList());

        neighbors.forEach(System.out::println);
    }
}
