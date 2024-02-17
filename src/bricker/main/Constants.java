package bricker.main;

import danogl.util.Vector2;

public class Constants {
    // screen dimensions
    /**
     * The width of the game window.
     */
    public static final int SCREEN_WIDTH = 700;
    /**
     * The height of the game window.
     */
    public static final int SCREEN_HEIGHT = 500;
    /**
     * Buffer between game objects and the edge of the screen.
     */
    public static final int BUFFER = 5;
    /**
     * The width of the wall that surrounds the game area.
     */
    public static final int WALL_WIDTH = 5;

    // assets
    /**
     * The path to the image file for the ball.
     */
    public static final String BALL_PATH = "assets/ball.png";
    /**
     * The path to the sound file for the ball.
     */
    public static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    /**
     * The path to the image file for the paddle.
     */
    public static final String PADDLE_PATH = "assets/paddle.png";
    /**
     * The path to the image file for the background.
     */
    public static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    /**
     * The path to the image file for the brick.
     */
    public static final String BRICK_PATH = "assets/brick.png";
    /**
     * The path to the image file for the heart.
     */
    public static final String LIFE_HEART_PATH = "assets/heart.png";
    /**
     * The path to the image file for the puck.
     */
    public static final String PUCK_PATH = "assets/mockBall.png";
    /**
     * The path to the sound file for the puck.
     */
    public static final String PUCK_SOUND_PATH = "assets/blop_cut_silenced.wav";



    // string constants
    /**
     * The title of the game window.
     */
    public static final String GAME_TITLE = "Bricker";
    /**
     * The prompt to display when the player loses.
     */
    public static final String LOSE_PROMPT = "You lose! Play again?";
    /**
     * The prompt to display when the player wins.
     */
    public static final String WIN_PROMPT = "You win! Play again?";
    
    // ball constants
    /**
     * The radius of the ball.
     */
    public static final int BALL_RADIUS = 20;
    /**
     * The speed of the ball.
     */
    public static final int BALL_SPEED = 200;
    
    // paddle constants
    /**
     * The width of the paddle.
     */
    public static final int PADDLE_WIDTH = 100;
    /**
     * The height of the paddle.
     */
    public static final int PADDLE_HEIGHT = 15;
    /**
     * The speed at which the paddle moves.
     */
    public static final float PADDLE_MOVEMENT_SPEED = 300;

    
    // brick constants
    /**
     * The default number of rows of bricks.
     */
    public static final int DEFAULT_ROW_OF_BRICKS = 7;
    /**
     * The default number of bricks per row.
     */
    public static final int DEFAULT_BRICK_PER_ROW = 8;
    /**
     * The height of a brick.
     */
    public static final int BRICK_HEIGHT = 15;

    // lives constants
    /**
     * The threshold for the color of the numerical lives display to be green.
     */
    public static final int GREEN_THRESHOLD = 3;
    /**
     * The threshold for the color of the numerical lives display to be yellow.
     */
    public static final int YELLOW_THRESHOLD = 2;
    /**
     * The default number of lives.
     */
    public static final int DEFAULT_LIVES = 3;
    /**
     * The size of the heart image.
     */
    public static final int DROPPING_LIFE_SIZE = 20;
    /**
     * The velocity of the heart image.
     */
    public static final Vector2 HEART_VELOCITY = new Vector2(0, 100);
    /**
     * The indentation form the right edge of the screen for the numerical lives display.
     */
    public static final int LIVES_INDENT_SIZE = 50;


    // special behavior constants
    /**
     * The number of special behaviors.
     */
    public static final int BEHAVIOR_AMOUNT = 5;
    /**
     * Numerical representation for the special behavior that gives the player an extra puck.
     */
    public static final int EXTRA_PUCK = 0;
    /**
     * Numerical representation for the special behavior that gives the player an extra paddle.
     */
    public static final int EXTRA_PADDLE = 1;
    /**
     * Numerical representation for the special behavior that changes the camera.
     */
    public static final int CAMERA_CHANGE = 2;
    /**
     * Numerical representation for the special behavior that drops extra lives.
     */
    public static final int EXTRA_LIFE = 3;
    /**
     * Numerical representation for the special behavior that creates double behavior bricks.
     */
    public static final int DOUBLE = 4;
    /**
     * The distribution of special behaviors.
     */
    public static final int SPECIAL_BEHAVIOR_ODDS = 10;
    /**
     * The maximum number of special behaviors that can be on a brick.
     */
    public static final int MAX_BEHAVIORS_PER_BRICK = 3;
    /**
     * The amount of times a collision with a game object for the camera change behavior.
     */
    public static final int CAMERA_CHANGE_MAX_COLLISIONS = 4;
    /**
     * The amount of times a collision with a ball for the disappearing paddle behavior.
     */
    public static final int DISAPPEARING_PADDLE_MAX_COLLISIONS = 4;
    /**
     * The amount of extra paddles that can be on the screen at once.
     */
    public static final int MAX_PADDLES = 1;
    /**
     * The reduction size of the puck compared to the ball.
     */
    public static final float PUCK_REDUCTION_FACTOR = 0.75f;
    /**
     * The amount of time the puck that are created by the extra puck behavior.
     */
    public static final int PUCK_AMOUNT = 2;
    /**
     * The speed of the puck.
     */
    public static final int PUCK_SPEED = 175;




    private Constants(){}
}
