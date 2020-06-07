package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Random;

public class Game implements java.io.Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public int width = 50;
    public int height = 30;
    public TETile[][] world;
    public int seed;
    public Position characterPos;
    public Position catPos;
    public boolean catTurn;
    public boolean gameWin;
    public boolean playWithKeyboard;


    public Game() {


    }


    public void createWorld(int seed) {

        this.world = new TETile[width][height];

        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Random r = new Random(seed);
        RandomUtils.uniform(r);
        int numOfRooms = RandomUtils.uniform(r, 4, 10);

        CreateMap.createRandomMap(r, world, numOfRooms);
        this.characterPos = CreateMap.createMoveObjectRandom(r, world, Tileset.PLAYER);
        this.catPos = CreateMap.createMoveObjectRandom(r, world, Tileset.CAT);
        this.catTurn = true;
        this.gameWin = false;

    }

    boolean fitInMap(Position p) {
        if (p.x < this.width && p.y < this.height
                && p.x >= 0 && p.y >= 0) {
            return true;
        }
        return false;
    }

    //return the TETile at the position p in the world
    public TETile worldAt(Position p) {
        if (!fitInMap(p)) {
            return null;
        }
        return world[p.x][p.y];
    }

    //change the TETile at position p in the world
    public TETile changeMapAt(Position p, TETile t) {
        if (!fitInMap(p)) {
            return null;
        }
        world[p.x][p.y] = t;
        return world[p.x][p.y];
    }

    //draw the seed input window
    public void gameStartWindow() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(0.5, 0.5, 0.5);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.52, "Enter Seed Number,");
        StdDraw.text(0.5, 0.48, "then press 's' to start");

    }

    // get seed method for play with keyboard method
    public int getSeed() {
        int seed = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                // when user input S or s, start the game
                if (c == 'S' || c == 's') {
                    this.seed = seed;
                    return seed;
                }
                // read seed number from user input
                int nextDigit;
                try {
                    nextDigit = (int) c;
                } catch (ClassCastException e) {
                    continue;
                }
                seed = seed * 10 + nextDigit;
            }
        }

    }
    //get seed method for play with input string method
    public int getSeed(Iterator<Character> input) {
        int seed = 0;
        while (true) {
            if (input.hasNext()) {
                char c = input.next();
                // when user input S or s, start the game
                if (c == 'S' || c == 's') {
                    this.seed = seed;
                    return seed;
                }
                // read seed number from user input
                int nextDigit;
                try {
                    nextDigit = (int) c;
                } catch (ClassCastException e) {
                    continue;
                }
                seed = seed * 10 + nextDigit;
            }
        }
    }

    public void saveGame() {
        try {
            FileOutputStream f = new FileOutputStream(new File("savedGame.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    public void readSavedFile() {
        System.out.println("loaded!");
        try {
            FileInputStream fi = new FileInputStream(new File("savedGame.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            Game g = (Game) oi.readObject();
            this.width = g.width;
            this.height = g.height;
            this.world = g.world;
            this.seed = g.seed;
            this.characterPos = g.characterPos;
            this.catPos = g.catPos;
            this.catTurn = g.catTurn;
            this.gameWin = g.gameWin;
            this.playWithKeyboard = g.playWithKeyboard;


            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loadGame(){
        System.out.println("loaded!");
        readSavedFile();
        this.ter.renderFrame(this.world);
        StdDraw.show();
        interactivePlay();

    }
    public void loadGame(Iterator<Character> input){
        System.out.println("loaded!");
        readSavedFile();
        interactivePlay(input);

    }

    public Position catMove() {
        Position movePotential = Position.relativeDirection(characterPos, catPos);
        Position[] moveDirectionRank = Position.directionRankin(movePotential);
        for (Position p : moveDirectionRank) {
            Position targetPos = Position.add(catPos, p);
            if (worldAt(targetPos).equals(Tileset.FLOOR)) {
                changeMapAt(catPos, Tileset.FLOOR);
                changeMapAt(targetPos, Tileset.CAT);
                catPos = targetPos;
                return catPos;
            }
        }
        return null;
    }

    public void move(char c) {
        Position targetPos;
        switch (c) {
            case 'w':
            case 'W':
                targetPos = new Position(characterPos.x, characterPos.y + 1);
                break;

            case 'd':
            case 'D':
                targetPos = new Position(characterPos.x + 1, characterPos.y);
                break;

            case 'a':
            case 'A':
                targetPos = new Position(characterPos.x - 1, characterPos.y);
                break;

            case 's':
            case 'S':
                targetPos = new Position(characterPos.x, characterPos.y - 1);
                break;
            default:
                return;

        }
        if (worldAt(targetPos).equals(Tileset.FLOOR)) {
            changeMapAt(characterPos, Tileset.FLOOR);
            changeMapAt(targetPos, Tileset.PLAYER);
            characterPos = targetPos;

            //cat move 1 step for every 2 step player move
            if (catTurn) {
                catMove();
            }
            catTurn = !catTurn;
        }

        if (worldAt(targetPos).equals(Tileset.CAT)) {
            changeMapAt(characterPos, Tileset.FLOOR);
            changeMapAt(targetPos, Tileset.PLAYER);
            characterPos = targetPos;
            gameWin = true;
        }
    }

    public void reactOponInput(char c) {
        switch (c) {
            //character move
            case 'w':
            case 'W':
            case 'd':
            case 'D':
            case 'a':
            case 'A':
            case 's':
            case 'S':
                move(c);
                break;

            //rotate the world
            case 'o':
            case 'O':
                CreateMap.rotateWorld(this, true);
                break;
            case 'p':
            case 'P':
                CreateMap.rotateWorld(this, false);
            case 'q':
            case 'Q':
                this.saveGame();
                System.exit(0);

        }
    }



    public void interactivePlay() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                System.out.println(c);
                reactOponInput(c);
                ter.renderFrame(this.world);
                }
            if (gameWin == true) {
                return;
            }
        }
    }

    public void interactivePlay(Iterator<Character> input) {

        while (input.hasNext()) {
            char c = input.next();
            reactOponInput(c);
        }
        if (gameWin == true) {
            return;
        }
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        playWithKeyboard = true;

        StdDraw.clear(StdDraw.BLACK);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Sans Serif", Font.PLAIN, 32));
        StdDraw.text(0.5, 0.8, "Meow Catching!");
        StdDraw.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        StdDraw.text(0.5, 0.54, "New Game (N)");
        StdDraw.text(0.5, 0.50, "Load Game (L)");
        StdDraw.text(0.5, 0.46, "Quit (Q)");

        // read input char
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                switch (c) {
                    case 'n':
                    case 'N':
                        System.out.println("started!");
                        gameStartWindow();
                        int seed = this.getSeed();
                        this.createWorld(seed);

                        this.ter.initialize(width, height);
                        this.ter.renderFrame(this.world);

                        this.interactivePlay();
                        // after game win
                        ter.initialize(width,height);
                        StdDraw.clear(StdDraw.BLACK);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.setFont(new Font("Sans Serif", Font.PLAIN, 32));
                        StdDraw.text(20, 25, "You Win!!");
                        StdDraw.text(20, 20, "你这个猫奴");
                        StdDraw.show();
                        break;

                    case 'l':
                    case 'L':
                        this.loadGame();
                        break;

                    case 'q':
                    case 'Q':
                        System.exit(0);
                        break;
                }
            }
        }
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        playWithKeyboard = false;

        StringIterator s = new StringIterator(input);

        //for (int i = 1; i < input.length(); i += 1)
        while (s.hasNext()) {
            char c = s.next();
            switch (c) {
                case 'n':
                case 'N':
                    int seed = this.getSeed(s);
                    this.createWorld(seed);
                    this.interactivePlay(s);
                    //after game win

                    break;

                case 'l':
                case 'L':
                    this.loadGame(s);
                    break;

                case 'q':
                case 'Q':
                    System.exit(0);
                    break;
            }

        }

        return this.world;
    }

    public static void main(String[] args) {
        Game g = new Game();
        //g.createWorld(1003);
        //g.createHorizontalHallWay(new Position(0,20), new Position(79,20));
        //g.createVertialHallWay(new Position(20,29), new Position(20,20));
        //g.ter.renderFrame(g.world);
        /*
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                System.out.println(c);
            }
        }
        */
        //g.ter.initialize(width, height);

    }
}
