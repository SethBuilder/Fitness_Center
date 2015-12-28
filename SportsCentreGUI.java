import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";
	
	/**an object to hold the fitness program object we'll create*/
	private FitnessProgram fp;
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(730, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		// more code needed here
		
		/**this is to read ClassesIn, feed it to FitnessProgram object*/
		FileReader reader = null;
		try {
			reader = new FileReader(classesInFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**put file content in scanner object*/
		Scanner scan = new Scanner(reader);
		
		/**create fitness program object by passing the scanner object and the attendances values*/
		fp = new FitnessProgram(scan, initAttendances());
		
		
		//closing reader object
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**fire up display*/
		updateDisplay();
		
	}//end of constructor

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		
		/**i eventually didn't have to use this method
		 * 
		 * but it sets a fitness class array to the value of the fitness program time table*/
		
		FitnessClass[] sched = fp.getSchedule();
	}

	/**
	 * Initializes the attendances using data
	 * from the file AttendancesIn.txt
	 * returns 2D string of all the value and passes them to the constructor
	 * of fitness program object
	 */
	public String[][] initAttendances() {
		
		/**reads the content of attendances file*/
	    FileReader reader = null;
	    try {
			 reader = new FileReader(attendancesFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    
	    int num = lineCounter();//number of lines in the file
	    Scanner scan = new Scanner(reader);//feeds reader content to a scanner object
	    String[] ats = new String[num];//holds each line before it is broken into string array
	    String[][] atData = new String[num][6];//2D array with rows = number of lines && columns = number of values in each row
	    
	    /**loops through all lines in the file, and broken in to string arrays*/
	    for (int i = 0; i < num; i++) {
	    	{
	    		ats[i] = scan.nextLine();//go to next line
	    		atData[i] = ats[i].split("[ ]+");//break line into 6 strings and put them in the rows of the 2D array
	    	}
	    	//now we have a 2D string array filled with the ID's and attendance values for all classes/
		}
	    
	    /**this value is ready to be fed into the fitness program object constructor*/
	    return atData;
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
	    
		FitnessClass[] fc = fp.getSchedule();//fetch time table from fitness program object
		String times = "";//to hold time slot for each class
		String classes = "";//to hold class names
		String tuts = "";//to hold tutors names
		for(int i = 0; i < 7; i++)//loop through all time slots(from 9 to 15)
		    {	
			int s = i + 9;//start time
			int t = i + 9 + 1;//end time for each 1 hour long class
			String ti = s + "-" + t;//to hold time slot, for example: 9-10
			
			String time = String.format("%-13s", ti);//to make sufficient space between classes
			times+=time;//add up all time slots
			
			
			String clas;//to hold each individual class
			String tut;//to hold each individual tutor name
			
			//if object is null then class name is available and tutor name is blank
			if(fc[i] == null)
				{clas = "Available";
				tut = "";}
			
			//else, fetch class name and tutor name
			else{
				clas = "" + fc[i].getClassName();
				tut = "" + fc[i].getTutorName();
				}
			
			//add up fetched info into the strings for classes and tutors
			classes+=String.format("%-13s", clas);
			tuts+=String.format("%-13s", tut);
			}
		
		//set display text to the strings for all classes
		display.setText("" + times + "\n" + classes + "\n" + tuts);
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
	    
		String id = idIn.getText();//get text from id field
		String name = classIn.getText();//get text from class name
		String tut = tutorIn.getText();//get text from tutor name
		int firstFree = fp.findFirstFree();//fetch first available time slot
		
		
		/**if program is full, inform user and clear fields*/
		if(firstFree == -1)
		{
			JOptionPane.showMessageDialog(null, "Sorry program is full", "Error message", 
			JOptionPane.ERROR_MESSAGE);
			
			//clear fields
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}
		
		/**check if class already exist*/
		else if(fp.ifExist(id) == true)
		{
			JOptionPane.showMessageDialog(null, "Class already exist", "Error message", 
			JOptionPane.ERROR_MESSAGE);
		
			//clear fields
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
		}
		
		/**if one of the fields is empty*/
		else if(id.isEmpty() || name.isEmpty() || tut.isEmpty())
			
			JOptionPane.showMessageDialog(null, "You must fill all fiels", "Error message", 
			JOptionPane.ERROR_MESSAGE);
		
		/**add class to time table*/
		else
		{
			fp.addClass(id, name, tut);//add new class
			
			JOptionPane.showMessageDialog(null, "New class has been added", "Confirmed", 
			JOptionPane.OK_OPTION);//show confirmation message
			
			//clears fields
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
			
			updateDisplay();//update time table
		}
		
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
		String id = idIn.getText();//get text from id field
		
		/**checks if empty*/
		if(id.isEmpty())
			JOptionPane.showMessageDialog(null, "Please enter the ID of the class you want deleted", "Error message",
			JOptionPane.ERROR_MESSAGE);
		
		/**check if class exist*/
		else if(!fp.ifExist(id))
			{
			JOptionPane.showMessageDialog(null, "Class does not exist", "Error message",
			JOptionPane.ERROR_MESSAGE);
			
			//clears fields
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
			}
		/**delete class*/
		else
			{
			fp.deleteClass(id);
			JOptionPane.showMessageDialog(null, "Class deleted", "Confirmed", 
			JOptionPane.OK_OPTION);//confirmation message
			
			//clears fields
			idIn.setText("");
			classIn.setText("");
			tutorIn.setText("");
			
			updateDisplay();//update display
			}
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
	    report = new ReportFrame();//create Report Frame object
	    JTextArea dis;//object to hold JTextArea
	    dis = report.getDisplay();//assign display to our object
	    
	    /**set display to data brought from method in fitness program object*/
	    	dis.setText(fp.atString());
	    
	    /**make report visible*/
	    report.setVisible(true);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
		/**open a file to save data*/
		PrintWriter writer = null;
	    try {
			 writer = new PrintWriter(classesOutFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    String s = "";//to hold data
	    FitnessClass[] f = fp.getSchedule();//fetch schedule from fitness program object
	    
	    /**loop through all time slots and bring data from non null objects*/
	    for(int i = 0; i < fp.getMax(); i++)
	    	if(f[i] != null)//while not null add up data to s string
	    		s += f[i].getID() + " " + f[i].getClassName() + " " + f[i].getTutorName() 
	    		+ " " + f[i].getStartTime() + "\n";
	    
	    /**write data to file*/
	    writer.println(s);
	    
	    //show confirmation message
	    JOptionPane.showMessageDialog(null, "Data saved to file", "Confirmed", JOptionPane.OK_OPTION);
	    
	    //close print writer object 
	    writer.close();
	    
	    //exit system
	    System.exit(0);
	    	}
	
	/**counts lines in the classesIn file*/
	public int lineCounter()
	{
		int numLines = 0;
		/**read file*/
		FileReader read = null;
		try {
			read = new FileReader(classesInFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**put file in a scanner object*/
		Scanner scan = new Scanner(read);
		
		//while there's a next line
		while(scan.hasNextLine())
			{
			numLines++;//increment
			scan.nextLine();//go to next line
			}
		return numLines;//return number of lines
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    
		/**if user clicks attendance button, display report*/
		if(ae.getSource() == attendanceButton)
			displayReport();
		
		/**if user clicks add button, process adding*/
		else if(ae.getSource() == addButton)
			processAdding();
		
		/**if user clicks delete button, process deletion*/
		else if(ae.getSource() == deleteButton)
			processDeletion();
		
		/**if user clicks close button, process save and close*/
		else if(ae.getSource() == closeButton)
			processSaveAndClose();
		
	}
}
