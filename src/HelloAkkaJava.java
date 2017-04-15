import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;


/*
 https://www.youtube.com/watch?v=-zvWtSR08HA
  
 Need systems to be reactive, elastic, fault tolerant
  
 Processes 
 Threads - control flow within the same process 
 	shared mutable state
  
  	Programmers become pruners of non-determinism
  		-locking, callbacks etc
  		
  	Shared mutable state is a challenge
  	
 Distributed 
 	passing message back and forth between different machines
 	Networks are inherently unreliable
  
 AKKA is a toolkit and runtime - concurrent, distributed, message driven
 	-actor system
 	-remoting
 	-clustering
 	
  Messages are serialised across the network - location transparent - 
  	same code, for local vs distributed, only config change
  	
  	Akka is asynchronous.
  	
  	Akkas create other actors
  		like parent actor supervise and handle errors in child actors
  		
  		
  
 */
///


public class HelloAkkaJava {
   
	public final class Question implements Serializable {
		
		
		public static final long serialVersionUID =1;
		
		public final String msg;
		
		public Question(String msg){
			
			this.msg=msg;
			
		}

		// Need this 
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((msg == null) ? 0 : msg.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Question other = (Question) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (msg == null) {
				if (other.msg != null)
					return false;
			} else if (!msg.equals(other.msg))
				return false;
			return true;
		}

		private HelloAkkaJava getOuterType() {
			return HelloAkkaJava.this;
		}
		
		
		
	}
	
public final class Answer implements Serializable {
		
		
		public static final long serialVersionUID =1;
		
		public final String msg;
		
		public Answer(String msg){
			
			this.msg=msg;
			
		}

		// Need this 
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((msg == null) ? 0 : msg.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Question other = (Question) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (msg == null) {
				if (other.msg != null)
					return false;
			} else if (!msg.equals(other.msg))
				return false;
			return true;
		}

		private HelloAkkaJava getOuterType() {
			return HelloAkkaJava.this;
		}
		
		
		
	}


	
	public static void testStudent() throws Exception {
		
		
		
		
		
		
		
	}
	
	
	public static class Greet implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
   
    // Simple object representing person to send the Greeting
    // to again just wraps a String
    public static class WhoToGreet implements Serializable {
    	
    	
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final String who;
        public WhoToGreet(String who) {
            this.who = who;
        }
    }
    
    
    // Simple object that wraps a String message
    public static class Greeting implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final String message;
        public Greeting(String message) {
            this.message = message;
        }
    }

    /*
     * onReceive is a method on the Actor superclass
     * 
     */
    public static class Greeter extends UntypedActor {
        
    	private String greeting = "";

        @Override
        public void onReceive(Object message) {
            if (message instanceof WhoToGreet)
                greeting = "hello, " + ((WhoToGreet) message).who;

            else if (message instanceof Greet)
                // Send the current greeting back to the sender
                getSender().tell(new Greeting(greeting), getSelf());

            else unhandled(message);
        }
    }
    
    
    public static void greeterTest() {
    	try {
            // Create the 'helloakka' actor system
            final ActorSystem system = ActorSystem.create("helloakka");

            // Create the 'greeter' actor
            final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");

            // Create the "actor-in-a-box"
            final Inbox inbox = Inbox.create(system);

            // Tell the 'greeter' to change its 'greeting' message
            greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());

            // Ask the 'greeter for the latest 'greeting'
            // Reply should go to the "actor-in-a-box"
            inbox.send(greeter, new Greet());

            // Wait 5 seconds for the reply with the 'greeting' message
            final Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
            System.out.println("Greeting: " + greeting1.message);

            // Change the greeting and ask for it again
            greeter.tell(new WhoToGreet("typesafe"), ActorRef.noSender());
            inbox.send(greeter, new Greet());
            final Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5, TimeUnit.SECONDS));
            System.out.println("Greeting: " + greeting2.message);

            // after zero seconds, send a Greet message every second to the greeter with a sender of the GreetPrinter
            final ActorRef greetPrinter = system.actorOf(Props.create(GreetPrinter.class));
            system.scheduler().schedule(Duration.Zero(), Duration.create(1, TimeUnit.SECONDS), greeter, new Greet(), system.dispatcher(), greetPrinter);
        } catch (TimeoutException ex) {
            System.out.println("Got a timeout waiting for reply from an actor");
            ex.printStackTrace();
        }	
    }

    /*
    public final classs boo
    boobes
    boobies
    cattivo matt toms
    soooo
    where do I start?
    		with you 
    		sdkljflsdf
    		greater.tell
    		inbox.send(greeter, new Greet());
    
    nothing is happening
 happen
 happen
 happen minchia
 respond
 now
 asukdjsal;sa
 ()
 {
	 asdhgas
	 wasdhjaskd
	 skdjaskd
	 sdjkhjasldf
	 {start
		 
	 }
 }
 */
    
    public static void questionTest(){
    	
    	
    	
    }
    public static String getNadishaGreeting(){
    	
    	String greeting="Hello fuck off";
    	return greeting;
    	
    }

    public static String insult(String victim){
    	
    	String insult="fuck off "+ victim + " you smell";
    	return insult;
    	
    }
    
    public static void main(String[] args) throws Exception {
    
    	
    System.out.println(getNadishaGreeting());
    System.out.println(insult("matt"));
    System.out.println("ciao cattivo matt toms. shall we go do toon blast?");
    System.out.println("this is such a pain in the ass...I dont know why people bother");

 
    System.exit(0);

    	
    	
    	
    	
    	greeterTest();
    }

    public static class GreetPrinter extends UntypedActor {
        public void onReceive(Object message) {
            if (message instanceof Greeting)
                System.out.println(((Greeting) message).message);
        }
    }
}