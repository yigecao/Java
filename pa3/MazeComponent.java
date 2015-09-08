// Name: Yige Cao
// USC loginid: 6796074453
// CS 455 PA3
// Fall 2014

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;



/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   
   private static final int START_X = 10;  // where to start drawing maze in frame
   private static final int START_Y = 10;  // (i.e., upper-left corner)
   
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;

   private Rectangle mazeBorder;
   private int[][] mazeVal;
   
   private int x;
   private int xOrig;
   private int y;
   private int yOrig;
   
   private LinkedList<MazeCoord> route;
   private ListIterator<MazeCoord> iterator;
   
   private Maze mazeForRepaint;
   /**
      Constructs the component.
      @param maze   the maze to display
      
      take in the border for drawing a transparent box
      take in the value of each element to know when to draw a black filled box as a wall
      also, we copy the maze object in whole for checking whether the path has been found or not
   */
   public MazeComponent(Maze maze) 
   {      
	   mazeBorder = new Rectangle(START_X, START_Y, maze.numCols()*BOX_WIDTH, maze.numRows()*BOX_HEIGHT);
	   mazeVal = maze.returnMazeVal();//grab maze info
	   xOrig = (int)mazeBorder.getX();//mark the starting point's x, where each row starts
	   yOrig = (int)mazeBorder.getY();//mark where the staring point Y is
	   
	   //****************************
	   mazeForRepaint = maze;
	   //****************************
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
     
     first draw the border
     next within the border read in the value of 2D array and do black box for 1, blank for 0, and green for end point
   */
   public void paintComponent(Graphics g)
   {
	   g.drawRect((int)mazeBorder.getX(), (int)mazeBorder.getY(), (int)mazeBorder.getWidth(), (int)mazeBorder.getHeight());
	   
	   x = (int)mazeBorder.getX();//this point moves after each val read in
	   y = (int)mazeBorder.getY();
	 
	   for(int i=0; i<mazeVal.length; i++){//row
		   for(int j=0; j<mazeVal[0].length; j++){//col
			   if (i==mazeVal.length-1 && j== mazeVal[0].length-1 && mazeVal[i][j] == 0){//reached endPoint, paint green rect
				   g.setColor(Color.GREEN);
				   g.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
			   }
			   else if (i==mazeVal.length-1 && j== mazeVal[0].length-1 && mazeVal[i][j] == 1){//endpoint is a wall
				   g.setColor(Color.BLACK);
				   g.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
			   }
			   else if (mazeVal[i][j] == 1){//if a wall, draw black box filled
				   g.setColor(Color.BLACK);
				   g.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);//draw filled box
				   x += BOX_WIDTH;//move to the next place u need to start drawing
			   }
			   else if (mazeVal[i][j] == 0){//if a path
				   x += BOX_WIDTH;//move to the next place u need to start drawing
			   }
		   }
		   x = xOrig;//x must return to the Original value because each row start at the same X although at different Y
		   y += BOX_HEIGHT;//only after going thru a row can you increase your col

	   }
	   

	   //check whether the maze has finished a search or not, if there is a path we proceed to draw it
	   if (mazeForRepaint.finishedSearch()){
		   
		   //setup for LinkedList iteration
		   route = mazeForRepaint.getPath();
		   iterator = route.listIterator();
		   
		   //This is a crucial arraylist which will store the point values for each segment of the path
		   //the path between two squares on the maze is from one's center to the other and this arraylist
		   //stores that info
		   ArrayList<Integer> points = new ArrayList<Integer>();
		   
		   //iterate through the entire linkedlist to transform all the coordinate(square) info into 
		   //usefull points info for drawing by the below algorithm in a (x1,y1, x2,y2,....) fashion
		   while (iterator.hasNext()){
			   MazeCoord box = iterator.next();
			   points.add(box.getRow()*BOX_HEIGHT+yOrig+BOX_HEIGHT/2);//where line starts for row
			   points.add(box.getCol()*BOX_WIDTH+xOrig+BOX_WIDTH/2);//where line starts for col
		   }
		   		   
		   Collections.reverse(points);//reverse all the points in this arraylist due to the path was in reverse order
		   System.out.println(points);

		   //Iterate through the arraylist and link the points together, increment in jump steps to make sure
		   //every point and line is drawn
		   for (int i=0; i<points.size()-2; i+=2){
			   g.setColor(Color.BLUE);
			   g.drawLine(points.get(i), points.get(i+1), points.get(i+2), points.get(i+3));
		   }
	   }
	   
	   
   }//end of method
   
}



