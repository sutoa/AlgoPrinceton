import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.*;

public class PointSET {
    private Set<Point2D> points = new TreeSet();

    public PointSET() {
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        Objects.requireNonNull(p, "cannot be null");
        points.add(p);
    }

    public boolean contains(Point2D p) {
        Objects.requireNonNull(p, "cannot be null");
        return points.contains(p);
    }

    public void draw() {
        points.stream().forEach(p -> p.draw());
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Objects.requireNonNull(rect);
        return points.stream().filter(p -> rect.contains(p))::iterator;
    }

    // a nearest neighbor in the set to point p; null if the set is empty {}
    public Point2D nearest(Point2D p) {
        Objects.requireNonNull(p);

        final Optional<Point2D> min = points.stream()
                .min(new Comparator<Point2D>() {
                    @Override
                    public int compare(Point2D o1, Point2D o2) {
                        final double v = o1.distanceTo(p) - o2.distanceTo(p);
                        return v < 0 ? -1 : (v == 0 ? 0 : 1);
                    }
                });
        return min.get();
    }

}