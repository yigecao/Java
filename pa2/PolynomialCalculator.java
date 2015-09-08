// Name: Yige Cao	
// USC loginid: 6796074453
// CS 455 PA2
// Fall 2014

import java.util.Scanner;
import java.util.ArrayList;

public class PolynomialCalculator{
	
	public static final int ARRAY_LEN_MAX=10;
	
	
	//main method to create an interactive interface for user to enter commands
	//and receive corresponding results and messages
	public static void main(String[] args) {
		
		//call the initialize() method to initialze the array of Polynomials we are
		//going to use in the calculator.
		//Print out welcome msg and introduction
		//take in user input, call method to manipulate input 
		
		Polynomial [] initialArray = initialize();
		System.out.println("Enter cmd for Polynomial Calculator. Type 'help' for command options.");
		System.out.print("cmd> ");
		Scanner in = new Scanner(System.in);
		String [] cmdElements = getUserCmd(in);
		
		//if the user didn't enter "quit" continue with the program
		while (!cmdElements[0].equalsIgnoreCase("quit")){
			
			//whether "help" has been entered, if so, call on the help() method to print help msg.
			//if not, then the user has inputed junk that has a length of 1, print error msg.
			if (cmdElements.length == 1){
				
				if (cmdElements[0].equalsIgnoreCase("help")){
					help();
				}
				else { System.out.println("ERROR: Illegal command. Type 'help' for command options.");}
				
			}
			
			//check for create, print, eval and check whether the args that follow these keywords
			//are valid by calling the errorIndexCheck() method.
			else if (cmdElements.length == 2 && errorIndexCheck(cmdElements)){
									
				int indexInt = Integer.parseInt(cmdElements[1]);

				if (cmdElements[0].equalsIgnoreCase("create")){
					create(indexInt, initialArray);
				}
				
				else if ((cmdElements[0].toLowerCase()).equalsIgnoreCase("print")){
					System.out.println(initialArray[indexInt].toFormattedString());
				}
				
				else if ((cmdElements[0].toLowerCase()).equalsIgnoreCase("eval")){
					eval(indexInt, initialArray);
				}
				
				else{System.out.println("ERROR: Illegal command. Type 'help' for command options.");}
					
			}
			
			//since "add" is the only command here that takes in 4 args, we check this specifically.
			else if (cmdElements.length == 4){
				
				//also check whether the 4 input string is indeed "add" followed by valid args.
				if ((cmdElements[0].toLowerCase()).equalsIgnoreCase("add") && errorIndexCheck(cmdElements)){
					
					int destIndex = Integer.parseInt(cmdElements[1]);
					int s1_Index = Integer.parseInt(cmdElements[2]);
					int s2_Index = Integer.parseInt(cmdElements[3]);
					add(destIndex, s1_Index, s2_Index, initialArray);
					
				}
				else {System.out.println("ERROR: illegal index for a poly. must be between 0 and 9, inclusive");}
					
			}
			
			//if no keywords were entered, or length of input was beyond 4, then print error msg.
			else{System.out.println("ERROR: Illegal command. Type 'help' for command options.");} 
			
			//if "quit" hasn't been entered yet, continue to ask the user for input via the getUserCmd() method.
			System.out.print("cmd> ");
			cmdElements = getUserCmd(in);
		}		
	}
	
/********************************HELPER METHODS*********************************************/
	
	//Below are the methods that were used to simplify the code in the main() method.
	
	//initialize array for Polynomial by creating an array of a prefixed length and
	//adding zero Polynomials into each element of the array.
	public static Polynomial[] initialize(){
		Polynomial [] initialArray = new Polynomial [ARRAY_LEN_MAX]; 
		for (int i=0; i<initialArray.length; i++){
			initialArray[i] = new Polynomial();
		}
		return initialArray;
	}
	
	//handle user input commands by reading it into a line of String, then splitting
	//and adding each element of that line into an array of String for further checks.
	public static String [] getUserCmd(Scanner in){
		String cmdLine = in.nextLine();
		String [] cmdElements = cmdLine.split(" ");
		return cmdElements;
	}
	
	//handle input "help" and print corresponding msgs on the console screen.
	public static void help(){
		System.out.println("Command Options: ");
		System.out.println("1) Create a Polynomial: 'create (index)'.");
		System.out.println("2) Print a Polynomial: 'print (index)'.");
		System.out.println("3) Add two Polynomials: 'add (Destination index) (Source index1) (Source index2)'.");
		System.out.println("4) Evaluate a Polynomial: 'eval (index)'.");
		System.out.println("5) Quit program: 'quit'.");
	}
	
	//handling create cmd
	//prompt the user to enter the sequence of coeff-expon pairs
	//scan strings in the line of input to determine the validity of the sequence
	//if invalid, return the Polynomial Array unchanged and print error msg
	//perform two more checks on the Arraylist of coeff and expon doubles
	//first check for even length, if not even length, delete the last number in the
	//arraylist since it's a coefficient with no pairing exponent.
	//next check for negative expon and change any to positive ones and print warning msg.
	public static Polynomial [] create(int index, Polynomial [] pArray){
		
		System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
		Scanner input = new Scanner(System.in);
		String inLine = input.nextLine();
		ArrayList<Double> polyInfo = new ArrayList<Double>();
		Scanner lineScan = new Scanner(inLine);
		
		if (!lineScan.hasNextDouble()){
			System.out.println("ERROR: Illegal type entered in command! Only enter Double and Int! No change made!");
			return pArray;
		}
		else {
			
			while (lineScan.hasNextDouble()){
				polyInfo.add(lineScan.nextDouble());
			}

			if (polyInfo.size()%2 != 0){
				System.out.println("WARNING: Illegal number of coeff-power pairs entered! Last Coefficient deleted!");
				polyInfo.remove(polyInfo.size()-1);//ignore the last item
			}
			
			for (int i=0; i<polyInfo.size(); i+=2){
				if (polyInfo.get(i+1)<0){
					System.out.println("WARNING: Exponent entered is Negative, will treat as Positive!");
					polyInfo.set(i+1, Math.abs(polyInfo.get(i+1)));
				}
			}
			
			//By adding the Polynomial one at a time we guarantee that no matter what
			//order the coeff-power pairs were entered, they will be stored in a reversed order
			pArray[index] = new Polynomial();
			for (int i=0; i<polyInfo.size(); i+=2){
				double Coeff = polyInfo.get(i);
				double Expon = polyInfo.get(i+1);
				pArray[index] = pArray[index].add(new Polynomial(new Term(Coeff, (int)Expon)));
			}
			return pArray;
		}
		
	}
	
	//handling eval cmd
	public static void eval(int index, Polynomial [] pArray){
		System.out.print("Enter a floating point value for x: ");
		Scanner input = new Scanner(System.in);
		
		//sanity check
		if (!input.hasNextDouble()){
			System.out.println("ERROR: Illegal type entered as value for x! No change made!");	
		}
		else{
			double value = input.nextDouble();
			System.out.println(pArray[index].eval(value));
		}
	}
	
	//handle add cmd
	public static void add(int dest, int source1, int source2, Polynomial [] pArray){
		pArray[dest] = pArray[source1].add(pArray[source2]);
	}

	//handle error index by checking whether the string after the first element in the 
	//cmd is of single length or multiple length. if the string has many characters 
	//the whole cmd is invalid. else if after casting the string into an int the number
	//is not with in the index of the Polynomial [] then the cmd is invalid also.
	public static boolean errorIndexCheck(String [] cmd){
		for (int i=1; i<cmd.length; i++){
			if (cmd[i].length()<=1){
				int index = Integer.parseInt(cmd[i]);
				if (index<0 || index>9){
					return false;
				}
			}
			else{
				return false;
			}
		}
		
		return true;
		
	}
}