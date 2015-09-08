// Name: Yige Cao
// USC loginid: 6796074453
// CS 455 PA4
// Fall 2014

import java.util.LinkedList;
import java.util.ListIterator;

final public class Prefix{
	
	private LinkedList<String> prefix;
	
	//creates a prefix class that contains a LL object of strings 
	public Prefix(LinkedList<String> pWords){
		prefix = new LinkedList<String> (pWords); 	
	}
	
	//empty constructor
	public Prefix(){
		prefix = new LinkedList<String> ();
	}
	
	//return a shifted prefix word LL
	public Prefix shiftIn(String successors){
		Prefix returnPrefix = new Prefix(this.prefix);
		returnPrefix.prefix.removeFirst();
		returnPrefix.prefix.addLast(successors);
		return returnPrefix;
	}
	
	//return a new LL containing the info of Prefix
	public LinkedList<String> get(){
		return new LinkedList<String> (prefix);
	}
	
	public String toString(){
		ListIterator<String> iter = prefix.listIterator();
		String preString = "";
		while (iter.hasNext()){
			preString += iter.next();
			preString += " ";
		}
		return preString;
	}

	
	//overwriting methods
	public int hashCode(){
		return prefix.hashCode();
	}
	
	public boolean equals(Object otherObject){
		if (otherObject == null) {
		    return false;
		}
		if (getClass() != otherObject.getClass()) {  // make sure the other object is the same type
		    return false;                            
		}
		Prefix other = (Prefix) otherObject;  // have to down-cast to Prefix
		if (prefix.size() != other.prefix.size()){
			return false;
		}
		
		ListIterator<String> iter1 = prefix.listIterator();
		ListIterator<String> iter2 = other.prefix.listIterator();
		while(iter2.hasNext()){
			if(iter1.next() != iter2.next()){//make sure the contents are the same
				return false;
			}
		}
		
		return true;
	}
	

	
	
	
	
	
	
	
	
	
	
}