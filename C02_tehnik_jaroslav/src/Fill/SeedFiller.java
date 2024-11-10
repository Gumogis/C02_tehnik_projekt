package Fill;

import Models.Point;
import raster.Raster;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SeedFiller implements Filler {
    private Raster raster;
    private int x,y;
    private int fillColor;
    private int backgroundColor;
    private Stack<Point> points;

    public SeedFiller(Raster raster, int x, int y, int fillColor) {
        points = new Stack<>();
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.fillColor = fillColor;
        this.backgroundColor = raster.getPixel(x,y);
    }

    @Override
    public void fill() {
        seedFill(x,y);
    }

    private void seedFill(int x, int y){

        for(int w = 0; w < (raster.getWidth()-x); w++){
            for(int h = 0; h < (raster.getHeight()-y); h++){
                if(raster.getPixel(x+w,y+h) == backgroundColor){
                    points.push(new Point(x+w,y+h));
                } else {
                    break;
                }
            }
        }

        while(!points.isEmpty()){
            Point point = points.pop();
            raster.setPixel(point.getX(),point.getY(), fillColor);
        }
    }
}
