package Models;

public class Edge {
    int x1, y1, x2, y2;

    public Edge(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isHorizontal() {
        return x1 == x2;
    }

    public void orientate() {
        // todo: podle y2 y1 prohodit nebo ne
        if(y1 > y2) {
            int tmpx = x2;
            int tmpy = y2;
            x2 = y1;
            y1 = tmpx;
            tmpx = x2;
            x2 = tmpy;
            y2 = tmpy;
        }
    }

    public boolean isIntersection(){
        return false;
    }
}
