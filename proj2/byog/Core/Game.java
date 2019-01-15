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
        Position[] preR = createRoom(r);

        for (int i = 1; i < 11; i += 1) {
            Position[] curR = createRoom(r);
            connectRoom(r, preR, curR);
            preR = curR;
            //System.out.println("new connect");
        }
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

    public Position[] createRoom(Random r) {
        int rWidth = RandomUtils.uniform(r, 4, 14);
        int rHeight = RandomUtils.uniform(r, 4, 14);
        int rX = RandomUtils.uniform(r, 0, width - rWidth);
        int rY = RandomUtils.uniform(r, 0, height - rHeight);


        Position BL = new Position(rX, rY);
        Position UR = new Position(rX + rWidth, rY + rHeight);

        if (!notOccupied(BL, UR)) {
            Position[] p = createRoom(r);
            //System.out.println("new room");
            return p;
        }

        for (int i = 0; i < rWidth; i += 1) {
            for (int j = 0; j < rHeight; j += 1) {
                if (i == 0 || i == rWidth - 1 || j == 0 || j == rHeight - 1){
                    world[i + rX][j + rY] = Tileset.WALL;
                } else {
                    world[i + rX][j + rY] = Tileset.FLOOR;
                }
            }
        }
        return new Position[]{BL, UR};


    }

    public void createHorizontalHallWay(Position p1, Position p2) {
        if (p1.y != p2.y) {
            throw new RuntimeException("cannot create horizontal hall way from these positions");
        }
        if (p1.equals(p2)) {
            return;
        }
        int displacement = p2.x - p1.x;
        int increment = displacement / Math.abs(displacement);
        for (int i = 0; i <= Math.abs(displacement); i += 1) {
            this.world[p1.x + increment * i][p1.y] = Tileset.FLOOR;
            if (this.world[p1.x + increment * i][p1.y - 1] == Tileset.NOTHING) {
                this.world[p1.x + increment * i][p1.y - 1] = Tileset.WALL;
            }
            if (this.world[p1.x + increment * i][p1.y + 1] == Tileset.NOTHING) {
                this.world[p1.x + increment * i][p1.y + 1] = Tileset.WALL;
            }
        }

    }
    public void createVertialHallWay(Position p1, Position p2) {
        if (p1.x != p2.x) {
            throw new RuntimeException("cannot create vertical hall way from these positions");
        }
        if (p1.equals(p2)) {
            return;
        }
        int displacement = p2.y - p1.y;
        int increment = displacement / Math.abs(displacement);
        for (int i = 0; i <= Math.abs(displacement); i += 1) {
            this.world[p1.x][p1.y + increment * i] = Tileset.FLOOR;
            if (this.world[p1.x - 1][p1.y + increment * i] == Tileset.NOTHING) {
                this.world[p1.x - 1][p1.y + increment * i] = Tileset.WALL;
            }
            if (this.world[p1.x + 1][p1.y + increment * i] == Tileset.NOTHING) {
                this.world[p1.x + 1][p1.y + increment * i] = Tileset.WALL;
            }
        }

    }

    public void createCornorHallWay(Position p) {
        Position p1 = new Position(p.x - 1, p.y - 1);
        Position p2 = new Position(p.x + 1, p.y - 1);
        Position p3 = new Position(p.x - 1, p.y + 1);
        Position p4 = new Position(p.x + 1, p.y + 1);

        Position[] ps = new Position[]{p1,p2,p3,p4};

        for (Position i : ps) {
            if (world[i.x][i.y] == Tileset.NOTHING) {
                this.world[i.x][i.y] = Tileset.WALL;
            }
        }
    }

    /**
     * connect two rooms using a 2 segment hallway
     * @param r random number
     * @param preR the BR and UR points of previous created room
     * @param curR the BR and UR points of current created room
     */
    public void connectRoom(Random r, Position[] preR, Position[] curR) {
        // determine the origin of hall way in both rooms
        int preX = RandomUtils.uniform(r, preR[0].x + 1, preR[1].x - 1);
        int preY = RandomUtils.uniform(r, preR[0].y + 1, preR[1].y - 1);
        int curX = RandomUtils.uniform(r, curR[0].x + 1, curR[1].x - 1);
        int curY = RandomUtils.uniform(r, curR[0].y + 1, curR[1].y - 1);

        // curvature determine which direction of the hallway creates first
        // if curvature == 0, a vertical hall way will be created from preR to the vertical position of curR first
        // if curvature == 1, a horizontal hall way will be created first
        int curvature = RandomUtils.uniform(r, 0, 2);

        switch (curvature) {
            case 0:
                createVertialHallWay(new Position(preX, preY), new Position(preX, curY));
                createHorizontalHallWay(new Position(curX, curY), new Position(preX, curY));
                createCornorHallWay(new Position(preX, curY));
                break;
            case 1:
                createHorizontalHallWay(new Position(preX, preY), new Position(curX, preY));
                createVertialHallWay(new Position(curX, curY), new Position(curX, preY));
                createCornorHallWay(new Position(curX, preY));
                break;
        }

    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        StdDraw.filledSquare(0.5, 0.5, 0.5);

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
                    case ('n' | 'N'):
                        System.out.println("started!");
                        gameStartWindow();
                        int seed = this.getSeed();
                        this.createWorld(seed);

                        this.ter.initialize(width, height);
                        this.ter.renderFrame(this.world);
                        break;
                    case ('l' | 'L'):
                        this.loadGame();
                        break;
                    case ('q' | 'Q'):
                        System.exit(0);
                        break;
                }
            }
        }
    }

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
        while (s.hasNext()) {
            char c = s.next();
            switch (c) {
                case ('n' | 'N'):
                    int seed = this.getSeed(s);
                    this.createWorld(seed);
                    break;
                case ('l' | 'L'):
                    this.loadGame();
                    break;
                case ('q' | 'Q'):
                    System.exit(0);
                    break;
            }

        }

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
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
