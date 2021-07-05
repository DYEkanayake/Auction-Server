import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Random; 

import java.util.*;

public class StockDatabase{

	private String filename;  //csv file name
	public static Map<String,StockDetails> database = new HashMap<String,StockDetails>(); //hashmap to contain the data in csv

	public static ArrayList<String> symbolList=new ArrayList<String>(); //to keep all the symbols
	
	public StockDatabase(String filename){ 
		this.filename=filename;
		fillDatabase();

	}
	
	private void fillDatabase(){  //method to create the data collection for stock details. //IS  'throws IOException' NECESSARY?
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
		        String line;
		        StringBuffer sb = new StringBuffer();
		        String [] data;   //data is the array which keeps data seperately(symbol,security name,initial price)
		        
		        
		        while ((line = br.readLine()) != null) {  //#########IS THE FIRST LINE OF CSV A PROBLEM?
		        	sb.append(line).append("\n");
		            data=line.split(",");  //splitting the data in the array seperating at commas
		            //System.out.println(data[0]+" "+data[1]);
		            double initialPrice = (Math.random()*((100-0)+1))+0; // Randomly generated..double initialPrice = (Math.random()*((max-min)+1))+min; .Range of (0 to 100)
		            
					/*
					data[0]=>symbol
					data[1]=>security name
					*/
					StockDetails stock=new StockDetails(data[0],data[1],initialPrice);
		            database.put(data[0],stock); 
					//System.out.println(database.get(data[0]).getSymbol());
					
					//To initialize bid status. The initial price is taken at the begining
					BidStatus initialStatus=new BidStatus(data[0],data[1],initialPrice);
					Client.bidStatus.put(data[0],initialStatus);
				
					symbolList.add(data[0]);
		        }
		
		} 

		catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}

	}

}

class StockDetails{  //data structure in which the details of each stock item is stored in the hash map.
	
	private String symbol;  //IS symbol NECESSARY?#######################
	private String name;
	private double initialPrice; //The price prior to bidding
	
	
	public StockDetails(String symbol,String name,double initialPrice){
		this.symbol=symbol;
		this.name=name;
		this.initialPrice=initialPrice;

	}
	
	public String getSymbol(){
		return symbol;
	}

	public String getName(){
		return name;
	}
	
	public double getInitialPrice(){
		return initialPrice;
	}
	
	
}





