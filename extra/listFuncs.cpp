/*  Name: Yige Cao
 *  loginid: 6796074453
 *  CS 455 Fall 2014
 *
 *  See listFuncs.h for specification of each function.
 */

#include <iostream>

#include <cassert>

#include <stack>


#include "listFuncs.h"

using namespace std;

/**
	mark the idx of the first occurrence of the data, then update this idx
	as we scan through all of the list for the last occurence
**/
int lastIndexOf(ListType list, int val) {
	int idx = 0;//for incre
	int finalIdx = 0;//for the final occurence
	int flag = 0;
	int flag2 = 0;


	if (list == NULL){//if the list itself is empty
		return -1;
	}
	else{

		//if the first item is the target, setup a flag for later checking
		if (list->data == val){
			flag = 1;
		}


		//if target is in the middle or the end
		while(list->next != NULL){

			if (list->next->data == val){//if we find target in the middle
				idx++;//increment incre idx pointer
				finalIdx = idx;//update the potential return value
				list = list->next;//move on to the next item on the list

				flag2 = 1;//setup a flag to show that we've entered the list's middle
			}
			else {//if the target isn't found in this item in the middle
				list = list->next;//move on to the next item
				idx++;//incre pointer idx
			}

		}

		//if either one of the flag is true, then a match must have been found
		//thus return the updated finalIdx value
		if(flag == 1 || flag2 == 1){
			return finalIdx;
		}

		//if both flags are 0 then no match was found anywhere in the list, return -1
		else if (flag == 0 && flag2 == 0){
			return -1;
		}		
	}
}


/**
	By scanning the given list one by one and setting up flags to detect
	continuous evens, we delete the extra even numbers and put the odd numbers
	and single even numbers to a new list that overwrites the old one.
**/
void removeAdjacentEvens(ListType &list) {

	//setup two flags to detect even adjacents
	int flag = 0;
	int flag2 = 0;

	//iteration ptr created to avoid changing list
	Node* iter = list;

	//if the list is empty, make no changes
	if(list == NULL)
	{
		list = list;
	}
	else
	{

		//if the first Node* is an even number, set flag
		if(iter->data%2 == 0)
		{
			flag = 1;
		}
		else
		{
			flag = 0;
		}


		//iterate through the entire list
		while(iter->next != NULL)
		{
			//if encountered two even numbers in a row, mark the Node*
			//and delete it
			if(iter->next->data%2 == 0 && (flag == 1 || flag2 == 1))
			{
				flag = 0;
				flag2 = 1;

				Node* newNext = iter->next->next;
				Node* del = iter->next;
				delete del;

				iter->next = newNext;//re chaining 

			}


			else if(iter->next->data%2 == 0)
			{
				flag2 = 1;

				iter = iter->next;

			}


			else
			{
				flag = 0;
				flag2 = 0;

				iter = iter->next;
			}
		}
	}
}


/**
	go through the old list once to save every element in reverse order in a stack
	after reaching the end point of the old list, start creating new Node* by poping
	off the content of the stack in a LIFO fashion, this will provide us with the
	contents of the old list in a mirrored way.
**/
void mirror(ListType & list) {

	//Node* for iteration
	Node *iter = list;

	//create a stack for saving the list content in a reversed order
	stack <int> nodePtr;

	if(iter == NULL){
		list = list;
	}
	else{

		//push the very first data on the list to the bottom of the stack
		nodePtr.push(iter->data);

		//as long as there is data on the list, push them to the stack
		while(iter->next != NULL){
			nodePtr.push(iter->next->data);
			iter = iter->next;
		}	

		//since iter now points to the last item on the list, it's time to 
		//pop everything from the stack and create them as new Node*s after
		//iter, which will in turn affect Node* list as well.
		while(nodePtr.size() != 0){
			iter->next = new Node(nodePtr.top());
			iter = iter->next;
			nodePtr.pop();
		}

	}

}


/**
	go through the list once to figure out the length of it, if smaller than K then
	the function will do no change to the list. otherwise, setup 3 different ptrs that
	points to the old head, old end and the new head to the given list. by chaining the
	3 new pointers to their desired position we can rotate the list to the left.
**/
void rotateLeft(ListType &list, int k) {
	int listLength = 0;
	Node* endPtr = list;
	Node* headPtr = list;

	if(list == NULL || k <= 0){
		list = list;
	}
	else{

		listLength++;//add the first element to count

		while(endPtr->next != NULL){//iter thru the rest in the list
			listLength++;
			endPtr = endPtr->next;
		}

		if(k >= listLength){//stop processing here
			list = list;
		}
		else{

			Node* iter = list;//a new iter2 ptr

			int idxPtr = 1;//the position ptr that depends on the size of k

			//we stop just at the element previous the new head element of the rotated list
			while(idxPtr != k){
				idxPtr++;
				iter = iter->next;
			}


			//cout << "iter should point to the one before the new head: " << iter->data << endl;

			list = iter->next;//make this the head of the list now

			endPtr->next = headPtr;//link old end to old front

			iter->next = NULL;//create new end for list

		}

	}
}
