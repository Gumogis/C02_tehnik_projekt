package Models;

public class BoldLine extends Line {

    // Tloušťka čáry
    private int r = 15;

    public BoldLine(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    public int getR(){
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
