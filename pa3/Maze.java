// Name: Yige Cao
// USC loginid: 6796074453
// CS 455 PA3
// Fall 2014


import java.util.Arrays;
import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).

   Assumptions about structure of the mazeData (given in constructor), and the
   path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate (START_SEARCH_ROW,
        START_SEARCH_COL) (constants defined below)
     -- exit loc is maze coordinate (numRows()-1, numCols()-1) 
           (methods defined below)
     -- mazeData input 2D array of 0's and 1's (see public FREE / WALL 
        constants below) where the first index indicates the row. 
        e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public int START_SEARCH_COL = 0;
   public int START_SEARCH_ROW = 0;

   public static final int FREE = 0;
   public static final int WALL = 1;
   
   //Initialization of several key parameters for a Maze object
   //The content of the maze stored as int in a 2D array
   //The path variable to be filled by .search()
   //rows and cols numbers specific to the maze
   private int[][] mazeArray;
   private LinkedList<MazeCoord> path = new LinkedList<MazeCoord>();
   private int rows;
   private int cols;
   
   //Flag to be used by the .finishedSearch() helper method, proper
   //use explained later.
   public boolean flag = false;

   
   /**
      Constructs a maze.
      @param mazeData the maze to search.  See Maze class comments, above,
      for what goes in this array.
      
      We take the mazeData given here and store it into our own private variable.
      we also take in the rows and cols info as well.

    */
   public Maze(int[][] mazeData) {
	  mazeArray = mazeData;
	  rows = mazeData.length;
	  cols = mazeData[0].length;
      
   }


   /**
      Returns the number of rows in the maze
      @return number of rows
    */
   public int numRows() {
	   return rows;
   }


   /**
     Returns the number of columns in the maze
     @return number of columns
   */
   public int numCols() {
      return cols;
   } 


   /**
      Gets the maze data at loc.
      @param loc the location in maze coordinates
      @return the value at that location.  One of FREE and WALL
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
      
      use the location provided and MazeCoord's methods to get the row and col
      value and further get the maze's value at that spot
    */
   public int getValAt(MazeCoord loc) {

      return mazeArray[loc.getRow()][loc.getCol()]; 

   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {

      return path;   // DUMMY CODE TO GET IT TO COMPILE

   }


   /**
      Find a path through the maze if there is one.
      @return whether path was found.
    */
   public boolean search()  {  
      
	   int[][] visited = new int[rows][cols]; //initialize the visited array
		  
	   path.clear();//clr the path else there'll be a diagonal line linking Start to end drawn
	   
	   MazeCoord start = new MazeCoord(START_SEARCH_ROW, START_SEARCH_COL);//starting point always at 0,0
	   
	   //call the recursive method below and start the search, if it returns true, this means
	   //a path has been found and we can now add the last point which is the start coordinate into the
	   //linked list. Please note that path is now in a reversed order and will be corrected in MazeComponent.java
	   if (recSearch(start.getRow(),start.getCol(), visited)){
		   path.add(start);
		   flag = true;
		   return true;
	   }
	   else{
		   flag = false;
		   return false;  
	   }

   }
   
   //This is the main recursive search where we call the same func. for different directions
   //all calls must go through the base cases listed below so the value of the coordinate inside
   //the maze can be properly checked and the correct flag returned
   private boolean recSearch(int r, int c, int[][] visited){
	   
	   //*******************BASE CASE****************************************
	   if (r < 0 || r >= rows || 
		   c < 0 || c >= cols){//check if out of bounds
		   return false;
	   }
	   
	   if (mazeArray[r][c] == 1){//check for wall
		   return false;
	   }
	   
	   if (visited[r][c] == 1){//check if visited
		   return false;
	   }
	   
	   if (r == rows-1 && c == cols-1){//check if at end point
		   return true;
	   }
	   
	   //******************RE CASE*************************************
	   
	   visited[r][c] = 1;//mark as visited
	   
	   if (recSearch(r-1, c, visited)){//call the func. to search in the UP direction
		   path.add(new MazeCoord(r-1, c));//add this point to the path
		   return true;
	   }
	   
	   if (recSearch(r+1, c, visited)){//call the func. to search in the DOWN direction
		   path.add(new MazeCoord(r+1, c));//add this point to the path
		   return true;
	   }
	   
	   if (recSearch(r, c-1, visited)){//call the func. to search in the LEFT direction
		   path.add(new MazeCoord(r, c-1));//add this point to the path
		   return true;
	   }
	   
	   if (recSearch(r, c+1, visited)){//call the func. to search in the RIGHT direction
		   path.add(new MazeCoord(r, c+1));//add this point to the path
		   return true;
	   }
	   
	   return false;//if the previous call is false, then have to return false
	   
   }
   
   //A helper method that returns the value of the maze to the caller for checking
   public int[][] returnMazeVal(){
	   return mazeArray;
   }
   
   //This helper method checks whether the .search() func. has been called and whether it's 
   //successful or not. This is important because we don't want to draw an invalid path when calling
   //component repaint. This helps us to only draw the path out if there is one and if it's valid
   public boolean finishedSearch(){
	   return flag;
   }

}
