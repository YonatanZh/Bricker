package main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import gameobjects.Ball;

import java.awt.*;

public class BrickerGameManager extends GameManager {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final Vector2 WINDOW_DIMENSIONS = new Vector2(WIDTH, HEIGHT);
    private static final String TITLE = "Bricker";
    private static final Vector2 DEFAULT_BALL_SIZE = new Vector2(20, 20);
    private static final Vector2 DEFAULT_PADDLE_SIZE = new Vector2(100, 15);


    public BrickerGameManager() {
        super();
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDimensions = windowController.getWindowDimensions();
        createBall(imageReader, windowDimensions);
        createPaddle(imageReader, windowDimensions);
        createWalls(windowDimensions);
        createBackground(imageReader, windowDimensions);


    }

    private void createBall(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        GameObject ball = new Ball(Vector2.ZERO, DEFAULT_BALL_SIZE, ballImage);
        ball.setVelocity(Vector2.DOWN.mult(300));
        ball.setCenter(windowDimensions.mult(0.5f));
        gameObjects().addGameObject(ball);
    }

    private void createPaddle(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        //todo change this later when paddle class is formed
        GameObject paddle = new GameObject(Vector2.ZERO, DEFAULT_PADDLE_SIZE, paddleImage);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }

    private void createWalls(Vector2 windowDimensions) {
        createWall(Vector2.ZERO, new Vector2(10, windowDimensions.y()));
        createWall(new Vector2(windowDimensions.x() - 10, 0), new Vector2(10, windowDimensions.y()));
        createWall(Vector2.ZERO, new Vector2(windowDimensions.x(), 10));
        createWall(new Vector2(0, windowDimensions.y() - 10), new Vector2(windowDimensions.x(), 10));
    }

    private void createWall(Vector2 position, Vector2 size) {
        GameObject wall = new GameObject(position, size, null);
        gameObjects().addGameObject(wall);
    }

    private void createBackground(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", true);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    public static void main(String[] args) {
        BrickerGameManager game = new BrickerGameManager(TITLE, WINDOW_DIMENSIONS);
        game.run();
    }
}

