import java.util.ArrayList;
import java.util.List;

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
	
	
	public static void main(String[] args) throws Exception {
		
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
