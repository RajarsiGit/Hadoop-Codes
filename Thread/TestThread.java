import java.io.*;
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   private int n;
   
   RunnableDemo( String name, int num) {
      threadName = name;
      n = num;
      System.out.println("Creating: " +  threadName );
   }
   
   public void run() {
      System.out.println("Running: " +  threadName );
      try {
         for(int i = n; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(1);
         }
      }catch (InterruptedException e) {
         System.out.println("Thread: " +  threadName + " interrupted.");
      }
      System.out.println("Thread: " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting: " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class TestThread {
   public static void main(String args[]) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int n=0;
      System.out.println("Enter number of processes: ");
      try{
         n = Integer.parseInt(br.readLine());
      }
      catch(Exception e){}
      RunnableDemo R1 = new RunnableDemo("Thread-1",n);
      R1.start();
      
      RunnableDemo R2 = new RunnableDemo("Thread-2",n);
      R2.start();

      RunnableDemo R3 = new RunnableDemo("Thread-3",n);
      R3.start();
   }   
}