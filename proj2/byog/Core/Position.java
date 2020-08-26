package byog.Core;

public class Position {
    protected int x;
    protected int y;

    public Position(int xp, int yp) {
        x = xp;
        y = yp;
    }

    @Override
    public boolean equals(Object pos) {
        Position p = (Position) pos;
        return (this.x == p.x) && (this.y == p.y);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + x;
        hash = hash * 31 + y;
        return hash;
    }

    /**
     * p[1,3,5,7] is the above,below,right,next position.
     * @return
     */
    public Position[] surrounding() {
        Position[] p = new Position[8];
        p[0] = new Position(x - 1, y - 1);
        p[1] = new Position(x - 1, y);
        p[2] = new Position(x - 1, y + 1);
        p[3] = new Position(x, y - 1);
        p[4] = new Position(x + 1, y - 1);
        p[5] = new Position(x + 1, y);
        p[6] = new Position(x + 1, y + 1);
        p[7] = new Position(x, y + 1);

        return p;
    }

    public boolean near(Position p) {
        return ((Math.abs(p.x - x) == 1 && p.y == y)
                || (p.x == x && Math.abs(p.y - y) == 1));
    }
}
