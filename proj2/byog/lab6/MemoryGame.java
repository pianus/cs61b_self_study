package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String s = "";
        for (int i = 0; i < n; i += 1) {
            s = s + CHARACTERS[RandomUtils.uniform(rand, n)];
        }
        return s;
    }

    public void drawFrame(String s) {
        StdDraw.clear(StdDraw.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);

        //TODO: Take the string and display it in the center of the screen
        StdDraw.text(20, 20, s);

        Font encourageFont = new Font("Monaco", Font.PLAIN, 18);
        StdDraw.setFont(encourageFont);
        //TODO: If game is not over, display relevant game information at the top of the screen
        if (!gameOver) {
            StdDraw.textLeft(0, 39, "Round: " + ((Integer) round).toString());
            StdDraw.text(20, 39, "Watch!");
            StdDraw.textRight(40, 39, ENCOURAGEMENT[RandomUtils.uniform(rand, 7)]);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setPenRadius(0.002);
            StdDraw.line(0, 38.5, 40, 38.5);
        }
        StdDraw.show();

    }

    public void flashSequence(String letters) {
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 0; i < letters.length(); i += 1) {
            drawFrame(((Character) letters.charAt(i)).toString());
            //pause for 1 second


            //display the black screen
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            //pause for 0.5 second
        }

        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int i = 0;
        String s = "";
        while (true) {
            if (i == n) {
                break;
            }
            if (StdDraw.hasNextKeyTyped()) {
                Character c = StdDraw.nextKeyTyped();
                s = s + c.toString();
                i += 1;
            }
        }
        return s;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            String gameGenerated = generateRandomString(round);
            flashSequence(gameGenerated);
            //display screen to let user input
            StdDraw.text(20, 20, "please type in characters you saw");
            StdDraw.disableDoubleBuffering();
            String userInput = solicitNCharsInput(round);
            if (!userInput.equals(gameGenerated)) {
                gameOver = true;
            }
            round += 1;

            //display screen that let user know the game continues
            StdDraw.text(20, 20, "congrads! next round!");
            
        }

    }

}
