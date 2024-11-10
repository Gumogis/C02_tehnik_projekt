package Fill;

import Models.Edge;
import Models.Point;
import Models.Polygon;
import Rasterize.LineRasterizer;
import Rasterize.PolygonRasterizer;

import java.util.ArrayList;

public class ScanLineFiller implements Filler{
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Polygon polygon;
    private ArrayList<Edge> edges;

    public ScanLineFiller(LineRasterizer lineRasterizer, PolygonRasterizer polygonRasterizer, Polygon polygon) {
        this.lineRasterizer = lineRasterizer;
        this.polygonRasterizer = polygonRasterizer;
        this.polygon = polygon;
        edges = new ArrayList<>();
    }

    public void fill() {
        // todo: pomocny seznam hran
        // todo: projdu pointy polygonu a pro každé 2 pointy vytvořím hranu
        // todo: hranu vložím do seznamu
        for(int i = 0; i < polygon.size();i++){
            Point p1 = new Point(polygon.getPoint(i).getX(), polygon.getPoint(i).getY());
            Point p2 = new Point(polygon.getPoint((i + 1) % polygon.size()).getX(), polygon.getPoint((i + 1) % polygon.size()).getY());

            Edge edge = new Edge(p1.getX(), p1.getY(), p2.getX(), p2.getY());

            if(!edge.isHorizontal()){
                edge.orientate();
                edges.add(edge);
            }
        }

        // todo: najít miny maxy
        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;

        // todo: najít reálný ymin ymax
        for(int y = yMin; y <= yMax;y++){
            for(Edge edge : edges){
                edge.isIntersection();
                // todo: existuje průsečík
                // todo: isIntersection
                // todo: pokud jo tak vypočítám
                // todo: vysledkem souradnice x -> ulozit do seznamu
            }

            // todo: preradit pruseciky zleva do prava
            // todo: vykreslim usecku mezi kazdym lichym a sudym prusecikem
            // todo: vykreslim hranici polygonu
        }



    }
}
