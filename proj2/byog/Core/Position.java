package byog.Core;

public class Position {
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