import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Boom {

	public static class Byer extends Greeter {

		public Byer(Bosh bosh, String name, Object lock) {
			super(bosh, name, lock);	
			
			
		}
		
		@Override
		public void run() {
			
			try {
				bosh.sayBye(name, lock);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
		
	}
	
	
	public static class Greeter implements Runnable {

		protected Bosh bosh;
		protected String name;
		protected Object lock;
		
		public Greeter(Bosh bosh, String name,Object lock){
			this.bosh=bosh;
			this.name=name;
			this.lock=lock;
		}
		
		@Override
		public void run() {
			
			try {
				bosh.sayHello(name, lock);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	
	public static class NumbersTask implements Runnable {

		@Override
		public void run() {
		
				for (int i=1; i<=100000; i++){
					System.out.println("task: "+Thread.currentThread().getName()+ " is outputting " + i);
					if (Thread.interrupted()){
						break;
					}
					
				}
				System.out.println("task: "+Thread.currentThread().getName()+ " is finished");
				
			
		}
		
		
		
	}
	
	
	/*
	 In Java thread pools are calls executor service
	 
	 FixedThreadPool - It limits the number of threads 
	 CacheThreadPool
	 SingleThreadExecutor - only one thread initially, you cannot
	 							change number of thrad
	 ScheduledThreadPool - set threads to run on a scheduler
	 
	 
	 And creates the objects in advanced
	 
	 
	 */
	public void testThreadPool() throws Exception{
		
		ExecutorService pool =
				Executors.newFixedThreadPool(20);
		//need to shutdown service.
		
		NumbersCallable callable = new NumbersCallable();
		
		Future<Double> result = pool.submit(callable);
		
		
		
	}
	public static class NumbersCallable implements Callable<Double>{

		@Override
		public Double call() throws Exception {
			
			System.out.println("boom");
			
			return 2d;
			
		}
		
	}
	
	
	
	public static void testInterrupted() throws InterruptedException{
		
		Thread t = new Thread(new NumbersTask());
		t.start();
		Thread.sleep(2000);
		
		// this sets the interrupted flag on the
		// thread object.
		
		// if we have code that queries the state
		// of the thread and breaks if the interrupted
		// flag is set to true then that is what
		// will happen
		t.interrupt();
		
		
	}
	
public static void testJoin() throws InterruptedException{
		
		Thread t1 = new Thread(new NumbersTask());
		Thread t2 = new Thread(new NumbersTask());
		
		t1.start();
		t2.start();
		Thread.sleep(2000);
		
		// this sets the interrupted flag on the
		// thread object.
		
		// if we have code that queries the state
		// of the thread and breaks if the interrupted
		// flag is set to true then that is what
		// will happen
		t1.join();
		t2.join();
		// all this does is pause the current thread until
		// the above threads have finished
		
		System.out.println("Finished");
		
	}
	
	public static void main(String[] args) throws Exception {
		
		testJoin();
		System.exit(1);
		
		//testInterrupted();
		
		System.out.println("boom");
		
		List<Customer> customers = new ArrayList<Customer>();
		
		Bosh bosh1 = new Bosh();
		Bosh bosh2 = new Bosh();
		
		Integer lock1 = 1;
		Integer lock2 = 2;
		
		// Key point here is if we use the same
		// lock object and the same bosh instance
		// then the calls will block
		
		// if we use the same monitor lock objec
		// but on a different instance of bosh
		// they WILL block - even if it is 
		// a separate instance - the lock is on
		// the monitor, not the object with the
		// synchroised block
		
		// if we use the same bosh instance but
		// different lock object, then the methods
		// will NOT block
		
		
		Greeter g1 = new Greeter(bosh1,"1",lock1);
		Greeter g2 = new Greeter(bosh2,"2",lock1);
		
		
		new Thread(new Greeter(bosh1,"1",lock1)).start();
		new Thread(new Byer(bosh1,"2",lock1)).start();
		
		
		
		
		
		
		
		/*
		while (true){
			
			Thread.sleep(2);
			Customer c1= new Customer();
			customers.add(c1);
			
		}
		*/
		// wait and notify are used to
		// avoid dead locks
		
		/*
		 deadlock when all threads are waiting
		 to enter a synchronised block but one
		 thread is holding the lock and not going to
		 leave
		 
		 
		 
		 */
		
		

		
		
	}
	
	
	
	
}
