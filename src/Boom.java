import java.util.ArrayList;
import java.util.List;

public class Boom {

	public static void main(String[] args) throws Exception {
		
		System.out.println("boom");
		
		List<Customer> customers = new ArrayList<Customer>();
		
		while (true){
			
			Thread.sleep(2);
			Customer c1= new Customer();
			customers.add(c1);
			
		}
		
		
	}
	
	
	
	
}
