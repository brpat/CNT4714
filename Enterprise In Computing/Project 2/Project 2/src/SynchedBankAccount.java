/*	Name: Brijesh Patel
	Course: CNT 4714 Fall 2019
	Assignment title: Project 2 – Synchronized, Cooperating Threads Under Locking
	Due Date: October 6th, 2019
*/

//Synchronize synchronizes access to a single shared integer.
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class SynchedBankAccount implements Account {
//  Will use to regulate access to account 
	private Lock accessLock = new ReentrantLock();

	// lock condition for withdraw and deposit threads
	private Condition canRead = accessLock.newCondition();

	private int account = 0;
	// Will specify if a thread is using the account
	private boolean occupied = false;

	public void set(int deposit, String name) {
		// lock account
		accessLock.lock();
		account += deposit;
		occupied = true;
		System.out.printf(" %s %s $%-77d%s $%d\n", name, "deposits", deposit, "Balance is", account);
		canRead.signal();
		accessLock.unlock();
	}

	public int get(int value, String name) {
		int readValue = 0;
		// lock this object
		accessLock.lock();

		try {
			// while no data to read, place thread in waiting state
			while (!occupied) {
				// When account contains a lot of deposits
				// Thread will then continue
				canRead.await();
			}

			readValue = account;
			// Check if we have a valid withdraw amount
			if (value > readValue) {
				System.out.printf("\t\t\t\t\t %s %s $%-25d %s\n", name, "withdraws", value,
						"WithDrawal - Blocked - Insufficient Funds");
				canRead.await();
			}

			account = account - value;
			System.out.printf("\t\t\t\t\t %s %s $%-35d%s $%d\n", name, "withdraws", value, "Balance is", account);

		}

		catch (InterruptedException exception) {
			exception.printStackTrace();
		} finally {
			// unlock account
			accessLock.unlock(); 
		}

		return readValue;
	}

}
