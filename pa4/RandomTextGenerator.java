// Name: Yige Cao
// USC loginid: 6796074453
// CS 455 PA4
// Fall 2014


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class RandomTextGenerator {
	
	//variables declarations
	public final int CHAR_NUM = 80;
	private int prefixLength;
	private int numWords;
	private boolean debugMode;
	private Prefix initPrefix;
	private Prefix newPrefix;
	private Map<Prefix, ArrayList<String>> mapPrefix;
	private ArrayList<String> fileContent;
	private ArrayList<String> randomWords;
	private ArrayList<Prefix> keysList;
	private Random rand;
	private final int SEED = 1;
	private boolean emptyFlag = false;//checking for special source file empty case
	public int sourceFileCount;
	//private File source;
	private String sFileName;
	
	
	//read in source file, prefixLength, numWords and debug mode status to create
	public RandomTextGenerator(int pLength, int wordNum, String sourceFile, boolean debug) throws FileNotFoundException{
		if (debug){
			debugMode = true;
			prefixLength = pLength;
			numWords = wordNum;
			sFileName = sourceFile;
			fileContent = new ArrayList<String> ();
			randomWords = new ArrayList<String> ();
			readFile();
			rand = new Random(SEED);
			initPrefix = createInitPrefix();
			mapPrefix = new HashMap<Prefix, ArrayList<String>> ();
			createMapPrefix();
			genRandomWord();
		}
		else{
			debugMode = false;
			prefixLength = pLength;
			numWords = wordNum;
			sFileName = sourceFile;
			fileContent = new ArrayList<String> ();
			randomWords = new ArrayList<String> ();
			readFile();
			rand = new Random();
			initPrefix = createInitPrefix();
			mapPrefix = new HashMap<Prefix, ArrayList<String>> ();
			createMapPrefix();
			genRandomWord();
		}
	}
	
	//read in the file's contents, separate by whitespace
	private void readFile() throws FileNotFoundException{
		File file = new File(sFileName);
		Scanner in = new Scanner(file);
		while (in.hasNextLine()){
			String [] line = in.nextLine().split(" ");
			//System.out.println(line[0] + line[1]);
			for (int i=0; i<line.length; i++){
				fileContent.add(line[i]);
				sourceFileCount++;
			}
		}
		
		//if the file is empty then no point in going through the rest of the code
		//set a flag to remind the writeback to file function to not write anything
		if (fileContent.isEmpty()){
			emptyFlag = true;
		}
		
		in.close();
	}
	
	//create the initial prefix by setting up the range of where to get the prefix from 
	//the sourcefile content
	private Prefix createInitPrefix(){
		
		if (emptyFlag){
			return new Prefix();
		}
		
		int idx = rand.nextInt(fileContent.size()-prefixLength+1);
		LinkedList<String> initPreLList = new LinkedList<String> ();
		initPreLList.addLast(fileContent.get(idx));
		
		if(prefixLength == 1){
			return new Prefix(initPreLList);
		}
		else{
			for (int i=1; i<= prefixLength-1; i++){
				initPreLList.addLast(fileContent.get(idx+i));
			}
			
			if (debugMode){
				System.out.println("DEBUG: chose a new initial prefix: " + initPreLList.toString());
			}
			
			return new Prefix(initPreLList);
		}

	}
	
	//main method for this class includes debug mode
	//outter while loop doesn't exit until the desired number of words has been generated
	//special case condition is written to help catch the case where the prefix ends at the end of the file.
	private void createMapPrefix(){
		
		if(emptyFlag){
			return;
		}
		
		newPrefix = new Prefix(initPrefix.get());//always change newPrefix
		
		if(debugMode){
			System.out.println("DEBUG: prefix: " + newPrefix.toString());
		}
		
		int wordCount = 0;
		int endFlag = 0;
		
		while (wordCount != numWords){
			
			int start=0;
			int end = prefixLength-1;
			int count = 0;
			
			while (start <= fileContent.size()-prefixLength){
				for (int i=start; i <= end; i++){
					if (fileContent.get(i) == newPrefix.get().get(i-start)){
						count++;
					}
					else {
						count = 0;
					}
				}
				
				//special condition to reselect a prefix and continue with the map generation
				if (count == prefixLength && end == fileContent.size()-1){//only at the end of the inner while loop this happens
					
					if(debugMode){
						System.out.println("DEBUG: successors: <END OF FILE>");
					}
					
					newPrefix = createInitPrefix();//prefix just at the end of the file, change to a new one
					count = 0;
					endFlag = 1;
					
					if(debugMode){
						System.out.println("DEBUG: prefix: " + newPrefix.toString());
					}
				}
				
				else if (count == prefixLength){//found the prefix match in the file
					
					if (mapPrefix.containsKey(newPrefix)){//check if already inside the map
						mapPrefix.get(newPrefix).add(fileContent.get(end+1));
						count = 0;
						wordCount++;
						
						if(debugMode){
							System.out.println("DEBUG: successors: " + mapPrefix.get(newPrefix).toString());
						}
						
					}
					else{
						ArrayList<String> successors = new ArrayList<String> ();
						successors.add(fileContent.get(end+1));//add the word following the prefix
						mapPrefix.put(newPrefix, successors);
						count = 0;
						wordCount++;
						
						if(debugMode){
							System.out.println("DEBUG: successors: " + successors.toString());
						}
					}			
				}
				
				start++;
				end++;
			}
			
			
			if (endFlag == 1){
				endFlag = 0;
				continue;
			}
			else {
				//after one pass through the file create a new prefix based on the number of occurances a word had
				//in the successor array
				String genWord = mapPrefix.get(newPrefix).get(rand.nextInt(mapPrefix.get(newPrefix).size()));
				newPrefix = newPrefix.shiftIn(genWord);
				
				if(debugMode){
					System.out.println("DEBUG: word generated: " + genWord);
				}
				
				if(debugMode){
					System.out.println("DEBUG: prefix: " + newPrefix.toString());
				}
			}	
		}
		
		//generate a key set of all the prefix in the map
		keysList = new ArrayList<Prefix> (mapPrefix.keySet());
		
	}

	
	//until the words amount generated has been satisfied, keep finding a random key and selecting random values
	private void genRandomWord(){
		if (emptyFlag){
			return;
		}
		
		Prefix randPrefix = keysList.get(rand.nextInt(keysList.size()));
		String genWord = mapPrefix.get(randPrefix).get(rand.nextInt(mapPrefix.get(randPrefix).size()));
		int count = 0;
		//System.out.println(numWords);
		while (count != numWords){
			randomWords.add(genWord);
			randPrefix = keysList.get(rand.nextInt(keysList.size()));
			genWord = mapPrefix.get(randPrefix).get(rand.nextInt(mapPrefix.get(randPrefix).size()));
			count++;
		}
	}
	
	//make sure every line can only have 80 char and doesn't end in white space.
	//if the next word is too long, make sure it goes to the next line instead of 
	//cramming in the same one.
	public void writeFile(PrintWriter out){
		if (emptyFlag){
			out.print("");
		}
		else{
			out.print(randomWords.get(0));
			int charNum = randomWords.get(0).length();
			for (int i=1; i<randomWords.size(); i++){
				if (charNum <= CHAR_NUM - randomWords.get(i).length()-1){//check for the potential limit of the line char numbers
					out.print(" " + randomWords.get(i));
					charNum += randomWords.get(i).length()+1;
				}
				else {
					out.println("");
					charNum = 0;
				}
			}
		}
	}
	
	

}
