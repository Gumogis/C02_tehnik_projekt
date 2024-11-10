package Rasterize;

import Models.BoldLine;
import Models.Line;

import raster.Raster;

public class LineRasterizerTrivial extends LineRasterizer {

    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }

    public LineRasterizerTrivial(Raster raster, int color) {
        super(raster, color);
    }

    @Override
    public void drawLine(Line line) {

        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;

        // Zjišťujeme v jakém kvadrantu se nacházíme a měníme podle toho proměnné
        if(y1 > y2) {
            int y = y2;
            y2 = y1;
            y1 = y;
        }
        if(x1 > x2){
            int x = x2;
            x2 = x1;
            x1 = x;
        }

        // Řesímě svislou úsečku
        if(x1 == x2){
            for(int y = y1; y <= y2; y++) {
                raster.setPixel(x1, y, color);
            }

        // Sledujeme po jaké ose se pohybujeme abychom vykreslily celou úsečku
        } else if( x2 - x1 > y2 - y1) {

            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                raster.setPixel(x, Math.round(y), color);
            }

        } else {

            for (int y = y1; y <= y2; y++) {
                float x = (y - q) / k;
                raster.setPixel(Math.round(x), y, color);
            }

        }
    }

    @Override
    public void drawBoldLine(BoldLine line) {
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();

        float k = (y2 - y1) / (float) (x2 - x1);
        float q = y1 - k * x1;


        if(y1 > y2) {
            int y = y2;
            y2 = y1;
            y1 = y;
        }
        if(x1 > x2){
            int x = x2;
            x2 = x1;
            x1 = x;
        }

        if(x1 == x2){

            for(int y = y1; y <= y2; y++){
                // Řešíme jako normální úsečka jen protahujeme do šířky pomocí for loopů
                for(int x = x1 - line.getR(); x < x1 + line.getR(); x++) {
                    raster.setPixel(x, y, color);
                }
            }

        } else if(x2 - x1 > y2 - y1) {

            for (int x = x1; x <= x2; x++) {
                float y = k * x + q;
                for(float n = y - line.getR(); n < y + line.getR(); n++){
                raster.setPixel(x, Math.round(n), color);
                }
            }

        } else {

            for (int y = y1; y <= y2; y++) {
                float x = (y - q) / k;
                for(float n = x - line.getR(); n < x + line.getR() ; n++) {
                    raster.setPixel(Math.round(n), y, color);
                }
            }

        }
    }
}
