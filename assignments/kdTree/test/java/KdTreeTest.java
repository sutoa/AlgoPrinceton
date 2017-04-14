import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class KdTreeTest {
    private static final RectHV UNIT_SQUARE = new RectHV(0, 0, 1, 1);
    private KdTree kdTree = new KdTree();
    private Point2D p1 = new Point2D(0.7, 0.2);
    private Point2D p2 = new Point2D(0.5, 0.4);
    private Point2D p3 = new Point2D(0.2, 0.3);
    private Point2D p4 = new Point2D(0.4, 0.7);
    private Point2D p5 = new Point2D(0.9, 0.6);


    @Test
    public void goesToLeftOfVerticalNodeIfXIsLess() throws Exception {
        KdTree.Node node = new KdTree.Node(p1, KdTree.V, UNIT_SQUARE);
        assertTrue(kdTree.goLeft(node, p2));
    }

    @Test
    public void goesToRightOfHorizontalNodeIfYIsHigher() throws Exception {
        KdTree.Node node = new KdTree.Node(p2, KdTree.H, UNIT_SQUARE);
        assertFalse(kdTree.goLeft(node, p4));
    }

    @Test
    public void insertsSmallerXToTheLeftOfRoot() throws Exception {
        buildTree();

        assertEquals(kdTree.root.p, p1);
        assertEquals(kdTree.root.left.p, p2);
        assertEquals(kdTree.root.left.left.p, p3);
        assertEquals(kdTree.root.left.right.p, p4);
        assertEquals(kdTree.root.right.p, p5);

    }

    private void buildTree() {
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(p5);
    }

    @Test
    public void findsPointAfterInsert() throws Exception {
        buildTree();
        assertTrue(kdTree.contains(p3));
    }

    @Test
    public void splitsRectVertically() throws Exception {
        KdTree.Node root = new KdTree.Node(p1, KdTree.V, UNIT_SQUARE);
        assertEquals(new RectHV(0, 0, 0.7, 1), kdTree.leftRect(root));
        assertEquals(new RectHV(0.7, 0, 1, 1), kdTree.rightRect(root));
    }

    @Test
    public void splitsRectHorizontally() throws Exception {
        KdTree.Node root = new KdTree.Node(p1, KdTree.H, UNIT_SQUARE);
        assertEquals(new RectHV(0, 0, 1, 0.2), kdTree.leftRect(root));
        assertEquals(new RectHV(0, 0.2, 1, 1), kdTree.rightRect(root));
    }

    @Test
    public void rangeCheckForRectWithOnePoint() throws Exception {
        kdTree.insert(p1);
        kdTree.insert(p2);
        final Iterable<Point2D> range = kdTree.range(new RectHV(0.4, 0.2, 0.6, 0.5));
        final Iterator<Point2D> iter = range.iterator();
        assertTrue(iter.hasNext());
        assertEquals(p2, iter.next());

    }

    @Test
    public void pointIsInRangeWhenItsOnRectBorder() throws Exception {
        kdTree.insert(p1);
        kdTree.insert(p2);
        final Iterable<Point2D> range = kdTree.range(new RectHV(0.5, 0.2, 0.6, 0.5));
        final Set<Point2D> points = StreamSupport.stream(range.spliterator(), false).collect(Collectors.toSet());
        assertEquals(1, points.size());
    }

    @Test
    public void rangeCheckForRectWithMultiPoint() throws Exception {
        buildTree();
        final Iterable<Point2D> range = kdTree.range(new RectHV(0.2, 0.2, 0.7, 0.4));
        final Set<Point2D> points = StreamSupport.stream(range.spliterator(), false).collect(Collectors.toSet());
        assertEquals(3, points.size());
        assertTrue(points.contains(p1));
        assertTrue(points.contains(p2));
        assertTrue(points.contains(p3));
    }

    @Test
    public void findsClosestPoint() throws Exception {
        buildTree();
        final Point2D nearest = kdTree.nearest(new Point2D(0.19, 0.31));
        assertEquals(p3, nearest);

    }
}
