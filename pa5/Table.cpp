// Table.cpp  Table class implementation
// CSCI 455 PA5
// Name: Yige Cao
// Loginid: 6796074453

/*
 * Modified 11/22/11 by CMB
 *   changed name of constructor formal parameter to match .h file
 */

#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>
#include <cstdlib>

//*************************************************************************
// Node class definition and member functions
//     You don't need to add or change anything in this section

// Note: we define the Node in the implementation file, because it's only
// used by the Table class; not by any Table client code.

struct Node {
  string key;
  int value;

  Node *next;

  Node(const string &theKey, int theValue);

  Node(const string &theKey, int theValue, Node *n);
};

Node::Node(const string &theKey, int theValue) {
  key = theKey;
  value = theValue;
  next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
  key = theKey;
  value = theValue;
  next = n;
}

typedef Node * ListType;

//*************************************************************************

//create the default constructor for a Table of a pre defined size.
Table::Table() {
  numEntry = 0;
  data = new Node*[HASH_SIZE];
  hashSize = HASH_SIZE; 
}

//create a Table of a size passed in by the user
Table::Table(unsigned int hSize) {
  numEntry = 0;
  data = new Node*[HASH_SIZE];
  hashSize = hSize;
}

/**lookup is done by finding the hashCode of the key provided and 
  looking at the Node* of this location and determine its value, if
  the stored value is NULL, then this entry doesn't exist, return 
  NULL. ELse, search the entire list.
**/
int * Table::lookup(const string &key) {
  int keyIndex = hashCode(key);
  Node* nodeLoc = data[keyIndex];

  if (nodeLoc == NULL){//Node* doesn't point to anywhere, meaning no entry
    return NULL;
  }

  else {//inside the LinkedList

    if(nodeLoc->key == key){//if the target is the front element
      return &(nodeLoc->value);
    }

    else {//if target is in the middle or the end of the LL

      while(nodeLoc->next != NULL){

        if (nodeLoc->next->key == key){
          return &(nodeLoc->next->value);
        }
        else{
          nodeLoc = nodeLoc->next;
        }

      }

      //at the last one in the list, haven't checked its key yet
      if(nodeLoc->key == key){
        return &(nodeLoc->value);
      }
      else{
        return NULL;
      }

    }
  }

}

/**
  check first if the key even exist in the hashTable, else return NULL
  then find out where it is in the linkedlist, front, middle, last
  if in front, delete this Node* from the data[]
  if in middle, find out the previous Node*->next, the next Node*->next,
  link these two together thus skipping the middle one
  if it's the last item, find the previous Node*->next and set to NULL

**/
bool Table::remove(const string &key) {
  //if the entry doesn't exist
  int keyIndex = hashCode(key);
  Node* nodeLoc = data[keyIndex];//nodeLoc is the head of this LL at this BUCKET

  if (nodeLoc == NULL){
    return false;  // dummy return value for stub    
  }
  else{

    if(nodeLoc->key == key){//in the front
      data[keyIndex] = nodeLoc->next;//change the front pointer
      delete nodeLoc;//delete the old pointer
      numEntry--;
      return true;
    }
    else{//if in the middle or the end

      //will stop at the element whose NEXT has the key
      //will iterate till the second last element if the key is in the LAST element
      while(nodeLoc->next->key != key){
        if(nodeLoc->next->next == NULL){
          return false;
        }
        else{
          nodeLoc = nodeLoc->next;
        }
      }

      if(nodeLoc->next->next != NULL){//found key in the middle
        Node* skipNode = nodeLoc->next;
        nodeLoc->next = nodeLoc->next->next;//skip the element that has the key
        delete skipNode;//delete the skipped element
        numEntry--;
        return true;
      }
      else{//found in the last element
        Node* skipNode = nodeLoc->next;//ready to skip the last node
        nodeLoc->next = NULL;//make the second last node the last one
        delete skipNode;
        numEntry--;
        return true;
      }
    }
  }

}

/**
 if collision, link the new element to the very end of the chain
 if no collision, simply add the element according to data[keyIndex]'s hashCode
**/
bool Table::insert(const string &key, int value) {
  int keyIndex = hashCode(key);
  Node* nodeLoc = data[keyIndex]; 

  if (nodeLoc == NULL){
    //cout << "No collision" << endl;

    data[keyIndex] = new Node(key, value);//save according to the hashCode, no chaining required
    numEntry++;
    return true;

  }
  else{

    //cout << "collision" << endl;

    if(nodeLoc->key == key){//the front one in the list has the same key, don't insert

     // cout << "head key is the same, quit." << endl;
      return false;
    }
    else {//key might be in the middle or end, or not present in list

      while(nodeLoc->next != NULL){
        if(nodeLoc->next->key == key){//in the middle of LL

          //cout << "middle or end key is the same, quit." << endl;
          return false;
        }
        else{
          nodeLoc = nodeLoc->next;
        }
      }

      //by here nodeLoc point to the last element in the list and there's no way it has the key
      //thus insert the key here and chain the new element together
      nodeLoc->next = new Node(key, value);
      numEntry++; 
      return true;
      }

    }
}

int Table::numEntries() const {
  return numEntry;      // dummy return value for stub
}


void Table::printAll() const {
  for (int i=0; i<hashSize; i++){
    if (data[i] != NULL){
      Node* iter = data[i];
      
      while(iter->next != NULL){
        cout << iter->key << " " << iter->value << endl;
        iter = iter->next;
      }

      cout << iter->key << " " << iter->value << endl;     
    }
  }
}

void Table::hashStats(ostream &out) const {
  out << "number of buckets: " << hashSize << endl;
  out << "number of entries: " << numEntry << endl;

  int count = 0;
  for(int i=0; i<hashSize; i++){
    if (data[i] != NULL){
      count++;
    }
  }

  out << "number of non-empty buckets: " << count << endl;

  //longest chain by looking at the headpointer for each data[] and updating the longest number
  int chainCount = 0;
  int max = 0;
  for(int i=0; i<HASH_SIZE; i++){
    if (data[i] != NULL){

      Node* iter = data[i];

      while(iter->next != NULL){
        iter = iter->next;
        chainCount++;
      }

      chainCount++;//for the last one

      if(chainCount>=max){
        max = chainCount;
      }

      chainCount = 0;
    }
  }

  out << "longest chain: " << max << endl;
  
}

// add definitions for your private methods here

