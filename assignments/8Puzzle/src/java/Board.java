import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.copyOf;

public class Board {
    private int emptyBlockIdx;
    private int[] blocks;
    private int dimension;


    public Board(int[][] blocks) {
        dimension = blocks.length;
        this.blocks = new int[dimension * dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                final int value = blocks[i][j];
                final int idx = index(i, j);
                this.blocks[idx] = value;
                if (value == 0) emptyBlockIdx = idx;
            }
        }
    }

    public Board(int[] twinBlocks) {
        this.blocks = twinBlocks;
        dimension = (int) Math.sqrt(twinBlocks.length);
        for (int i = 0; i < twinBlocks.length; i++) {
            if(twinBlocks[i] == 0)
                emptyBlockIdx = i;
        }
    }

    private int index(int row, int col) {
        return row * dimension + col;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of blocks out of place
    public int hamming() {
        int score = 0;
        for (int i = 0; i < blocks.length; i++) {
            score += outOfPlace(i) ? 1 : 0;
        }
        return score;
    }

    private boolean outOfPlace(int idx) {
        final int block = blocks[idx];
        return block != 0 && block != (idx + 1);
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int score = 0;
        for (int i = 0; i < blocks.length; i++) {
            score += distanceFromGoal(i);
        }
        return score;
    }

    private int distanceFromGoal(int idx) {
        if (!outOfPlace(idx)) return 0;

        int targetPos = blocks[idx] - 1;
        final int flatDistance = Math.abs(targetPos - idx);
        return flatDistance / dimension + flatDistance % dimension;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        List<Integer> excludedValues = new ArrayList<>();
        excludedValues.add(0);
        final int fromIdx = pickARandomNoEmptyBlock(excludedValues);
        excludedValues.add(blocks[fromIdx]);
        final int toIdx = pickARandomNoEmptyBlock(excludedValues);
        final int[] twinBlocks = switchBlocks(fromIdx, toIdx);
        return new Board(twinBlocks);
    }

    protected int pickARandomNoEmptyBlock(List<Integer> excludedValues) {
        for (int i = 0; i < blocks.length; i++) {
            if(!excludedValues.contains(blocks[i])) return i;
        }
        throw new IllegalStateException();
    }

    private int[] switchBlocks(int fromIdx, int toIdx) {
        final int[] twinBlocks = copyOf(blocks, blocks.length);
        final int temp = twinBlocks[fromIdx];
        twinBlocks[fromIdx] = twinBlocks[toIdx];
        twinBlocks[toIdx] = temp;
        return twinBlocks;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(blocks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        return Arrays.equals(blocks, board.blocks);

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Integer> emptyBlockNeighbors = getNeighbors(emptyBlockIdx);
        return emptyBlockNeighbors.stream()
                .map(n -> new Board(switchBlocks(n, emptyBlockIdx)))
                .collect(Collectors.toList());
    }


    private List<Integer> getNeighbors(int emptyBlockIdx) {
        List<Integer> neighbors = new ArrayList<>();

        if (emptyBlockIdx % dimension > 0) neighbors.add(emptyBlockIdx - 1);
        if (emptyBlockIdx % dimension < dimension - 1) neighbors.add(emptyBlockIdx + 1);
        if (emptyBlockIdx / dimension > 0) neighbors.add(emptyBlockIdx - dimension);
        if (emptyBlockIdx / dimension < dimension - 1) neighbors.add(emptyBlockIdx + dimension);

        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension).append(System.getProperty("line.separator"));
        for (int i = 0; i < blocks.length; i++) {
            sb.append(blocks[i]).append(" ");
            if (i % dimension == dimension - 1) sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}