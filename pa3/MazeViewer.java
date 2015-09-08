// Name: Yige Cao
// USC loginid: 6796074453
//CS 455 PA3
//Fall 2014

import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze:
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 
 * The top left is the maze entrance, and the bottom right is the maze exit.
 * 
 */


public class MazeViewer {

   public static void main(String[] args)  {

      String fileName = "";

      try {

         if (args.length < 1) {
            System.out.println("ERROR: missing file name command line argument");
         }
         else {
            fileName = args[0];

            int[][] mazeData = readMazeFile(fileName);
            
            JFrame frame = new MazeFrame(mazeData);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
         }

      }
      catch (FileNotFoundException exc) {
         System.out.println("File not found: " + fileName);
      }
      catch (IOException exc) {
         exc.printStackTrace();
      }
   }

   
   /**
      readMazeFile reads in and returns a maze from the file whose name is
      String given. The file format is shown in the MazeViewer class comments.
      
      @param fileName
                the name of a file to read from
      @returns 
               the array with maze contents. 0's correspond to 0's in the file
               and 1's correspond to 1's in the file. The first dimension is
               which row, and the second is which column. E.g. if the file
               started with 3 10, it would mean this array returned would have
               3 rows, and 10 columns.
      @throws FileNotFoundException
                 if there's no such file (subclass of IOException)
      @throws IOException
                 (hook given in case you want to do more error-checking.
                  that would also involve changing main to catch other 
                  exceptions)
    */
   private static int[][] readMazeFile(String fileName) throws IOException {
	   
	   //scan the file for the first line and store it inside a String array splited by white space
	   File file = new File(fileName);
	   Scanner scan = new Scanner(file);
	   String [] firstLine = scan.nextLine().split(" ");
	   
	   //grab the row and col info from the firstline of the file
	   int rowNum = Integer.parseInt(firstLine[0]);
	   int colNum = Integer.parseInt(firstLine[1]);
	   
	   System.out.println("row: " + rowNum);
	   System.out.println("col: " + colNum);

	   //setup the size of the 2D array that we are going to use for storing the maze values
	   int[][] mazeData = new int[rowNum][colNum];
	   
	   //scan each row inside the maze file and split every character, store them by iterating 
	   //through the cols and increase the row one by one until no more lines are present inside
	   //the file
	   int row = 0;
	   while (scan.hasNextLine()){
		   String [] line = scan.nextLine().split("");
		   for (int j=0; j<mazeData[0].length; j++){
			   mazeData[row][j] = Integer.parseInt(line[j]);
		   }
		   row++;
	   }
		   
	   return mazeData; 
   }

}


