// grades.cpp
// CSCI 455 PA5
// Name: Yige Cao
// Loginid: 6796074453
// 
/*
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 * 
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

int main(int argc, char * argv[]) {

  // gets the hash table size from the command line

  int hashSize = Table::HASH_SIZE;

  Table * grades;  // Table is dynamically allocated below, so we can call
                   // different constructors depending on input from the user.

  if (argc > 1) {
    hashSize = atoi(argv[1]);  // atoi converts c-string to int

    if (hashSize < 1) {
      cout << "Command line argument (hashSize) must be a positive number" 
	   << endl;
      return 1;
    }

    grades = new Table(hashSize);

  }
  else {   // no command line args given -- use default table size
    grades = new Table();
  }


  grades->hashStats(cout);

  // add more code here
  // Reminder: use -> when calling Table methods, since grades is type Table*
  cout << "cmd> " << endl;
  string cmdIn = "";
  string name;
  string score;
  string newScore;


  while (cmdIn != "q"){

    cin >> cmdIn;

    if(cmdIn == "h"){
      cout << "i: (name score) Insert this name and score in the grade table." << endl;
      cout << "c: (name newscore) Change the score for name." << endl;
      cout << "l: (name) Lookup the name, and print out his or her score." << endl;
      cout << "r: (name) Remove this student." << endl;
      cout << "p: Prints out all names and scores in the table." << endl;
      cout << "n: Prints out the number of entries in the table." << endl;
      cout << "s: Prints out statistics about the hash table at this point." << endl;
      cout << "h: Prints out a brief command summary." << endl;
      cout << "q: Exits the program." << endl;      
    }

    else if (cmdIn == "i"){
      cin >> name;
      cin >> score;
      if(grades->insert(name, atoi(score.c_str())) == false){
        cout << "insert failed, " << name << " already exists in Table." << endl;
      }
    }

    else if (cmdIn == "c"){
      cin >> name;
      cin >> newScore;

      int *result = grades->lookup(name);

      if ( result == NULL){
        cout << "score change failed, " << name << " doesn't exists in Table." << endl;
      }
      else {
        *result = atoi(newScore.c_str());
      }
    }

    else if (cmdIn == "l"){
      cin >> name;

      int *result = grades->lookup(name);

      if ( result == NULL){
        cout << "lookup failed, " << name << " doesn't exists in Table." << endl;
      }
      else {
        cout << "name: " << name << " score: " << *result << endl;
      }
    }

    else if (cmdIn == "r"){
      cin >> name;

      if(!grades->remove(name)){
        cout << "removal failed, " << name << " doesn't exists in Table." << endl;
      }
    }

    else if (cmdIn == "p"){
      grades->printAll();
    }

    else if (cmdIn == "n"){
      cout << grades->numEntries() << endl;
    }

    else if (cmdIn == "s"){
      grades->hashStats(cout);
    }

    else if (cmdIn == "q"){
      break;
    }
    cout << "cmd> " << endl;
  }

  return 0;
}
