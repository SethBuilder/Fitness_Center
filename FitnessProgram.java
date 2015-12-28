import java.io.*;
import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialized in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
   private FitnessClass[] classList;//to hold classes array sorted by start time and null when available
   private FitnessClass[] tmpC;//to hold classes as read from file
   private final int maxNum = 7;
   private int numOfClasses;//number of non null objects
   int numLines;//number of lines
   private Scanner scan;//to hold scanner object
   private String [][] attends;//to hold 2D array for all attendances values with ID's
   
   /***constructor receives scanner with ClassesIn content and a 2D array with all attendances values*/
   public FitnessProgram(Scanner sc, String[][] atts)
   {
	   attends = atts.clone();//clone attendances parameter 
	   scan = sc; // assign scanner object parameter to our own scanner object 
	   
	   tmpC = this.tempClassList();//fetch fitness class objects (unsorted)
	   
  	   classList = new FitnessClass[maxNum];//create fitness class array with 7 spaces
		
  	   		//loop through all spots from 9 to 15
			for(int i = 0; i < maxNum; i ++)
			{
				/**bring value from method findClass, which takes the unsorted array and a time slot*/
				int found = findClass(tmpC, i + 9);
				
				/**if found is greater than 0, assign to the sorted array*/
				if(found >= 0)
					classList[i] = tmpC[found];
				
				/**else, assign null*/
				else
					classList[i] = null;
			}
	}//end of constructor
   
   /**return sorted array of classes*/
   public FitnessClass[] getSchedule(){return classList;}
   
   /**returns max number of spots --> 7*/
   public int getMax(){return maxNum;}
   
   /**returns number of non null objects*/
   public int numOfClasses()
   {
	    numOfClasses = 0;
	    //loop through all spots
	   for(int i = 0; i < classList.length; i++)
		   if(classList[i] != null)//when not null, increment variable
			   numOfClasses++;
	   
	   return numOfClasses;
   }


/**returns object at index x as required by assignment description
 * eventually, I didn't have to use this method*/
   public FitnessClass classAt(int x)
   {
	   return classList[x];
   }

  //return number of line in the scanner object passed from the GUI class
   public int numOfLines()
   {
	   while(scan.hasNextLine())//while there's a next line increment variable
		{
			numLines++;
			scan.nextLine();
		}
	 return numLines;
   }

   /**creates unsorted list of classes as read from file*/
   public FitnessClass[] tempClassList()
   {
	   FileReader red = null;
	try {
		red = new FileReader("ClassesIn.txt");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		Scanner in = new Scanner(red);//put it in a scanner object
		
		tmpC = new FitnessClass[this.numOfLines()];//create a fitness class array as big the number of lines
		
		//loop through all array
		for(int i = 0; i < tmpC.length; i++)
			{
				String record = in.nextLine();//read first line
				String[] id = new String[5];//to hold broken line
				id = record.split("[ ]+");//break line
				String idx = id[0];//id is at first spot
				
				/**create a new fitness class by sending the line from ClassesIn file 
				 * and the value returned from findID(attendances)*/
				tmpC[i] = new FitnessClass(record, findID(idx));
			}
		
		return tmpC;//return temporary array of unsorted classes
   }
   /**used by constructor to make sorted class array. 
    * takes unsorted array and a time slot. 
    * returns index of required class in the unsorted array if found
    * returns -1 if there's no class in a particular spot so that the constructor can fill null in the place*/
   
   public int findClass(FitnessClass[] fc, int x)
   {
	   int len = fc.length;//length of unsorted array
	   for(int i = 0; i < len; i++)
	   {
		   if(x == fc[i].getStartTime())//if found return index
			   return i;   
	   }
	   return -1;//return -1 when not found
   }
   
   /**used by tempClassList method to make unsorted list of classes
    * takes a string (ID) and it returns the matching set of attendance values*/
   public String[] findID(String x)
   {
	   int len = numOfLines();//number of line
	   int i;
	   for(i = 0; i < len; i++)
		   if(x.equals(attends[i][0]))//if id matches one of the lines
			   return attends[i]; //return the entire line with attendance values
	   
	return attends[i];//if not found, return anything as the program description assumes it will find the id
   }
   
   /**used by atString method that makes the attendance report content
    * returns array of classes sorted by averages*/
   public FitnessClass[] sortClasses()
   {
	 //array to hold non-null objects before sorting
	   FitnessClass[] temp = new FitnessClass[numOfClasses()];
	   
	   //go through all program and fill non-null objects in temp array
	   int t = 0;//to increment temp array
	   	for(int c = 0; c < maxNum; c++)
   		 if(classList[c] != null)
   		 	{ 
   			temp[t] = classList[c];//when not null fill temp and increment
   	 		t++;
   	 		}
	   
	   Arrays.sort(temp);//sort temp array per averages
	   return temp;
   }
   
   /**return string for attendance report*/
   public String atString()
   {
	   String s = "";//to hold entire string
	   
	   String header = "id \t class \t tutor \t      attendances "
	   + "\t  avg \n ===================================================================\n";//header string
	   
	   /**loop through all sorted classes and bring their strings*/
	   for(int i = 0; i < numOfClasses(); i++)
	   s += sortClasses()[i].toString() + "\n";
	   
	   //add up header string + main string + overall value in s string and return it
	   s = String.format("%s %s \n\t\t\t\t overall avg: %4.2f", header, s, avgAll()); 
	   return s;
   } 
   
   /**used by atString
    * returns overall average
    * */
   public double avgAll()
   {
	   double d = 0.0;
	   /**when not null, add up averages from all classes*/
	   for(int i = 0; i < maxNum; i++)
		   if(classList[i] != null)
			   d += classList[i].avg();
	   
	   d /= numOfClasses();//calculate average
	   
	   return d;
   }
   
   /*returns index for first free (null) position in the program*/
   public int findFirstFree()
   {
	   for(int i = 0; i < maxNum; i++)
		   if(classList[i] == null)
			   return i;//return first free spot
	   
	   return -1;//when program is full
		
   }
   
   /*checks if an ID already exists before adding or deleting a class*/
   public boolean ifExist(String id)
   {
	   for(int i = 0; i < maxNum; i++)//loops through the list of all classes
	   {
		 //if it finds a class with the same id it returns true
		   if(classList[i] != null)
			   if(id.equals(classList[i].getID()))
				   return true;
	   }
	  return false;//returns false if ID is not found
   }
   
   /*adds new class with values from text fields at the first free spot in the program*/
   public void addClass(String id, String name, String tut)
   {
	   String[] atts = {"ignored value", "0","0","0","0","0"};//initialize attendances of new class as 0's
	   int time;
	   int index = findFirstFree();//pulls first free spot index
	   
	   if(index == 0)//if first free spot is the first one set time at 9
		   time = 9;
	   else//otherwise, start time of the new class if the start time of previous class + 1
		   time = classList[index - 1].getStartTime() + 1;
	   
	   classList[index] = new FitnessClass(id, name, tut, time, atts);//adds new class to the first free spot on the program
   }
   
   	 /*deletes a class based on the ID user enters*/
	 public void deleteClass(String id)
	 {
		 for(int i = 0; i < maxNum; i++)//loops through the list of all classes
		   {
			 //if it finds a class with the same id it sets it to null
			   if(classList[i] != null)
				   if(id.equals(classList[i].getID()))
				   {
				   	classList[i] = null;
			   		break;
			   		}
		   }
	 }//end of delete Class method
   
  }//end of class
