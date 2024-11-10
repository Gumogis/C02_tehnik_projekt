package raster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster  {

    private final BufferedImage image;

    public RasterBufferedImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void setPixel(int x, int y, int value) {
        // Kontrola pro vykreslení i mimo okno
        if(x < getWidth() && y < getHeight() && y > 0 && x > 0){
            image.setRGB(x, y, value);}
    }

    @Override
    public int getPixel(int x, int y) {
        return image.getRGB(x, y);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void clear() {
        Graphics g = image.getGraphics();
        g.clearRect(0, 0, getWidth(), getHeight());
    }

    public Graphics getGraphics() {
        return image.getGraphics();
    }

    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}
