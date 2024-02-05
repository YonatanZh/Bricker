package main;

import danogl.GameManager;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(WIDTH, HEIGHT);
    private static final String TITLE = "Bricker";


    public BrickerGameManager() {
        super();
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    public static void main(String[] args) {
        BrickerGameManager game = new BrickerGameManager(TITLE, WINDOW_DIMENSIONS);
        game.run();
    }
}
