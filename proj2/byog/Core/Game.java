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

import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int width = 80;
    public static final int height = 30;
    public TETile[][] world;
    public int seed;
    public Position characterPos;

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
        int numOfRooms = RandomUtils.uniform(r, 4, 14);

        CreateMap.createRandomMap(r, world, numOfRooms);
        this.characterPos = CreateMap.createCharacter(r, world);

    }

    boolean fitInMap(Position BL, Position UR) {
        if (BL.x > UR.x || BL.y > UR.y) {
            throw new RuntimeException("the two points cannot form a square");
        }
        if (BL.x < this.width && BL.y < this.height && UR.x < this.width && UR.y < this.height
                && BL.x >= 0 && BL.y >= 0 && UR.x >= 0 && UR.y >= 0) {
            return true;
        }
        return false;
    }

    boolean fitInMap(Position p) {
        if (p.x < this.width && p.y < this.height
                && p.x >= 0 && p.y >= 0) {
            return true;
        }
        return false;
    }


    public boolean notOccupied(Position BL, Position UR) {
        if (!fitInMap(BL, UR)) {
            throw new RuntimeException("room not fit in map");
        }
        for (int i = 0; i < UR.x - BL.x; i += 1) {
            for (int j = 0; j < UR.y - BL.y; j += 1) {
                if (world[i + BL.x][j + BL.y] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    public TETile worldAt(Position p) {
        if (!fitInMap(p)) {
            return null;
        }
        return world[p.x][p.y];
    }

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



    public void loadGame(){
        System.out.println("loaded!");
    }

    public void interactivePlay() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
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
                    ter.renderFrame(this.world);
                }
            }
        }
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

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

        StringIterator s = new StringIterator(input);

        //for (int i = 1; i < input.length(); i += 1)
        while (s.hasNext()) {
            char c = s.next();
            switch (c) {
                case 'n':
                case 'N':
                    int seed = this.getSeed(s);
                    this.createWorld(seed);
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
