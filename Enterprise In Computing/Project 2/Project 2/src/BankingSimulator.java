
/*	Name: Brijesh Patel
	Course: CNT 4714 Fall 2019
	Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
	Due Date: October 6th, 2019
*/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankingSimulator {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		Account account = new SynchedBankAccount();
		// Column Headers.
		System.out.format("%-5s %40s %47s", "Deposit Threads", "Withdrawal Threads", "Balance\n");
		System.out.format("%-5s %40s %55s", "---------------", "----------------", "---------------\n");
		// Create 4 deposit threads and 8 withdraw threads
		// Send them to executor
		executor.execute(new Depositor(account, "D1"));
		executor.execute(new Depositor(account, "D2"));
		executor.execute(new Depositor(account, "D3"));
		executor.execute(new Depositor(account, "D4"));
		executor.execute(new Withdrawer(account, "W1"));
		executor.execute(new Withdrawer(account, "W2"));
		executor.execute(new Withdrawer(account, "W3"));
		executor.execute(new Withdrawer(account, "W4"));
		executor.execute(new Withdrawer(account, "W5"));
		executor.execute(new Withdrawer(account, "W6"));
		executor.execute(new Withdrawer(account, "W7"));
		executor.execute(new Withdrawer(account, "W8"));

		executor.shutdown();
	}

}
