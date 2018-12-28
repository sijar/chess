/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chess.pieces;

/**
 *
 * @author sijar
 */
public class CoOrdinate {

    private int x;
    private int y;

    public CoOrdinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * 
     *
     * @param rowCol
     */
    CoOrdinate(String rowCol) {
        int numRowCol = Integer.parseInt(rowCol);
        this.x = numRowCol / 10;
        this.y = numRowCol % 10;
    }


    @Override
    public String toString() {
        return "[" + x + "," + y +"]";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoOrdinate other = (CoOrdinate) obj;
        //System.out.print("\n%% Comparing X's :" + this.x + other.x);
        //System.out.print("\n%% Comparing Y's :" + this.y + other.y);
        if((this.x == other.x) && (this.y == other.y)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.x;
        hash = 59 * hash + this.y;
        return hash;
    }

        

}
