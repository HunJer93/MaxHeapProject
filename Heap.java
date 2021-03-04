//Author: Jeremy Hunton
//Purpose: The purpose of this class is to manage the heap and sort the user inputed numbers
//with the maximum number on the top of the heap. 
//Date: 3/3/20201
package HuntonHeapProject;

public class Heap 
{
	//declare class constants
	private final int MAX_RECORD_COUNT = 100;
	private final int ZERO = 0;
	private final int ONE = 1;
	private final int TWO = 2;
	//declare class variables 
	private int[] arrayList = new int[MAX_RECORD_COUNT];
	private int[] deletedList = new int[MAX_RECORD_COUNT];
	private int recordCount = 0;
	private int deletedNumber = 0;
	private int deletedRecordCount = 0;
	
	//declare class constructor
	public Heap()
	{
		
	}//end of class constructor
	
	//start of SETTER METHODS 
	
	//setAddToArray adds the user input to the arrayList using a swap
	public void setAddToArray(int borrowedInput)
	{
		//create local index 
		int localIndex = 0;
			
		//create new array that is set to the incremented record count to account for the additional element
		int tempArray[] = new int [recordCount + ONE];
			
		//copy the arrayList into the tempArray using a for loop
		for (localIndex = 0; localIndex < recordCount ; localIndex++)
		{
			tempArray[localIndex] = arrayList[localIndex];
		}//end of arrayList copy into tempArray
			
		//add the input to the last element of the array (current record count incremented)
		tempArray[recordCount++] = borrowedInput;
			
		//swap the arrays back using a for loop
		for (localIndex = 0; localIndex < recordCount; localIndex++)
		{
			arrayList[localIndex] = tempArray[localIndex];
		}//end for loop swapping arrays back 
	}//end setAddToArray method
	
	//setHeapSiftUp sorts the heap by sifting up the next user input to ensure that the largest number is at the top of the heap
	public void setHeapSiftUp()
	{	
		//declare indexK to be the index of the item just added
		int indexK = recordCount - ONE;
		
		/*declare the parent node and child node. A parent node at indexK has the relationship 2K+1 (left) and 2K+2 (right) with its children. 
		Children at indexK have a relationship (K-1)/2 (or (K-2)/2 respectably) with their parent node. Since we have added a new
		number to the array, we will be using the index for the child node equal to our indexK. Round this number to get an even index.*/
		int childNode = arrayList[indexK];
		int parentNodeIndex = Math.round((indexK-ONE)/TWO);
		
		//if the formula gets a negative number, set parentNodeIndex to ZERO instead
		if (parentNodeIndex < ZERO)
		{
			parentNodeIndex = ZERO;
		}//END parentNodeIndex default to ZERO
		
		//declare parentNode at the index 
		int parentNode = arrayList[parentNodeIndex];
		
		//while loop to check if the index is at the top of the array (i.e. no parent node.). If no parent, we don't need to sift up.
		while(indexK > 0)
		{
			//selection structure to swap if the child is larger than the parent
			if(childNode > parentNode)
			{
				//swap the nodes so greater number is the parent in the arrayList
				arrayList[parentNodeIndex] = childNode;
				arrayList[indexK] = parentNode;
				
				//assign new child index
				indexK = parentNodeIndex;
				
				//assign new parent node index
				parentNodeIndex = Math.round((indexK - ONE)/TWO);
				
				//if the formula get a negative number, set the parentNodeIndex to ZERO
				if (parentNodeIndex <= ZERO)
				{
					parentNodeIndex = ZERO;
				}//END parentNodeIndex default to ZERO
			
				//assign new parent node
				parentNode = arrayList[parentNodeIndex];
				
			}//end selection structure if child node is greater than the parent node
			
			//else break because the heap is in order
			else
			{
				break;
			}//end else break
		}//end while loop greater than 0 
	}//end setHeapSiftUp
	
	//setDeleteNumber deletes the largest number from the heap and swaps it with the lowest child.
	//We will save this number removed as deletedNumber and add it to our deletedList array.
	public void setDeleteNumber()
	{
		//declare largest number to deletedNumber before removing it from the array.
		deletedNumber = arrayList[ZERO];
		
		//swap the lowest number in the array to the highest number for our "sift down"
		arrayList[ZERO] = arrayList[recordCount - ONE];
		
		//set the recordCount to minus one because to account for the removed number
		recordCount = recordCount - ONE;

	}//end setDeleteNumber setter method
	
	//setAddToDeletedArray adds the deleted numbers to an array list
	public void setAddToDeletedArray()
	{
		//create local index
		int localIndex = 0;
		
		//create new array that is set to the incremented record count to account for the additional element
		int tempArray[] = new int [deletedRecordCount + ONE];
		
		//copy the arrayList into the tempArray using a for loop
		for (localIndex = 0; localIndex < deletedRecordCount ; localIndex++)
		{
			tempArray[localIndex] = deletedList[localIndex];
		}//end of arrayList copy into tempArray
		
		//add the input to the last element of the array (current record count incremented)
		tempArray[deletedRecordCount++] = deletedNumber;
		
		//swap the arrays back using a for loop
		for (localIndex = 0; localIndex < deletedRecordCount; localIndex++)
		{
			deletedList[localIndex] = tempArray[localIndex];
		}//end for loop swapping arrays back 
	}//end setAddToDeletedArray method
	
	//setSiftDown sorts our heap down by swapping parent and children nodes so the largest node is on top and the heap is in order.
	public void setSiftDown()
	{
		//declare local variables
		int parentNodeIndex = 0;
		int parentNode = arrayList[parentNodeIndex];
		int maxIndex = recordCount - ONE;
		
		//assign children nodes and children indexes
		int indexL = (TWO*parentNodeIndex) + ONE; //assigned based upon parent/child relationship in a heap
		int indexR = (TWO*parentNodeIndex) + TWO; //assigned based upon parent/child relationship in a heap
		int leftChildNode = arrayList[indexL]; 
		int rightChildNode = arrayList[indexR];
		
		//while loop making sure the index is less than the max index and not at the bottom of the heap, and that at least one of the 
		//children are larger than the parent for the sift down.
		while(parentNodeIndex < maxIndex && (leftChildNode > parentNode || rightChildNode > parentNode))
		{
			//if statement checking that a right index exists to make a comparison
			if(indexR <= maxIndex)
			{
				//if the left child is greater than the right child, swap left (left is first because we fill the heap from left to right).
				if(leftChildNode >= rightChildNode)
				{
					//swap with parent node
					arrayList[parentNodeIndex] = leftChildNode;
					arrayList[indexL] = parentNode;
					
					//assign new parent node index to reflect the new parent node
					parentNodeIndex = indexL;
					
					//assign new children indexes and nodes for next sift down comparison
					indexL = (TWO*parentNodeIndex) + ONE;
					indexR = (TWO*parentNodeIndex) + TWO;
					leftChildNode = arrayList[indexL];
					rightChildNode = arrayList[indexR];
				}//end left child larger, so swap left
				
				//else the right node is larger of the two children, and larger than the parent node, so we swap right
				else
				{
					//swap with parent node
					arrayList[parentNodeIndex] = rightChildNode;
					arrayList[indexR] = parentNode;
					
					//assign indexK to parent node from right index.
					parentNodeIndex = indexR;
					
					//assign new children indexes and nodes for next sift down comparison
					indexL = (TWO*parentNodeIndex) + ONE;
					indexR = (TWO*parentNodeIndex) + TWO;
					leftChildNode = arrayList[indexL];
					rightChildNode = arrayList[indexR];
				}//end right larger, so swap right
			}//end right child exists selection structure
			
			//if there is no right node, so we default swap to the left node
			else if(indexL == maxIndex)
			{
				//swap with parent node
				arrayList[parentNodeIndex] = leftChildNode;
				arrayList[indexL] = parentNode;
				
				//assign new children for next sift down comparison
				indexL = (TWO*parentNodeIndex) + ONE;
				indexR = (TWO*parentNodeIndex) + TWO;
				leftChildNode = arrayList[indexL];
				rightChildNode = arrayList[indexR];
				
			}//end left child node swap with no right child node available
			//else no child so break
			else
			{
				break;
			}//end no child so break	
		}//end while loop making sure the parent node isn't at the end of the array	
	}//end setSiftDown setter method
	
	
	//end of setter methods
	
	//start of getter methods
	
	//getLoadedArray returns the sorted array
	public int[] getLoadedArray()
	{
		return arrayList;
	}//end getLoadedArray

	//getRecordCount returns the array record count
	public int getRecordCount()
	{
		return recordCount;
	}//end getRecordCount
	
	//getDeletedNumber returns the largest number that was deleted from the array
	public int getDeletedNumber()
	{
		return deletedNumber;
	}//end getDeletedNumber
	
	//getDeletedRecordCount returns the record count for deleted numbers
	public int getDeletedRecordCount()
	{
		return deletedRecordCount;
	}//end getDeletedRecordCount
	
	//getDeletedArray returns the deleted numbers array list
	public int[] getDeletedArray()
	{
		return deletedList;
	}//end getDeletedArray
}//end Heap class
