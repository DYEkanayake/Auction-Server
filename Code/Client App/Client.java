import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.*;

import java.util.*;

import java.net.*;

public class Client implements Runnable{ 
	
	private Socket clientSocket;
	private BufferedReader input;
	private PrintWriter output;

	private double bidPrice=-1; //just as an initial value(negative value assuming not valid)

	
	public static Map<String,BidStatus> bidStatus = new HashMap<String,BidStatus>();  //keeps current highest bid for all stock items
	public static Map<String,ArrayList<BidHistory>> bidHistory = new HashMap<String,ArrayList<BidHistory>>();  //keeps bid history of stock items

	public static ArrayList<String> usernames=new ArrayList<String>(); //keeps all the usernames.

	public Client(Socket clientSocket){  
		this.clientSocket=clientSocket;
		try {
			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); //what the user inputs
			this.output = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));  //what's being diaplayed to the user
			
		}
		catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}
		
	}
	
	public void run(){  
		
		try {
           
			output.print("Welcome to the Auction Server!\n");
			output.flush();
			output.print("If you wish to quit the server enter 'quit' \n");
			output.flush();
		//First,the user name is requested
			output.print("Please, enter the name: ");
			output.flush();
			
			String clientName=getUserInput();
			
			if(clientName.equals("quit")){
				output.print("\nServer exits. Thank you!"); 
				output.flush();
				clientSocket.close(); 
			}
			while(clientName.equals("")){ //in case client has entered an empty string
				output.print("\nYou haven't entered the username.Please, enter the name: ");
				output.flush();
				clientName=getUserInput();
				if(clientName.equals("quit")){
					output.print("\nServer exits. Thank you!"); 
					output.flush();
					clientSocket.close(); 
				}
			}
			
			//////////////////////////////
			
			if(usernames.contains(clientName)){ //in case username already exists.
				output.print("\nCAUTION : A user with this username already exists.If that's not you please quit the server and reconnect.\n");
				output.flush();
			
			}
			if(!(usernames.contains(clientName))){
				usernames.add(clientName); //at this point user name is confirmed by the user. So, added to database.
			}
		///////////////////
		
		//Second,the symbol is requested
			output.print("\nPlease, enter the Symbol: ");  
			output.flush();
			
			String symbol=getUserInput();
			if(symbol.equals("quit")){
				output.print("\nServer exits. Thank you!"); 
				output.flush();
				clientSocket.close(); 
			}
			while(symbol.equals("")){ //if quit should quit
				output.print("\nYou haven't entered the symbol of the security.Please, enter the symbol: "); 
				output.flush();
				symbol=getUserInput();
				if(symbol.equals("quit")){
					output.print("\nServer exits. Thank you!"); 
					output.flush();
					clientSocket.close(); 
				}
			}
			
			/*
			if(!(StockDatabase.database.containsKey(symbol))){
				output.print("\n-1"); //Replies with -1 to indicate the key is invalid
				output.flush();
				//what happens now?????
			}
			*/
			//SHOULD THE USER BE ALLOWED TO CARRY ON? IF SO,
			
			while(!(StockDatabase.database.containsKey(symbol))){
				output.print("\n-1"); //Replies with -1 to indicate the key is invalid
				output.print("\nYou haven't entered a valid symbol.Please, enter the symbol: "); 
				output.flush();
				symbol=getUserInput();
				
				while(symbol.equals("")){ 
					output.print("\nYou haven't entered the symbol of the security.Please, enter the symbol: "); 
					output.flush();
					symbol=getUserInput();
					if(symbol.equals("quit")){
						output.print("\nServer exits. Thank you!"); 
						output.flush();
						clientSocket.close(); 
					}
				}
			}
			
			
		//Once a valid symbol is entered, the server replies with the current cost/highest bid of the security
			double currentCost=bidStatus.get(symbol).getHighestBid();
			output.print("\nThe current price of the security"+symbol+" is "+ currentCost); 
			output.flush();

		//Client is asked for her/his bid
			output.print("\nPlease place your bid: "); 
			output.flush();
			
			String bid=getUserInput();
			if(bid.equals("quit")){
				output.print("\nServer exits. Thank you!"); 
				output.flush();
				clientSocket.close(); 
			}

			while(bid.equals("")){ 
				output.print("\nYou haven't entered the bid.Please, enter the bid: "); 
				output.flush();
				bid=getUserInput();
				if(bid.equals("quit")){
					output.print("\nServer exits. Thank you!"); 
					output.flush();
					clientSocket.close(); 
				}
			}
			
			while(bidPrice==-1){  
				try {   
					bidPrice=Double.parseDouble(bid); //Check for valid Format of bid  //NO NEED TO HANDLE NEGATIVE VALUES?
				}
				catch (NumberFormatException e) {      //If not ask for a valid Bid
					output.println("\nInvalid Format for the bid. Please enter in valid format(currency): ");  
					output.flush();
					bid=getUserInput();
					if(bid.equals("quit")){
						output.print("\nServer exits. Thank you!"); 
						output.flush();
						clientSocket.close(); 
					}
					
					//clientSocket.close(); //OR THE SERVER EXITS (AND NO WHILE LOOP). CHANGE THIS TO LET THE USER GIVE THE BID IN THE PROPER FORMAT
				}
				
			}

		//Updating bidHistory 
		if(bidHistory.containsKey(symbol)){
			BidHistory newRecord=new BidHistory(symbol,clientName,bidPrice);
			bidHistory.get(symbol).add(newRecord);  //add the new entry to the array list of bid records
		}
		else if(!(bidHistory.containsKey(symbol))){
			BidHistory newRecord=new BidHistory(symbol,clientName,bidPrice);
			ArrayList<BidHistory> newEntry=new ArrayList<BidHistory>();
			newEntry.add(newRecord);
			bidHistory.put(symbol,newEntry);
		}
		
		//Updating bidStatus (if necessary)	
			if(currentCost<bidPrice){ //Or if null?
				bidStatus.get(symbol).setHighestBid(bidPrice);
			}
			
		
		
		output.print("\nBid completed.\nServer exits. Thank you!");
		output.flush();
		
		
		output.close();
        input.close();
		this.clientSocket.close();
			
		}
		catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
		finally{
			try{
                this.clientSocket.close(); 
            }catch(Exception e){}
			
		}
		
	}

	public String getUserInput(){  //method to get the user input 
		String userInput="";
		try{
		userInput=input.readLine(); //make  buffer reader read the line
		}
		catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}
		return userInput;
	}
	
	

}

class BidStatus{  //data structure to keep the bid status(current highest bid of a stock item)

	private String symbol;  
	private String name;
	private double highestBid; 
	
	
	public BidStatus(String symbol,String name,double highestBid){
		this.symbol=symbol;  
		this.name=name;
		this.highestBid=highestBid;

	} 
	public String getSymbol(){  //to get the symbol of the security
		return symbol;
	}

	public String getName(){  //to get the name of the security
		return name;
	}

	public double getHighestBid(){  //to get the current cost/highest bid of a security
		return highestBid;
	}
	
	public void setHighestBid(double currentHighest){  //to set the current cost/highest bid of a security
		highestBid=currentHighest;
	}
	

}

class BidHistory{  //data structure to keep the bid history of a stock item

	private String symbol;  
	private String clientName;
	private double bid; 
	private Date date;
	//No.of shares(AS A USER INPUT)
	
	public BidHistory(String symbol,String clientName,double bid){
		this.symbol=symbol;  
		this.clientName=clientName;
		this.bid=bid;
		date=new Date();
	} 
	
	public String getSymbol(){  //to get the symbol of the security
		return symbol;
	}

	public String getClientName(){  //to get the username of the client
		return clientName;
	}

	public double getBid(){  //to get the  bid of a security
		return bid;
	}
	
	
	public String getDate(){  //to get the  date and time of bidding
		return date.toString();
	}
	


	
}