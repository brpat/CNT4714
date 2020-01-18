import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.SwingUtilities;

/*  Name:  Brijesh Patel
 * Course: CNT 4714 – Fall 2019      
 * Assignment title: Program 1 – Event-driven Programming
 * Date: Sunday September 15, 2019 
 * 
 * 
 */

public class BookStore {

	public static void main(String[] args) {
		
		System.out.println("Starting Application");
		
		SwingUtilities.invokeLater(new Runnable() {
			
			// Make JFrame Window run using a thread
			// Better way to update GUI this way.
			public void run() 
			{
				System.out.println("Creating UI");
				try {
					new MainFrame();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("File Not Found");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("IO Exception has occured");
				}	
			}
						
		});

	}

}
