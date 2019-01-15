package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class CreateMap {
    public Random r;
    public int height;
    public int width;
    public TETile[][] world;

    public CreateMap(Random r, int height, int width, TETile[][] world) {
        this.r = r;
        this.height = height;
        this.width = width;
        this.world = world;
    }


    public TETile[][] create(int numOfRooms) {

        Position[] preR = createRoom(r);

        for (int i = 0; i < numOfRooms - 1; i += 1) {
            Position[] curR = createRoom(r);
            connectRoom(r, preR, curR);
            preR = curR;
            //System.out.println("new connect");
        }
        return this.world;
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


}
