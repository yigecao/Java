/**
 * MazeCoord class
 * 
 * Immutable class for storing maze coordinates. Note that the first argument to
 * the constructor is a row, and the second is a column. (So if rows go down the
 * screen, this is the reverse of the Java GUI coordinates, where x, the
 * horizontal part of the coordinate, comes first.)
 *
 * Do not modify this file.
 *
 */

public class MazeCoord {
   final private int row; // final (non-static) means it can't be updated once         
   final private int col; //      it's initialized

   public MazeCoord(int row, int col) {
      this.row = row;
      this.col = col;
   }

   public int getRow() { return row; }

   public int getCol() { return col; }

   public String toString() {
      return "MazeCoord[row=" + row + ",col=" + col + "]";
   }

}
