/*	Name: Brijesh Patel
	Course: CNT 4714 Fall 2019
	Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
	Due Date: October 6th, 2019
*/


import java.util.Random;

// Deposit Thread Class
public class Depositor implements Runnable 
{
   private static Random generator = new Random();
   private Account sharedLocation; 
   private String name;

   public Depositor( Account shared , String name)
   {
      sharedLocation = shared;
      this.name = name;
   } 
   public void run()
   {
	// Try to deposit a random amount an infinite amount of times.
      while (true) 
      {  
         try 
         {
            sharedLocation.set(generator.nextInt(249) + 1, name); 
            Thread.sleep(generator.nextInt(100) + 1); 
         } 
         catch ( InterruptedException exception ) 
         {
            exception.printStackTrace();
         } 
      } 
   } 
} 