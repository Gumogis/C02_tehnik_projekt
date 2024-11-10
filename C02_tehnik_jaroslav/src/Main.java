import Controller.Controller2D;
import View.Window;

public class Main {
    public static void main(String[] args) {

        Window window = new Window(1000,800);
        new Controller2D(window.getPanel());
    }
}