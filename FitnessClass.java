/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
    private final int numOfWeeks = 5;
    private String classID;//class id
    private String className;//class name
    private String tutorName;// tutor name
    private int startTime;// start time
    private int[] atts;//attendances for the class
    
    /**constructor that takes id, name, tutor name, start time and an array of attendances*/
    public FitnessClass(String id, String cName, String tName, int time, String[] att)
    {
    	//assigns values
    	atts = new int[5];
    	classID = id;
    	className = cName;
    	tutorName = tName;
    	startTime = time;
    	atts = new int[5];
    	
    	//the value att that is passed is 6 spaces as the first one is id
		for(int i = 0; i < numOfWeeks; i++)
    		atts[i]	= Integer.parseInt(att[i + 1]);//assigning starts with i+1 to skip id spot
    }
    
    /**second constructor that takes one string for id, name , tutor, start time
     *  and an array of attendances*/
    public FitnessClass(String all, String[] att)
    {
    	//assigns values
    	atts = new int[5];
    	String[] allSplit = all.split("[ ]+");
    	classID = allSplit[0];
    	className = allSplit[1];
    	tutorName = allSplit[2];
    	startTime =	 Integer.parseInt(allSplit[3]);
    	
    	//the value att that is passed is 6 spaces as the first one is id
    	for(int i = 0; i < 5; i++)
    		atts[i]	= Integer.parseInt(att[i + 1]);//assigning starts with i+1 to skip id spot
    }
    
    //get and set for class ID
    public String getID(){return classID;}
    public void setID(String id){classID = id;}
    
    //get and set for class name
    public String getClassName(){return className;}
    public void setClassName(String name){className = name;}
    
  //get and set for tutor name
    public String getTutorName(){return tutorName;}
    public void setTutorName(String name){tutorName = name;}
    
  //get and set for start time
    public int getStartTime(){return startTime;}
    public void setStartTime(int time){startTime = time;}
    
  //get and set for attendances
    public int[] getAttendances(){return atts;}
    public void setAttendances(int[] att)
    {
    	for(int i = 0; i < 5; i++)
    		atts[i] = att[i];
    }
    
    
    /*returns the average of the class in question*/
    public double avg()
    {	
    	double ave = 0;
    	double av = 0;
    	
    	for(int i = 0; i < numOfWeeks; i++)
    		  av += atts[i];
    	ave = av / numOfWeeks;
    	return ave;
    }
    
    /*a method that returns a string for the attendance report*/
    
    public String toString()
    {
    	String st;//to hold the string
    	
    	//add up all values with proper spacing
    	st = String.format("%-15s \t %s  \t  %-10s  \t %02d %s %02d %s %02d %s %02d %s %02d \t  %05.2f", getID(), getClassName(), 
    	getTutorName(), getAttendances()[0], "  ", getAttendances()[1], "  ", getAttendances()[2], 
    	"  ", getAttendances()[3],"  ",  getAttendances()[4], avg());
    	
    	return st;
    }
    

    /**compares classes per averages*/
    public int compareTo(FitnessClass other) {
    	
    	if(this.avg() > other.avg())
    		return -1; //return -1 if this object is greater
    	
    	else if(this.avg() == other.avg())
    		return 0;//return 0 if equal
    	
    	else
    		return 1;//return 1 if this object is less than other object
    }
}
