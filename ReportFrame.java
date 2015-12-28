import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
	
	private JTextArea display;
        
	public ReportFrame()
	{
		this.setSize(510, 230);
		this.setTitle("Attendance Report");
		this.setLocation(100, 100);
		
		/*this is not to close the entire program upon closing this window*/
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		display = new JTextArea();//create JTextArea field
		
		add(display, BorderLayout.CENTER);//adds it to Center
	}
	
	public JTextArea getDisplay(){return display;}//returns display to write to - used by GUI class
}
