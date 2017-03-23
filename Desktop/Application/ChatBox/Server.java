import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server extends Thread {
   static private ServerSocket serverSocket;
   //static private String text="";
   static Scanner sc;
   static Socket server;
   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      sc=new Scanner(System.in);
      server=null;
      //serverSocket.setSoTimeout(10000000);
   }
   public static void asyncSendServiceMethod(){
	   while(true)
	   {
       //System.out.print("send");
	   if(server!=null)
	   {
		   try {
		   String msg=sc.nextLine();
		   DataOutputStream out = new DataOutputStream(server.getOutputStream());
		   out.writeUTF(msg);
		   } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   }
		}
	   }
   }
   public static void asyncReceiveServiceMethod(){
	   while(true)
	   {
		   //System.out.print("receive");
		   try {
    	   DataInputStream in = new DataInputStream(server.getInputStream());
    	   //if(in.readUTF().length()>0)
    		   System.out.println(in.readUTF());
           } catch (Exception ex) {
           //handle error which cannot be thrown back
           }
	   }
   }
   
   public static void main(String [] args) {
	   int port = Integer.parseInt(8082+"");
	   //Thread for running server continuously
	   try {
	         Thread t = new Server(port);
	         t.start();
	         System.out.println("Waiting for client on port " + 
	                 serverSocket.getLocalPort() + "...");
	         server = serverSocket.accept();
	      }catch(IOException e) {
	         e.printStackTrace();
	      }
	   //Thread for reading
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
