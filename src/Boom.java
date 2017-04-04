import java.util.ArrayList;
import java.util.List;

public class Boom {

	public static void main(String[] args) throws Exception {
		
		System.out.println("boom town");
		
		List<Customer> customers = new ArrayList<Customer>();
		
		while (true){
			
			if (Math.random()<0.3){
				Thread.sleep(1);
			}
			
			Customer c1= new Customer();
			customers.add(c1);
			
		}
		
		
	}
	
	
	
	
}
