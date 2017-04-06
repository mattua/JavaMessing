
public class Bosh {

	public void sayHello(String from,Object lock) throws Exception{
		
		synchronized (lock)
		{
			
			
			System.out.println("start Hello from "+from);
			lock.wait();
			Thread.sleep(10000);
			System.out.println("end Hello from "+from);
		}
		
	}
	
	// with no waits specified, the first
	// method to acquire the lock will get it
	// run to completion - before releasing the
	// lock - so the second can begin the method
	// so you will see 
	/*
	start Bye from 1
	end Bye from 1
	start Bye from 2
	end Bye from 2
	
	REMEMBER WE CALL WAIT ON THE LOCK OBJECT
	NOT THE OBJECT CONTAINING THE SYNCHRONISED
	BLOCK (THOUGH THEY COULD BE THE SAME IF
	SYNCHRONISING ON THIS
	
	if we put a wait Hello in one method after start,
	but no notify in the bye method 
	start Hello from 1
	start Bye from 2
	end Bye from 2
	and the first one hangs....
	
	If we put a notifyAll in the second method
	then WHEN that method completes and releases
	the lock, the first thread is woken up and
	will attempt to regain the lock. 
	
	Without the notify all, the first thread will
	remain waiting forever, even though the second
	one has release the locked having completed
	
	Notifyall does NOT let go of the lock. It
	is a signal to other waiting threads to try to 
	attempt to acquire the lock once the the notifyAll
	thread releases the lock
	
	*/
	
	
	
	public void sayBye(String from,Object lock) throws Exception{
		
		synchronized (lock)
		{
			
			System.out.println("start Bye from "+from);
			lock.notifyAll();
			Thread.sleep(10000);
			
			
			System.out.println("end Bye from "+from);
		
			
		
		}
		
	}
	
}
