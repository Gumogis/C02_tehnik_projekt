package Rasterize;

import Models.Line;
import Models.Polygon;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;

    public PolygonRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
    public void rasterize(Polygon polygon) {

        // Zjistíme jestli vůbec lze vykreslit polygon
        if(polygon.size() < 3)
            return;

        // Pokud lze vykreslit tak procházíme list a spojujeme pomocí úseček, nakonec spojíme první a poslední
        for(int i = 0; i < polygon.getPoints().size(); i++){
         int indexb = i + 1;
         if(indexb == polygon.getPoints().size()){
             indexb = 0;
         }
         lineRasterizer.drawLine(new Line(polygon.getPoint(i).getX(),polygon.getPoint(i).getY(),polygon.getPoint(indexb).getX(),polygon.getPoint(indexb).getY()));
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}
