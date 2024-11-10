package Controller;

import Fill.SeedFiller;
import Models.BoldLine;
import Models.Line;
import Models.Polygon;
import Models.Point;
import Rasterize.LineRasterizer;
import Rasterize.LineRasterizerTrivial;
import Rasterize.PolygonRasterizer;
import View.Panel;
import clip.Clipper;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller2D {
    private final Panel panel;
    private LineRasterizer lineRasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Point start;
    private Point end;
    private Polygon polygon;
    private boolean straightLineBool;
    private boolean polygonBool;
    private boolean boldLineBool;
    private ArrayList<Line> lines;
    private ArrayList<BoldLine> boldLines;
    private SeedFiller seedFiller;
    private Line clipperLine;
    private boolean seedFillBool;

    public Controller2D(Panel panel) {
        this.panel = panel;

        panel.getHeight();

        initObjects();
        initListeners();

        panel.repaint();

        }

        // Inicializace objektů separátně pro větší přehlednost
        private void initObjects(){

        straightLineBool = false;
        polygonBool = false;
        boldLineBool = false;

        lines = new ArrayList<>();
        boldLines = new ArrayList<>();

        polygon = new Polygon();
        // clipperLine = new Line(0, panel.getHeight()/2, panel.getWidth()-1, panel.getHeight()/2);

        lineRasterizer = new LineRasterizerTrivial(panel.getRaster());
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);

        }

        private void initListeners(){

        // Listener pro prvotní stisk a ošetření jednopixelové čáry
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    start = new Point(e.getX(),e.getY());
                    if(!polygonBool){
                        panel.clear();
                        end = start;
                        lineRasterizer.drawLine(new Line(start.getX(),start.getY(),start.getX(),start.getY()));
                        panel.repaint();
                    }
                }
                if(SwingUtilities.isRightMouseButton(e)){
                    start = new Point(e.getX(),e.getY());

                    panel.clear();
                    seedFiller = new SeedFiller(panel.getRaster(), start.getX(),start.getY(), 0x00ff00);
                    seedFillBool = true;
                    System.out.println("painting");
                    panel.repaint();
                    redraw();
                }
            }
        });

        // Listener tahání myši
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {


                if (SwingUtilities.isLeftMouseButton(e) && !polygonBool) {
                    panel.clear();
                    end = new Point(e.getX(),e.getY());

                    // Podmínka kde kontrolujeme jestli se jedná o zarovnanou čáru, když ne tak vykreslujeme
                    if (straightLineBool) {
                        float width = start.getX() - end.getX();
                        float height = start.getY() - end.getY();
                        double angle = (height / (Math.sqrt((width * width) + (height * height))) * 90);

                        // Zjišťujeme kde se nacházíme úhelně a podle toho nastavujeme úsečku
                        if (Math.abs(angle) <= 75 && Math.abs(angle) >= 20) {
                            if (height < 0 && width > 0 || height > 0 && width < 0) {
                                end = new Point(start.getX() + Math.round(height), e.getY());
                            } else if (height < 0 && width < 0 || height > 0 && width > 0) {
                                end = new Point(start.getX() - Math.round(height), e.getY());
                            }
                        } else if (Math.abs(angle) > 75) {
                            end = new Point(start.getX(), e.getY());
                        } else {
                            end = new Point(e.getX(), start.getY());
                        }
                    }

                    // Nakonec vykreslujeme úsečku, zjišťujeme jestli se jedná o tlustou
                    if(boldLineBool)
                        lineRasterizer.drawBoldLine(new BoldLine(start.getX(), start.getY(), end.getX(), end.getY()));
                    else
                        lineRasterizer.drawLine(new Line(start.getX(), start.getY(), end.getX(), end.getY()));

                    panel.repaint();
                    redraw();
                }
            }
        });

        // Listener pro tlačítka
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                // C maže všechno a resetuje canvas
                if(key == KeyEvent.VK_C){
                    panel.clear();
                    polygon.clearPoints();
                    lines.clear();
                    boldLines.clear();
                    panel.repaint();
                    redraw();
                    System.out.println("Canvas cleared");
                }

                // Shift nastavuje zarovnanou čáru
                if(key == KeyEvent.VK_SHIFT){
                straightLineBool = !straightLineBool;

                    if(straightLineBool){
                        System.out.println("Straightening the line");
                    } else {
                        System.out.println("Ending the straightening of the line");
                    }
                }

                // Y nastavuje kreslení polygonu
                if(key == KeyEvent.VK_Y){
                    polygonBool = !polygonBool;
                    if(polygonBool){
                        System.out.println("Drawing a polygon");
                    } else {
                        System.out.println("Stopping drawing a polygon");
                    }
                }

                // X nastavuje tlustou čáru
                if(key == KeyEvent.VK_X){
                    boldLineBool = !boldLineBool;
                    if(boldLineBool){
                        System.out.println("Drawing a bold line");
                    } else {
                        System.out.println("Stopping drawing a bold line");
                    }
                }
            }
        });

        // Listener pro ukončení stisku a zapsání objektů do listů
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    panel.clear();

                if(!polygonBool){
                    if(boldLineBool)
                        boldLines.add(new BoldLine(start.getX(),start.getY(), end.getX(), end.getY()));
                    else
                        lines.add(new Line(start.getX(),start.getY(), end.getX(), end.getY()));

                } else {
                    polygon.addPoint(new Point(start.getX(), start.getY()));

                    //List<Point> clipperPoints = new ArrayList<>();
                    //clipperPoints.add(new Point(clipperLine.getX1(),clipperLine.getY1()));
                    //clipperPoints.add(new Point(clipperLine.getX2(),clipperLine.getY2()));

                    //Clipper clipper = new Clipper();

                    //List<Point> resultPoints = clipper.clip(clipperPoints, polygon.getPoints());

                    //polygonRasterizer.rasterize(new Polygon(resultPoints));
                }
                    panel.repaint();
                    redraw();
            }
            }
        });
    }

    // Funkce co nám kreslí už uložené objekty, prochází listy
    private void redraw(){

     //lineRasterizer.drawLine(clipperLine);

    for(Line line : lines)
        lineRasterizer.drawLine(line);

    for(BoldLine boldLine : boldLines)
        lineRasterizer.drawBoldLine(boldLine);

    if(polygon.size() > 3 )
        polygonRasterizer.rasterize(polygon);

    if(seedFillBool)
        seedFiller.fill();

    }

}
