import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	static String serverName;
    static int port;
    static Scanner sc;
    static OutputStream outToServer;
    static DataOutputStream out;
    static Socket client;
    
	 public static void asyncSendServiceMethod(){
	                   send();
	    }
	 public static  void asyncReceiveServiceMethod(){
               receive();
	    }
   public static void send()
   {
	   while(true)
	      {
		   //System.out.println("in send");
		      try {
		         
		         //if(count==0)
		        	 //System.out.println("Just connected to " + client.getRemoteSocketAddress());
		    	 System.out.println("client "+client);
		         outToServer = client.getOutputStream();
		         out = new DataOutputStream(outToServer);
		         System.out.println("out "+out);
		         String msg=sc.nextLine();
		         if(msg.length()>0)
		        	 out.writeUTF(msg);
		         //out.writeUTF("Hello from " + client.getLocalSocketAddress());
		         //if(in.readUTF().length()>0)
		         System.out.println("Cient says " + msg);
		         //out.close();
		         //client.close()
		      }catch(IOException e) {
		         e.printStackTrace();
		      }
		      catch(Exception e) {
			         e.printStackTrace();
			      }
	      }
   }
   public static void receive()
   {
	   while(true)
	      {
		      try {
		    	  //System.out.println("In recieve");
		         //Socket client = new Socket(serverName, port);
		         InputStream inFromServer = client.getInputStream();
		         DataInputStream in = new DataInputStream(inFromServer);
		         if(in.readUTF().length()>0)
		        	 System.out.println("Server says "+in.readUTF());
		         //client.close();
		      }catch(IOException e) {
		         e.printStackTrace();
		      }
	      }
   }
   public static void main(String [] args) {
	      serverName = "localhost";
	      port = Integer.parseInt("8082");
	      System.out.println("Connecting to " + serverName + " on port " + port);
	      sc=new Scanner(System.in);
	      try {
			client = new Socket(serverName, port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      Thread t1 = new Thread() {
	           public void run() {
	        	   asyncReceiveServiceMethod();
	           }
	       };
	       t1.start();
		   //Thread for sending
	       Thread t2 = new Thread() {
	           public void run() {
	        	   asyncSendServiceMethod();
	           }
	       };
	       t2.start();
   }
}