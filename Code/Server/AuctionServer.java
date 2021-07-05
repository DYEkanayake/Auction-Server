import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.*;
import java.awt.*;

public class AuctionServer{

	public static final int port=2000; 
	private static ServerSocket serverSocket;

	public AuctionServer() throws IOException{ //constructor
		this.serverSocket=new ServerSocket(port);
		this.createConnection();

	}

	public void createConnection() throws IOException{  
//create connection with each Client.(A Client object represents a single connection and as the AuctionServer will be maintaining multiple connections at once,Client is a thread.) 
		
		while(true){  //for the multiple client connections
			Socket socket = serverSocket.accept();
			
		/*	OutputStream s1out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(s1out);
		
		//utf-8 encoded
			dos.writeUTF("Hello from server!");*/
			
			Client client=new Client(socket); //Thread newClient;       newClient=new Client(socket); #########???????CHECK THE CODE HERE
			Thread newClient=new Thread(client);
			newClient.start(); //Cleint is a thread. Start() method invoking
			
		}
	}
	
	

	public static void main(String[] args) throws IOException {
		//System.out.println("works");
		StockDatabase sdb=new StockDatabase("stocks.txt"); //csv file name??????
		//INITIALIZE BIDSTATUS & AND BIDHISTORY .      Does bidHostory needed to be initialized?
		//TableModel tm=new TableModel("bidStatus");
		
		
		//JFrame
		JFrame frame = new JFrame("Server Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ServerGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
		
		/*JFrame frame = new JFrame("Current Stock Prices");      //Create new GUI
        frame.setSize(600, 600);
  		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		//frame.add(new ServerGUI());                         //Calling Display class Constructor
  		frame.pack();
        frame.setVisible(true);
		*/
		AuctionServer server=new AuctionServer();
	
	}
	
}

