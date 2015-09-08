// Name: Yige Cao
// USC loginid: 6796074453
// CS 455 PA4
// Fall 2014


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenText {

   public static void main(String[] args)  {
	   
	   //Vairous error checking for cmd input, prefixLength and numWords limitations
	   if (args.length < 4 || args.length > 5){
		   System.out.println("ERROR: Invalid number of cmd line arguments! Min 4, max 5.");
	   }
	   else if (args.length == 5 && !args[0].equals("-d") ){
		   System.out.println("ERROR: Invalid cmd option.");
	   }
	   else{
		   if (args.length == 4){//this is for normal mode
			   int prefixLength = Integer.parseInt(args[0]);
			   int numWords = Integer.parseInt(args[1]);
			   
			   if (numWords<0){
				   System.out.println("ERROR: numWords cannot be less than 0");
				   System.exit(1);
			   }
			   else if (prefixLength<1){
				   System.out.println("ERROR: prefixLength cannot be less than 1");
				   System.exit(1);
			   }
			   
			   String sourceFile = args[2];
			   String outFile = args[3];
			   try{//catch exceptions
	    		   PrintWriter out = new PrintWriter(new FileWriter(outFile));
				   RandomTextGenerator randText = new RandomTextGenerator(prefixLength, numWords, sourceFile, false);
				   
				   if (randText.sourceFileCount <= prefixLength){
					   System.out.println("ERROR: length of sourceFile cannot be less than prefixLength");
					   System.exit(1);
				   }
				   
				   randText.writeFile(out);
				   out.close();
			   }
			   catch(FileNotFoundException exception) {
				   System.out.println("ERROR: sourceFile does not exist.");
			   }
			   catch(IOException exception) {
				   System.out.println("ERROR: cannot write to outFile.");
			   }
		   }
		   
		   else{//debug mode active
			   int prefixLength = Integer.parseInt(args[1]);
			   int numWords = Integer.parseInt(args[2]);
			   
			   if (numWords<0){
				   System.out.println("ERROR: numWords cannot be less than 0");
				   System.exit(1);
			   }
			   else if (prefixLength<1){
				   System.out.println("ERROR: prefixLength cannot be less than 1");
				   System.exit(1);
			   }
			   
			   String sourceFile = args[3];
			   String outFile = args[4];
			   try{
	    		   PrintWriter out = new PrintWriter(new FileWriter(outFile));
				   RandomTextGenerator randText = new RandomTextGenerator(prefixLength, numWords, sourceFile, true);
				   System.out.print(randText.sourceFileCount);
				   if (randText.sourceFileCount <= prefixLength){
					   System.out.println("ERROR: length of sourceFile cannot be less than prefixLength");
					   System.exit(1);
				   }
				   
				   randText.writeFile(out);
				   out.close();
			   }
			   catch(FileNotFoundException exception) {
				   System.out.println("ERROR: sourceFile does not exist.");
			   }
			   catch(IOException exception) {
				   System.out.println("ERROR: cannot write to outFile.");
			   }
		   }

	   }
   }
}