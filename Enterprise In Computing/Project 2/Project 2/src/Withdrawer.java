/*	Name: Brijesh Patel
	Course: CNT 4714 Fall 2019
	Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
	Due Date: October 6th, 2019
*/


import java.util.Random;

// Withdraw thread class
public class Withdrawer implements Runnable 
{ 
	private static Random generator = new Random();
	private Account sharedLocation; 
	private String name;
	
	public Withdrawer( Account account, String name)
	{
	   sharedLocation = account;
	   this.name = name;
	} 
	
	public void run()
	{
	   // Try to withdraw a random amount an infinite amount of times.
	   while (true) 
	   {
		 try 
	     {
	         sharedLocation.get(generator.nextInt(49) + 1, name);
	         Thread.sleep(generator.nextInt(10) + 1);
	     } 
	     catch ( InterruptedException exception ) 
	     {
	         exception.printStackTrace();
	     } 
	   } 
	}
} 