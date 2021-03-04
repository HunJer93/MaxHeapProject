//Author: Jeremy Hunton
//Purpose: The purpose of this project is to utilize a max heap to return the highest integer from the user-inputed data,
//delete the integer, and then repeat to the user's discretion. The purpose of MainClass is to 
//display menus, manage user inputs, and initiate the heap. 
//Date: 3/3/20201
package HuntonHeapProject;

import java.util.Scanner; //import scanner

public class MainClass 
{
	//declare class CONSTANTS
	public static final char[] MAIN_MENU_CHARS = {'A', 'B', 'Q'};
	public static final String[] MAIN_MENU_OPTIONS = {"Add to the Heap", "Delete Max and Sort the Heap", "Quit the Program"};
	public static final char[] HEAP_MENU_CHARS = {'Y', 'N'};
	public static final String[] HEAP_MENU_OPTIONS = {"Yes, delete and sort", "No, return to Main Menu"};
	

	public static void main(String[] args)
	{
		//declare and initialize scanner object
		Scanner input = new Scanner(System.in);
		
		//declare local variables 
		char menuSelection = ' ';
		
		//invoke Heap class 
		Heap currentHeap = new Heap();
		
		//display welcome banner
		displayWelcomeBanner();
		
		//display the main menu and validate the input
		menuSelection = validateMainMenuSelection(input);
		
		//while loop making sure the Quit option isn't selected
		while(menuSelection != 'Q')
		{
			if(menuSelection == 'A')
			{
				//request and validate the input to the array, and write that input to the array
				currentHeap.setAddToArray(validateArrayInput(input));
				
				//sift the heap up to organize it
				currentHeap.setHeapSiftUp();
				
				//display the array report of the updated array
				displayArrayReport(currentHeap.getLoadedArray(), currentHeap.getRecordCount());
			}//end menuSelection is A (add to the heap)
			
			//else main menu selection B selected
			else
			{
				//request if the user wants to run the delete function again
				menuSelection = validateSiftHeapMenu(input);
				
				//while loop for repeating the delete function and sort of the heap
				while(menuSelection == 'Y')
				{
					//selection structure checking that the record count is larger than 0 and able to delete an element from
					if(currentHeap.getRecordCount() > 0)
					{
						//delete the top number (max) from the heap
						currentHeap.setDeleteNumber();
						
						//add the deleted number to the deleted number array
						currentHeap.setAddToDeletedArray();
						
						//sift the heap down to reorganize it
						currentHeap.setSiftDown();
						
						//display the sifted array, the deleted number, and the record count
						displayArrayReport( currentHeap.getLoadedArray(),currentHeap.getRecordCount(), currentHeap.getDeletedNumber());
						
						//request if the user wants to run the delete function again
						menuSelection = validateSiftHeapMenu(input);
					}//end record count valid, and delete commits
					
					//else recordCount not valid so display error and break out of while-loop to return to the main menu
					else
					{
						displayArrayError();
						break;
					}//end display error
				}//end while loop for "Yes" option
			}//end else main menu selection B
			
			//return to main menu
			menuSelection = validateMainMenuSelection(input);
		}//end while loop to quit the main menu
		
		//display the final report
		displayFinalReport(currentHeap.getDeletedArray(), currentHeap.getLoadedArray(), currentHeap.getRecordCount(), currentHeap.getDeletedRecordCount());
		
		//display farewell message
		displayFarewellMessage();
		
		//close Scanner class
		input.close();
	}//end of Main Method 
	
	//start of Void methods
	
	//displayWelcomeBanner VOID method greets the user about the function of the heap program
	public static void displayWelcomeBanner()
	{
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("Welcome to my max heap project! The goal of this project");
		System.out.println("is to better understand the logic of a max heap so I can");
		System.out.println("utilize it for other projects. The goal of a max heap");
		System.out.println("is to sort a data set so that the parent values are always");
		System.out.println("greater than the children values, with the max value being");
		System.out.println("on the top of the heap. When the heap is sorted, the max");
		System.out.println("value of the heap is removed, and the heap is sorted again.\n"); 
		System.out.println("Follow the menu options to input up to 100 numbers");
		System.out.println("into the heap, and then use the sort option to sort the");
		System.out.println("heap. The final report will display the items deleted");
		System.out.println("from the heap, and the final heap");
	}//end displayWelcomeBanner
	
	//displayMainMenu VOID method displays the main menu to be called in validateMainMenu VR method
	public static void displayMainMenu() 
	{
		//declare local index 
		int localIndex = 0;
		
		//display menu title
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("MAIN MENU");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~\n");
		
		//cycle menu options using a while loop to display the menu options
		while(localIndex < MAIN_MENU_CHARS.length)
		{
			//display line 
			System.out.printf("%-3c%-4s%-30s\n", MAIN_MENU_CHARS[localIndex], "for", MAIN_MENU_OPTIONS[localIndex]);
			
			//LCV
			localIndex++;
		}//end main menu array cycle
	}//end displayMainMenu
	
	//displayHeapMenu VOID method displays the heap menu to be called in validateSiftHeapMenu VR method
	public static void displayHeapMenu() 
	{
		//declare local index 
		int localIndex = 0;
			
		//display menu title
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("HEAP MENU");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~\n");
		System.out.println("Do you wish to delete the max value from the heap and");
		System.out.println("sort it?\n");
			
		//cycle menu options using a while loop to display the menu options
		while(localIndex < HEAP_MENU_CHARS.length)
		{
			//display line 
			System.out.printf("%-3c%-4s%-30s\n", HEAP_MENU_CHARS[localIndex], "for", HEAP_MENU_OPTIONS[localIndex]);
			
			//LCV
			localIndex++;
		}//end heap menu array cycle
	}//end displayHeapMenu method
	
	//displayArrayReport displays the array report after an item has been added to the heap
	public static void displayArrayReport(int[]borrowedLoadedArray, int borrowedRecordCount)
	{
		//declare local index and ZERO placeholder
		int localIndex = 0;
		
		//display menu title
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("HEAP ARRAY REPORT");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~\n");
		System.out.printf("%-22s\n", "Current Heap Array:");

		//cycle array with repetition structure to show all elements of the heap
		while (localIndex < borrowedRecordCount)
		{
			System.out.printf("%-8s%3d%-7s%4d\n", "Element ", localIndex, ":", borrowedLoadedArray[localIndex]);
			
			//LCV
			localIndex++;
		}//end array cycle
		System.out.printf("\n%-16s%6d\n", "Record Count:", borrowedRecordCount);
	}//end displayArrayReport void method
	
	//displayArrayReport OVERLOADED METHOD displays the array report after an item has been deleted from the heap and the top
	//number has been sifted up
		public static void displayArrayReport(int[]borrowedLoadedArray, int borrowedRecordCount, int borrowedDeletedNumber)
		{
			//declare local index and ZERO placeholder
			int localIndex = 0;
			
			//display menu title
			System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.println("EDITED HEAP ARRAY REPORT");
			System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~\n");
			System.out.printf("%-22s\n", "Current Heap Array:");

			//cycle array with repetition structure to show all elements of the heap
			while (localIndex < borrowedRecordCount)
			{
				System.out.printf("%-8s%3d%-7s%4d\n", "Element ", localIndex, ":", borrowedLoadedArray[localIndex]);
				
				//LCV
				localIndex++;
			}//end array cycle
			System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.printf("%-16s%6d\n", "Deleted Number:", borrowedDeletedNumber);
			System.out.printf("%-16s%6d\n", "Record Count:", borrowedRecordCount);
		}//end displayArrayReport void method
	
	//displayArrayError displays an error that the record count is not valid.
	public static void displayArrayError()
	{
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("ERROR! The array does not have any records in it. Please");
		System.out.println("load the array with at least 1 variable before deleting");
		System.out.println("from the array. Returning to the main menu...");
	}//end displayArrayError
		
	//displayFinalReport void method displays the final report with the count of deleted elements and the final array
	public static void displayFinalReport(int[] borrowedDeletedArray, int[] borrowedLoadedArray, int borrowedRecordCount, int borrowedDeletedRecordCount)
	{
		//declare local index
		int localIndex = 0;
				
		//display menu title
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("FINAL REPORT");
		System.out.println("~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~\n");
		System.out.println("\nDeleted Numbers:");
		
		//cycle array with repetition structure to show all the elements that were deleted
		while(localIndex < borrowedDeletedRecordCount)
		{
			System.out.printf("%-8s%3d%-7s%8d\n", "Element ", localIndex, ":", borrowedDeletedArray[localIndex]);
			
			//LCV
			localIndex++;
		}//end while loop cycling deletedArray
			//display deleted number count
		System.out.printf("\n%-18s%8d\n", "Numbers Deleted:", borrowedDeletedRecordCount);
		//reset localIndex
		localIndex = 0;
		
		//display current heap array
		System.out.println("\nCurrent Heap Array:");
		//cycle array with repetition structure to show all elements of the heap
		while (localIndex < borrowedRecordCount)
		{
			System.out.printf("%-8s%3d%-7s%8d\n", "Element ", localIndex, ":", borrowedLoadedArray[localIndex]);
							
			//LCV
			localIndex++;
		}//end array cycle
		System.out.printf("\n%-18s%8d\n", "Record Count:", borrowedRecordCount);
		
	}//end displayFinalReport
	
	//displayFarewellMessage displays the farewell message
	public static void displayFarewellMessage()
	{
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("Thank you for using my max heap sorter. For any questions,");
		System.out.println("comments, or to see other project's I've worked on, please");
		System.out.println("check out my github at https://github.com/HunJer93");
		System.out.println("\nI hope you have a great day!");
	}//end displayFarwellMessage
	
	//end of VOID METHODS 
	
	//start of VR METHODS
	
	//validateMainMenuSelection calls the displayMainMenu method and validates the input
	public static char validateMainMenuSelection(Scanner borrowedInput)
	{
		//declare local variable
		char localMenuSelection = ' ';
		
		//display main menu
		displayMainMenu();
		
		//request input
		
		System.out.print("Please enter your selection here:");
		localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		
		//validation loop checking for valid input
		while(localMenuSelection != 'A' && localMenuSelection != 'B' && localMenuSelection != 'Q')
		{
			//display error message
			System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.println("Error! That's not a valid input. Please enter a valid input");
			
			//display main menu
			displayMainMenu();
			
			//request input
			System.out.print("Please enter your selection here:");
			localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation loop
		
		//return localMenuSelection
		return localMenuSelection;
	}//end validateMainMenuSelection 
	
	//validateArrayInput request a user input to add to the heap and validates it
	public static int validateArrayInput(Scanner borrowedInput)
	{
		//declare local variable
		int localInput = 0;
		
		//display array input menu
		System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.println("ARRAY INPUT");
		System.out.println("Please enter a non-negative integer into the array. NOTE: the");
		System.out.print("array can hold a max of 100 elements:");
		
		//request input
		localInput = borrowedInput.nextInt();
		
		//validate input with validation loop
		while(localInput <0)
		{
			//display error
			System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.println("Error! That's not a valid input. Please enter a valid input");
			
			//display array input menu
			System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.println("ARRAY INPUT");
			System.out.print("Please enter a non-negative integer into the array:");
			
			//request input
			localInput = borrowedInput.nextInt();	
		}//end validation loop
		
		//return user input
		return localInput;
	}//end validateArrayInput
	
	//validateSiftHeapMenu calls the displayHeapMenu and validates the input
	public static char validateSiftHeapMenu(Scanner borrowedInput)
	{
		//declare local variable
		char localMenuSelection = ' ';
		
		//display heap menu
		displayHeapMenu();
		
		//request input
		System.out.print("Enter your selection here:");
		localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		
		//validation loop checking for valid input
		while(localMenuSelection != 'Y' && localMenuSelection != 'N')
		{
			//display error message
			System.out.println("\n~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
			System.out.println("Error! That's not a valid input. Please enter a valid input");
			
			//display  heap menu
			displayHeapMenu();
			
			//request input
			System.out.print("Enter your selection here:");
			localMenuSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation loop
		
		//return localMenuSelection 
		return localMenuSelection;
	}//end validateSiftHeapMenu

}//end of Main Class
