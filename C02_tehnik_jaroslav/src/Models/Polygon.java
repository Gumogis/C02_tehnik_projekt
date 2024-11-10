package Models;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private final List<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getPoint(int index){
        return points.get(index);
    }

    public int size(){
        return points.size();
    }

    public void clearPoints(){ points.clear(); }
}
