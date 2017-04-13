import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import static edu.princeton.cs.algs4.StdDraw.setPenColor;
import static edu.princeton.cs.algs4.StdDraw.setPenRadius;

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
            if (node.key.equals(p))
                return true;

            if (goLeft(node, p)) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Objects.requireNonNull(rect);

        return subRange(root, rect);
    }

    private Set<Point2D> subRange(Node root, RectHV rect) {
        Set<Point2D> points = new HashSet<>();

        if (root == null) return points;

        if (rect.contains(root.key)) points.add(root.key);

        if (rect.intersects(leftRect(root)))
            points.addAll(subRange(root.left, rect));

        if (rect.intersects(rightRect(root)))
            points.addAll(subRange(root.right, rect));

        return points;
    }

    public void draw(){
        if(isEmpty()) return;

        LinkedList<Node> nodesToDraw = new LinkedList<>();
        nodesToDraw.add(root);
        while(!nodesToDraw.isEmpty()){
            System.out.println("size " + nodesToDraw.size());
            Node node = nodesToDraw.remove();
            draw(node);

            if(node.left != null){
                 nodesToDraw.add(node.left);
            }

            if(node.right != null){
                nodesToDraw.add(node.right);
            }
        }
    }

    private void draw(Node node) {
        System.out.println("drawing node " + node);
        setPenColor(Color.BLACK);
        setPenRadius(0.008);
        node.key.draw();

        setPenRadius(0.002);
        Point2D begin = null;
        Point2D end = null;
        if(node.split == V) {
            setPenColor(Color.RED);
            begin = new Point2D(node.key.x(), node.rect.ymin());
            end = new Point2D(node.key.x(), node.rect.ymax());
        }  else {
            begin = new Point2D(node.rect.xmin(), node.key.y());
            end = new Point2D(node.rect.xmax(), node.key.y());
        }

        begin.drawTo(end);
    }

    RectHV leftRect(Node node) {
        if (node.split == V) {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.key.x(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.key.y());
        }
    }

    RectHV rightRect(Node node) {
        if (node.split == V) {
            return new RectHV(node.key.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
        } else {
            return new RectHV(node.rect.xmin(), node.key.y(), node.rect.xmax(), node.rect.ymax());
        }
    }

    private Byte oppositeSplit(Node node) {
        return node.split == V ? H : V;
    }

    boolean goLeft(Node node, Point2D p) {
        if (node.split == V)
            return Point2D.X_ORDER.compare(p, node.key) < 0;
        else
            return Point2D.Y_ORDER.compare(p, node.key) < 0;
    }

    static class Node {
        final Point2D key;
        final Byte split;
        RectHV rect;
        Node left;
        Node right;

        Node(Point2D key, Byte split, RectHV rect) {
            this.key = key;
            this.split = split;
            this.rect = rect;
        }

    }
}
