import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientApp {
	
	public static final int GET_NAME = 0;      //Initial case for get Clients' Name
	public static final int GET_SYMBOL = 1;     //Case for get the symbol
	public static final int GET_BID_PRICE = 2;  //Case for get the bid amount

	private static int currentState;           //Variable to switch between states    
	static String clientMsg;    // this updates with what user's answer is
	static String serverMsg = new String();	//the messege client get from server
	
	
	public static void main(String [] args) throws IOException{
   
		//server ip and port
		String serverName = "localhost";
		int port = 2000;
		
		System.out.println("Waiting for a server...");
			
		Socket socket = new Socket(serverName, port);

		
		try{
       
			Scanner sc = new Scanner(System.in); //System.in is a standard input stream.

			BufferedReader inMessege = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter outMessege = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			currentState = GET_NAME;


			serverMsg = inMessege.readLine();//.........Welcome to the Auction Server....blah blah!
			serverMsg = inMessege.readLine();//..........If you wish to quit the server enter 'quit'
			
			//*************************************************************************************************************
			serverMsg = inMessege.readLine();//..........Please, enter the name:
			//System.out.println(serverMsg);
			
			
			while(true){
             				
				switch (currentState){                 //Switch between states
                
					case GET_NAME:                     //Intial Case to get Client Name
					  
						//**************** clientMsg will be updated in GUI
					   // clientMsg = sc.nextLine();  //this is the client name now
					    
						outMessege.print(clientMsg);  //send the client name 
						outMessege.flush();
						
						if(clientMsg.equals(" ")){
							
							serverMsg = inMessege.readLine(); //"You haven't entered usename.Please, enter the name: "
							//System.out.println(serverMsg);
							currentState = GET_NAME;
							
						} else if(clientMsg.equals("quit")){
							
							serverMsg = inMessege.readLine(); //"Server exits. Thank you!"
							//System.out.println(serverMsg);
							outMessege.close();
							inMessege.close();
							socket.close();
							
						}else{
						
							serverMsg = inMessege.readLine(); 
							//System.out.println(serverMsg);
							
							if (serverMsg.equals("CAUTION : A user with this username already exists.If that's not you please quit the server and reconnect.")) {
								//the name you entered may be already entered
								currentState = GET_NAME;
							
							}else {
								//ok you have entered a valid name 
								//now the serverMsg = "Please, enter the Symbol: "
								currentState = GET_SYMBOL;
								break;
							}
							
						}
						
						//***********************************************************************************************************
						

					case GET_SYMBOL :                   //Case to get Symbol
                     
                	 	//clientMsg = sc.nextLine();	//this is the symbol which client sends 
					    
						outMessege.print(clientMsg);
						outMessege.flush();
						
						if(clientMsg.equals(" ")){
							
							serverMsg = inMessege.readLine(); //"You haven't entered the symbol of the security.Please, enter the symbol:  "
							//System.out.println(serverMsg);
							currentState = GET_SYMBOL;
							
						} else if(clientMsg.equals("quit")){
							
							serverMsg = inMessege.readLine(); //"Server exits. Thank you!"
							//System.out.println(serverMsg);
							outMessege.close();
							inMessege.close();
							socket.close();
							
						}else{
							
						
							// client have sent a reply sth else (neither quit nor " ") 
							serverMsg = inMessege.readLine(); 		//this is the server's reply
							
							if(serverMsg.equals("-1")){
								
								System.out.println("The symbol you entered is invalid. Enter the symbol again: ");
								currentState = GET_SYMBOL;
							
							}else{
								
								serverMsg = inMessege.readLine(); //"The current price of the security (symbol) is (currentCost )"
								//System.out.println(serverMsg);
								
								serverMsg = inMessege.readLine(); //"Please place your bid: "
								//System.out.println(serverMsg);
								currentState = GET_BID_PRICE;
								break;
							}
						}

						
					case GET_BID_PRICE:                 //Case to get bid amount
					
                	   // clientMsg = sc.nextLine();		//this is the client's bid now
					    
						outMessege.print(clientMsg);
						outMessege.flush();
						
						
						if(clientMsg.equals(" ")){ 
							
							serverMsg = inMessege.readLine(); //"You haven't entered the bid.Please, enter the symbol: "
							//System.out.println(serverMsg);
							currentState = GET_BID_PRICE;
							break;
							
						}else if(clientMsg.equals("quit")){ 
							
							
							serverMsg = inMessege.readLine(); //"Server exits. Thank you!"
							//System.out.println(serverMsg);
							
							outMessege.close();
							inMessege.close();
							socket.close();
					 
						
						}else {
							
						// client have sent a reply sth else (neither quit nor " ")
							serverMsg = inMessege.readLine(); 
							
							if (serverMsg.equals("-1")){
							
							serverMsg = inMessege.readLine(); //"Invalid Format for the bid. Please enter in valid format(currency):  "
							//System.out.println(serverMsg);
							currentState = GET_BID_PRICE;
							break;
						
						
							}else {
								
								serverMsg = inMessege.readLine(); //"Bid completed.\nServer exits. Thank you!"
								System.out.println("Bid completed.");
								
								outMessege.close();
								inMessege.close();
								socket.close();
								
							}
						}	
							
					default:
					
						//clientMsg = null;
						outMessege.close();
						inMessege.close();
						socket.close();
             	}
			}
         

		}catch (IOException e) { 
         	System.out.println(e); 
         
     	}catch( NumberFormatException e){
         	System.out.println(e);
         
     	}finally { 	    
         	try{
             	socket.close(); 
         	}catch(Exception e){}
	 	}
	}

}