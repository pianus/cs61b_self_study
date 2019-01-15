package byog.lab5;
import byog.Core.RandomUtils;
import static org.junit.Assert.*;
import org.junit.Test;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private int width;
    private int height;
    private int hexSize;
    private TERenderer ter = new TERenderer();
    public TETile[][] world;

    public HexWorld(int width, int height, int hexSize) {
        this.width = width;
        this.height = height;
        this.hexSize = hexSize;
        this.ter.initialize(width, height);
        this.world = new TETile[width][height];
        this.createEmptyWorld();
    }

    public void createEmptyWorld() {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static class Position {
        public int x;
        public int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object p) {
            if (this == p) {
                return true;
            }
            if (p == null) {
                return false;
            }
            if (this.getClass() != p.getClass()) {
                return false;
            }
            Position a = (Position) p;
            return (a.x == this.x && a.y == this.y);
        }
    }

    /**
     * does the tile with dominant position p fit inside this map size?
     * @param p the dominant position of tile inserted
     */
    boolean tileFitInMap(Position p) {
        if (!(p.x <= this.width && p.y <= this.height
                        && p.x >= 0 && p.y >= 0)) {
            return false;
        }
        int rightBound = p.x + 2 * this.hexSize - 2;
        int leftBound = p.x - this.hexSize + 1;
        int bottomBound = p.y;
        int upperBound = p.y + 2 * this.hexSize - 1;
        return rightBound <= this.width && upperBound <= this.height
                && leftBound >= 0 && bottomBound >= 0;
    }
    boolean tileOccupied(Position p) {
        if (! tileFitInMap(p)) {
            return false;
        }
        if (this.world[p.x][p.y] == Tileset.NOTHING) {
            return false;
        }
        return true;
    }

    private void fillTileLine(Position p, int l, TETile t) {
        for (int i = 0; i < l; i += 1) {
            world[p.x + i][p.y] = t;
        }
    }

    public void addHexagon(Position p, TETile t) {
        // make sure the hex block is inside the frame
        if (! tileFitInMap(p)) {
            return;
            //throw new IndexOutOfBoundsException();
        }
        // fill tile t at desired location
        int s = this.hexSize;
        for (int i = 0; i < s; i += 1) {
            fillTileLine(new Position(p.x - i, p.y + i), s + i * 2, t);
            fillTileLine(new Position(p.x - i, p.y + s*2 - i -1), s + i * 2, t);
        }
    }

    // positionLabel identify the position between hex, posiiton 0 is the hex above it
    // position 1 is the hex at the upperright corner
    // position 2 is the hex at the lowerright corner
    // etc
    public Position findAjdPos(Position p, int posLabel) {
        int s = this.hexSize;
        switch (posLabel) {
            case 0:  return new Position(p.x ,p.y + s * 2);
            case 1:  return new Position(p.x + s * 2 - 1,p.y + s);
            case 2:  return new Position(p.x + s * 2 - 1,p.y - s);
            case 3:  return new Position(p.x ,p.y - s * 2);
            case 4:  return new Position(p.x - s * 2 + 1,p.y - s);
            case 5:  return new Position(p.x - s * 2 + 1,p.y + s);
            default: throw new NullPointerException();
        }
    }

    public void addAdjHex(Position basePos, int posLabel, TETile t) {
        Position targetPos = this.findAjdPos(basePos, posLabel);
        this.addHexagon(targetPos, t);
    }


    public void randomFillMap(Position basePos, Random r, Set cache) {
        if (! tileOccupied(basePos)) {
            this.addHexagon(basePos, Tileset.nextRanMapTex(r));
        }
        cache.add(basePos);
        List<Integer> nexPoss = new ArrayList<>();
        for (int i = 0; i < 6; i += 1) {
            Position nexPos = findAjdPos(basePos,i);
            if (! this.tileFitInMap(nexPos)) {
                continue;
            }
            if (cache.contains(nexPos)) {
                continue;
            } else {
                cache.add(nexPos);
            }

            if (! this.tileOccupied(nexPos)) {
                this.addHexagon(nexPos, Tileset.nextRanMapTex(r));
                nexPoss.add(i);
            }
        }
        for (int i: nexPoss) {
            Position nexPos = findAjdPos(basePos,i);
            if (this.tileFitInMap(nexPos)) {
                randomFillMap(findAjdPos(basePos, i), r, cache);
            }
        }
    }

    public static void main(String[] args) {
        HexWorld hw3 = new HexWorld(60,60, 3);
        Random r = new Random(200);
        hw3.randomFillMap(new Position(20,30), new Random(1000), new HashSet<Position>());
        //hw.fillTileLine(new Position(20,20), 3, Tileset.GRASS);
        //hw.fillTileLine(new Position(20,21), 5, Tileset.GRASS);
        hw3.ter.renderFrame(hw3.world);
        //hw4.ter.renderFrame(hw4.world);

    }

}
