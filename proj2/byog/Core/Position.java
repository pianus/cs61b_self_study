package byog.Core;

public class Position implements java.io.Serializable{
    public int x;
    public int y;
    public static final Position leftUnit = new Position(-1, 0);
    public static final Position rightUnit = new Position(1, 0);
    public static final Position upUnit = new Position(0, 1);
    public static final Position downUnit = new Position(0, -1);

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position rotatedPosition(int widthAfter, int heightAfter, boolean clockwise) {
        if (clockwise) {
            return new Position(this.y,heightAfter - this.x - 1);
        } else {
            return new Position(widthAfter - this.y - 1, this.x);
        }
    }

    public static Position add(Position p1, Position p2) {
        return new Position(p1.x + p2.x, p1.y + p2.y);
    }
    public static Position relativeDirection(Position fromP, Position targetP) {
        return new Position(targetP.x - fromP.x, targetP.y - fromP.y);
    }

    public static Position[] directionRankin(Position p) {
        Position p1;
        Position p2;
        Position p3;
        Position p4;

        if (p.x == 0) {
            p1 = new Position(0, p.y / Math.abs(p.y));
            p2 = Position.leftUnit;
            p3 = Position.rightUnit;
            p4 = new Position(0, -p.y / Math.abs(p.y));
        } else if (p.y == 0) {
            p1 = new Position(p.x / Math.abs(p.x), 0);
            p2 = Position.upUnit;
            p3 = Position.downUnit;
            p4 = new Position(-p.x / Math.abs(p.x), 0);
        } else if (Math.abs(p.x) >= Math.abs(p.y)) {
            p1 = new Position(p.x / Math.abs(p.x), 0);
            p2 = new Position(0, p.y / Math.abs(p.y));
            p3 = new Position(0, -p.y / Math.abs(p.y));
            p4 = new Position(-p.x / Math.abs(p.x), 0);
        } else {
            p1 = new Position(0, p.y / Math.abs(p.y));
            p2 = new Position(p.x / Math.abs(p.x), 0);
            p3 = new Position(-p.x / Math.abs(p.x), 0);
            p4 = new Position(0, -p.y / Math.abs(p.y));
        }

        return new Position[]{p1,p2,p3,p4};
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