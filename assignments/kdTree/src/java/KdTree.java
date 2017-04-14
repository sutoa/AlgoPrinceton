import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.awt.*;
import java.util.*;
import java.util.List;

import static edu.princeton.cs.algs4.Point2D.X_ORDER;
import static edu.princeton.cs.algs4.Point2D.Y_ORDER;
import static edu.princeton.cs.algs4.StdDraw.setPenColor;
import static edu.princeton.cs.algs4.StdDraw.setPenRadius;
import static java.util.Collections.reverse;

public class KdTree {
    static final Byte V = 0;
    static final Byte H = 1;
    Node root;
    private int size;

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        Objects.requireNonNull(p);

        size++;
        if (root == null) {
            root = new Node(p, V, new RectHV(0, 0, 1, 1));
            return;
        }

        Node node = root;
        while (true) {
            if (goLeft(node, p)) {
                if (node.left == null) {
                    node.left = new Node(p, oppositeSplit(node), leftRect(node));
                    break;
                } else {
                    node = node.left;
                }
            } else {
                if (node.right == null) {
                    node.right = new Node(p, oppositeSplit(node), rightRect(node));
                    break;
                } else {
                    node = node.right;
                }
            }
        }
    }

    public boolean contains(Point2D p) {
        Objects.requireNonNull(p);

        Node node = root;
        while (node != null) {
            if (node.p.equals(p))
                return true;

            if (goLeft(node, p)) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    public Iterable<Point2D> range(RectHV rect) {
        Objects.requireNonNull(rect);
        return findPointsInRectangleUnderRoot(root, rect);
    }

    private Set<Point2D> findPointsInRectangleUnderRoot(Node root, RectHV rect) {
        Set<Point2D> points = new HashSet<>();

        if (root == null) return points;

        if (rect.contains(root.p)) points.add(root.p);

        if (rect.intersects(leftRect(root)))
            points.addAll(findPointsInRectangleUnderRoot(root.left, rect));

        if (rect.intersects(rightRect(root)))
            points.addAll(findPointsInRectangleUnderRoot(root.right, rect));

        return points;
    }

    public void draw() {
        if (isEmpty()) return;

        LinkedList<Node> nodesToDraw = new LinkedList<>();
        nodesToDraw.add(root);
        while (!nodesToDraw.isEmpty()) {
            Node node = nodesToDraw.remove();
            draw(node);

            if (node.left != null) {
                nodesToDraw.add(node.left);
            }

            if (node.right != null) {
                nodesToDraw.add(node.right);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        final NearestDistantHolder nearestDistantHolder = new NearestDistantHolder(Double.MAX_VALUE, null);
        findNearestFromAllPointsUnderRoot(root, p, nearestDistantHolder);
        return nearestDistantHolder.p;
    }

    private void findNearestFromAllPointsUnderRoot(Node root,
                                                   Point2D queryP,
                                                   NearestDistantHolder nearestDistantHolder) {
        if(root == null) return;

        checkIfNodeIsCloser(root, queryP, nearestDistantHolder);

        List<Node> nodesToCheck = orderNodesToCheck(root, queryP);

        if(nodesToCheck.size() > 0)
            findNearestFromAllPointsUnderRoot(nodesToCheck.get(0), queryP, nearestDistantHolder);
        if (nodesToCheck.size() == 2 && nearestPointsMayExist(queryP, nearestDistantHolder, nodesToCheck.get(1))) {
            findNearestFromAllPointsUnderRoot(nodesToCheck.get(1), queryP, nearestDistantHolder);
        }

    }

    private List<Node> orderNodesToCheck(Node root, Point2D queryP) {
        List<Node> nodesToCheck = new ArrayList<>();
        if(root.right != null) nodesToCheck.add(root.right);
        if(root.left != null) nodesToCheck.add(root.left);

        if(nodesToCheck.size() < 2) return nodesToCheck;

        if (root.split == V) {
            if (X_ORDER.compare(queryP, root.p) < 0) {
                reverse(nodesToCheck);
            }
        } else {
            if (Y_ORDER.compare(queryP, root.p) < 0) {
                reverse(nodesToCheck);
            }
        }
        return nodesToCheck;
    }

    private boolean nearestPointsMayExist(Point2D queryP, NearestDistantHolder nearestDistantHolder, Node node) {
        return node.rect.distanceTo(queryP) < nearestDistantHolder.minDist;
    }


    private void checkIfNodeIsCloser(Node root, Point2D queryP, NearestDistantHolder nearestDistantHolder) {
        final double dist = root.p.distanceTo(queryP);
        if (dist < nearestDistantHolder.minDist) {
            nearestDistantHolder.minDist = dist;
            nearestDistantHolder.p = root.p;
        }
    }

    private void draw(Node node) {
        setPenColor(Color.BLACK);
        setPenRadius(0.008);
        node.p.draw();

        setPenRadius(0.002);
        Point2D begin = null;
        Point2D end = null;
        if (node.split == V) {
            setPenColor(Color.RED);
            begin = new Point2D(node.p.x(), node.rect.ymin());
            end = new Point2D(node.p.x(), node.rect.ymax());
        } else {
            begin = new Point2D(node.rect.xmin(), node.p.y());
            end = new Point2D(node.rect.xmax(), node.p.y());
        }

        begin.drawTo(end);
    }

    RectHV leftRect(Node node) {
        if (node.split == V) {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
        }
    }

    RectHV rightRect(Node node) {
        if (node.split == V) {
            return new RectHV(node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
        }
    }

    private Byte oppositeSplit(Node node) {
        return node.split == V ? H : V;
    }

    boolean goLeft(Node node, Point2D p) {
        if (node.split == V)
            return X_ORDER.compare(p, node.p) < 0;
        else
            return Y_ORDER.compare(p, node.p) < 0;
    }

    static class Node {
        final Point2D p;
        final Byte split;
        RectHV rect;
        Node left;
        Node right;

        Node(Point2D p, Byte split, RectHV rect) {
            this.p = p;
            this.split = split;
            this.rect = rect;
        }

    }

    private static class NearestDistantHolder {
        private double minDist;
        private Point2D p;

        public NearestDistantHolder(double minDist, Point2D p) {
            this.minDist = minDist;
            this.p = p;
        }
    }
}
