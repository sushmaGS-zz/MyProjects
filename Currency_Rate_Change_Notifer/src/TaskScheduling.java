import java.util.Timer;
import java.util.Scanner;
public class TaskScheduling {

    public static void main(String[] args) throws InterruptedException {

        Timer time = new Timer(); // Instantiate Timer Object
        Task st = new Task(); // Instantiate Task
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the currency pair you would like to observe: ");
        String pair = input.nextLine();
        Task.pair = pair;
        Double target;
		System.out.print("\nEnter target bid: ");
		target = input.nextDouble();
		Task.target = target;
        time.schedule(st, 0, 1000); // Creates task Repetitively for every 1 sec

        // for demo only.
        for (int i = 0; i <= 50; i++) {
            Thread.sleep(2000);

        }
    }
}
