import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PointSETTest {
    private PointSET pointSet = new PointSET();
    private Point2D pInsideRect = new Point2D(0.6, 0.5);
    private Point2D pOutsideRect = new Point2D(0.1, 0.4);
    private RectHV rect = new RectHV(0.4, 0.3, 0.8, 0.6);

    @Test
    public void newSetIsEmpty() throws Exception {
        assertTrue(pointSet.isEmpty());
        assertFalse(pointSet.contains(pInsideRect));

    }

    @Test
    public void sizeIncreasesWhenUniqueValueIsInserted() throws Exception {
        pointSet.insert(pInsideRect);
        assertEquals(1, pointSet.size());
    }

    @Test
    public void duplicatedValueIsNotInserted() throws Exception {
        pointSet.insert(pInsideRect);
        pointSet.insert(pInsideRect);
        assertEquals(1, pointSet.size());
    }

    @Test
    public void insertedUniqueValueIsFoundInTheSet() throws Exception {
        pointSet.insert(pInsideRect);
        assertTrue(pointSet.contains(pInsideRect));
    }

    @Test
    public void findsPointsInsideARectangle() throws Exception {
        pointSet.insert(pInsideRect);
        final Iterator<Point2D> points = pointSet.range(rect).iterator();
        assertTrue(points.hasNext());
        assertEquals(pInsideRect, points.next());
    }

    @Test
    public void recognizesPointsOutsideARectangle() throws Exception {
        pointSet.insert(pOutsideRect);
        final Iterator<Point2D> points = pointSet.range(rect).iterator();
        assertFalse(points.hasNext());
    }

    @Test
    public void findsClosestPoints() throws Exception {
        final Point2D p1 = new Point2D(0.3, 0.3);
        final Point2D p2 = new Point2D(0.1, 0.1);
        pointSet.insert(p1);
        pointSet.insert(p2);

        assertEquals(p1, pointSet.nearest(new Point2D(0.4, 0.4)));
        assertEquals(p2, pointSet.nearest(new Point2D(0.19, 0.19)));
        assertEquals(p2, pointSet.nearest(p2));

    }
}
