package clip;

import Models.Point;

import java.util.ArrayList;
import java.util.List;

public class Clipper {
    public List<Point> clip(List<Point> clipperPoints, List<Point> pointsToClip) {
        List<Point> clippedPoints = new ArrayList<>();

        // ořezávací hrana body //
        Point cP1 = clipperPoints.get(0);
        Point cP2 = clipperPoints.get(1);

        // todo: t = ???
        Point t = new Point(cP2.getX()-cP1.getX(), cP2.getY()-cP1.getY());
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
        // todo: h = ???
        Point h = new Point(t.getY(), -t.getX());

        for (int i = 0; i < pointsToClip.size(); i++) {
            Point p1 = pointsToClip.get(i);
            //pro každý point se ptám jestli je např. vlevo (pozor na orientaci polygonu, jestli jde protisměru ručiček či s)

            // todo: v = ?
            Point v = new Point(pointsToClip.get(i).getX() - cP1.getX(), pointsToClip.get(i).getY() - cP1.getY());

            // todo: dot product

        }

        return clippedPoints;
    }
}
