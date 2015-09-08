// Name: Yige Cao	
// USC loginid: 6796074453
// CS 455 PA2
// Fall 2014


import java.util.ArrayList;

/**
   A polynomial. Polynomials can be added together, evaluated, and
   converted to a string form for printing.
*/
public class Polynomial {

    /**
       Creates the 0 polynomial
    */
    public Polynomial() {
    	poly = new ArrayList<Term>();
    	assert isValidPolynomial();
    }


    /**
       Creates polynomial with single term given
     */
    public Polynomial(Term term) {
    	poly = new ArrayList<Term>();
    	poly.add(term);
    	removeZeroTerm();
    	assert isValidPolynomial();
    }


    /**
       Returns the Polynomial that is the sum of this polynomial and b
       (neither poly is modified)
       Merge is achieved by setting up two index pointers for the two Polynomials
       and iterate them through term by term. By comparing each term's expon we can
       quickly figure out the larger term to be placed into the sum Polynomial first.
       we hold the pointer for the smaller term Polynomial because it hasn't been able
       to be compared to every term the other Polynomial has yet. 
       After iterating through one of the Polynomials we simply copy the remainder terms
       of the second Poly into the sum Poly since now we are certain that the remainders
       are definitely placed in a decremented expon fashion.
     */
    public Polynomial add(Polynomial b) {
    	
    	Polynomial sum = new Polynomial();
    	
    	int i = 0;
    	int j = 0;
    	
    	while (i < poly.size() && j < b.poly.size()){
    		if (poly.get(i).getExpon() > b.poly.get(j).getExpon()) {
    			sum.poly.add(poly.get(i));
    			i++;
    		} 
    		else if (poly.get(i).getExpon() < b.poly.get(j).getExpon()){
    			sum.poly.add(b.poly.get(j));
    			j++;
    		}
    		else if (poly.get(i).getExpon() == b.poly.get(j).getExpon()){
    			double coeff = poly.get(i).getCoeff()+b.poly.get(j).getCoeff();
    			int expon = poly.get(i).getExpon();
    			Term addTerm = new Term(coeff, expon);
    			sum.poly.add(addTerm);
    			i++;
    			j++;
    		}
    	}
    	
    	
    	for (int l=i; l<poly.size(); l++){
    		sum.poly.add(poly.get(l));
    	}
    	
    	for (int k=j; k<b.poly.size(); k++){
    		sum.poly.add(b.poly.get(k));
    	}
    	
    	sum.removeZeroTerm();
    	assert isValidPolynomial();   // call it on "this"-- the left operand of the add
    	assert b.isValidPolynomial();  // call it on the right operand of the add
    	assert sum.isValidPolynomial();    // call it on the poly created in add
    	return sum;  // dummy code.  just to get stub to compile
    }


    /**
       Returns the value of the poly at a given value of x.
     */
    public double eval(double x) {
    	
    	double sum = 0.0;
    	for (int i=0; i<poly.size(); i++){
        	sum = poly.get(i).getCoeff() * Math.pow(x, poly.get(i).getExpon()) + sum;
    	}
	return sum;        
	
    }


    /**
       Return a String version of the polynomial with the 
       following format, shown by example:
       zero poly:   "0.0"
       1-term poly: "3.2x^2"
       4-term poly: "3.0x^5 + -x^2 + x + -7.9"

       Polynomial is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
    */
    public String toFormattedString() {
    	
    	assert isValidPolynomial();

    	if (poly.size()==0) {
    		return "0.0";
    	}
    	else {
    		
    		//first figure out what to write from the first element inside the Poly
			String returnString = "";

    		if (poly.get(0).getCoeff() == 1.0 && poly.get(0).getExpon() != 0){
    			returnString = "x^" + poly.get(0).getExpon();
    		}
    		else if (poly.get(0).getCoeff() == -1.0 && poly.get(0).getExpon() != 0){
    			returnString = "-x^" + poly.get(0).getExpon();
    		}
    		else if (poly.get(0).getExpon() == 0){
    			returnString = "" + poly.get(0).getCoeff();
    		}
    		else if (poly.get(0).getExpon() == 1){
    			returnString = poly.get(0).getCoeff() + "x";
    		}
    		else{
    			returnString = poly.get(0).getCoeff() + "x^" + poly.get(0).getExpon();
    		}
    		
    		//continue printing the terms through out the Poly
    		if (poly.size() != 1){
    			for (int i=1; i<poly.size(); i++){
    	    		if (poly.get(i).getCoeff() == 1.0 && poly.get(i).getExpon() != 0){
    	    			returnString += " + x^" + poly.get(i).getExpon();
    	    		}
    	    		else if (poly.get(i).getCoeff() == -1.0 && poly.get(i).getExpon() != 0){
    	    			returnString += "+ -x^" + poly.get(i).getExpon();
    	    		}	
    	    		else if (poly.get(i).getExpon() == 0){
    	    			returnString += " + " + poly.get(i).getCoeff();
    	    		}
    	    		else if (poly.get(i).getExpon() == 1){
    	    			returnString += " + "  + poly.get(i).getCoeff() + "x";
    	    		}
    	    		else{
    					returnString += " + " + poly.get(i).getCoeff() + "x^" + poly.get(i).getExpon();
    	    		}					
    			}
    		}
    	return returnString;	
    	}
    }


    // **************************************************************
    //  PRIVATE METHOD(S)

    /**
       Returns true iff the poly data is in a valid state.
    */
    private boolean isValidPolynomial() {
    	if (poly.size()<=1){
    		return true;
    	}
    	else{
    		for (int i=0; i<poly.size(); i++){
    			if (poly.get(i).getCoeff() == 0){//zero terms
    				return false;
    			}
    			if (poly.get(i).getExpon() < 0){//negative expon terms
    				return false;
    			}
    			if (i<poly.size()-1){
        			if (poly.get(i).getExpon() == poly.get(i+1).getExpon()){//no same expon
        				return false;
        			}
    			}
    			if (i<poly.size()-1){
    				if (poly.get(i).getExpon() < poly.get(i+1).getExpon()){//expon must be in reverse order
    					return false;
    				}
    			}
    		}
    	return true;
    	}
    }
    
    /** 
    	Check if Term 0 is being created by the constructor.If so, remove
    	the 0 term in the arrayList
    */
    
    private void removeZeroTerm() {
    	if (poly.size() > 0) {
    		for (int i = 0; i < poly.size(); i++){
    			if (poly.get(i).getCoeff() == 0.0) {
    				poly.remove(i);
    				i--;
    			}		
    		}
    	}
    }


    // **************************************************************
    //  PRIVATE INSTANCE VARIABLE(S)
    private ArrayList<Term> poly;
    
    //Representation Invariants:
    //Poly must contain NO zero terms
    //Poly must contain NO negative Exponent terms
    //Poly must contain NO same exponent terms
    //Poly's terms must be in a decreasing order

}
