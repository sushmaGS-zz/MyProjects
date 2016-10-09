import java.util.*;

public class Task extends TimerTask {
    
    public static String pair="";
    public static Double target = 0.0;
	// run is an abstract method that defines task performed at scheduled time.
	public void run() {
	    //System.out.println("\nThe pair received is: "+pair);
				Double Bid = WebReader.getBid(pair); // we get Bid value from XML parsed data
				System.out.println("\nThe target was: "+target+"\nThe current Bid for this pair is :"+Bid);
		if (Bid >= target) {
			System.out.println("\nTARGET VALUE REACHED !! ");
		}
		else
		System.out.println(""); //We don't need to display anything until target value is reached
	}
}

