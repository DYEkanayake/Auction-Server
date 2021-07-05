import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.io.*;
import java.util.*;

import java.awt.*; //IMPORT ONLY NECESSAR PACKAGES

import javax.swing.JLabel;
import java.text.*;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.BorderLayout;

public class TableModel extends JPanel{

	private DefaultTableModel tableModel;
	private JTable table;
	private String tableType;

	private JLabel header;
	
	public static ArrayList<String> keys; //key array for the securities to be displayed on bid status table

	public static String historyKey;
	

	public TableModel(String tableType){
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200,200));

		this.tableModel=new DefaultTableModel();
		this.table=new JTable(tableModel);
		this.tableType=tableType;
		selectType(tableType);
		
	}
	
	private void selectType(String tableType){
		
		if(tableType.equals("bidStatus")){
			bidStatusType();
			header=new JLabel("Current prices of securities: ");
			header.setFont(new Font("Serif", Font.BOLD, 14));
			this.add(header,BorderLayout.NORTH);
		}

		else if(tableType.equals("bidHistory")){
			bidHistoryType();
			header=new JLabel("Bid History of the security: ");
			header.setFont(new Font("Serif", Font.BOLD, 14));
			this.add(header,BorderLayout.NORTH);
			
		}

		else if(tableType.equals("stockDetails")){
			
			initialStockDetails();
			header=new JLabel("Initial Details of stocks: ");
			header.setFont(new Font("Serif", Font.BOLD, 14));
			this.add(header,BorderLayout.NORTH);
		}

	}
	
	private void bidStatusType(){ //table model for bid status display.
		tableModel.addColumn("Symbol");
		tableModel.addColumn("Security Name");
		tableModel.addColumn("Current Highest bid");

		//add data then add to JPanel after adding to scrollpane
		
		//String [] keys={"FB","VRTU","MSFT","GOOGL","YHOO","XLNX","TSLA","TXN"};
		
		BidStatus st;
		String symbol;
		String name;
		String currentPrice;
		// BidStatus(String symbol,double highestBid)
		//for(int k=0;k<keys.length;++k){
		for(int k=0;k<keys.size();++k){
			//st=Client.bidStatus.get(keys[k]);
			st=Client.bidStatus.get(keys.get(k));
			symbol=st.getSymbol();
			//System.out.println(symbol);
			name=st.getName();
			//System.out.println(name);
		//WILL BE UPDATED EVERY 500ms
			currentPrice=Double.toString(st.getHighestBid());
			//System.out.println(currentPrice);

		//add the row
			tableModel.addRow(new Object[] {symbol,name,currentPrice});
		}
		
		//add the table to JPanel after adding to scrollpane
		 this.add(new JScrollPane(table));
		//this.add(new JScrollPane(table),BorderLayout.CENTER);
	}
	

	
	private void bidHistoryType(){ //table model for bid history display.
		//tableModel.addColumn("Symbol");  //DO WE NEED THESE?#####
		//tableModel.addColumn("Security Name");
		tableModel.addColumn("Customer");
		tableModel.addColumn("Bid");
		//tableModel.addColumn("No.of shares"); #########
		//tableModel.addColumn("Date and time");  #########
		
		//add data then add to JPanel after adding to scrollpane
		//bidHistory = new HashMap<String,ArrayList<BidHistory>>();
		
		//String symbol;
		//String name;
		String customer;
		String bid;
		//String shares;
		//String time;
		ArrayList<BidHistory> recordList;
		BidHistory history;
		if(Client.bidHistory.containsKey(historyKey)){
			recordList=Client.bidHistory.get(historyKey);
			for(int k=0;k<recordList.size();++k){
				history=recordList.get(k);
				customer=history.getClientName();
				bid=Double.toString(history.getBid());
				
			//add the row
				tableModel.addRow(new Object[] {customer,bid});
			}

			//add the table to JPanel after adding to scrollpane
			 this.add(new JScrollPane(table));
		}
		else{
			customer="none";
			bid="nil";
			tableModel.addRow(new Object[] {customer,bid});
			this.add(new JScrollPane(table));
		}

	}
	//addRow method on button click


	private void initialStockDetails(){ //table model for stock initial details display.
		
		tableModel.addColumn("Symbol");
		tableModel.addColumn("Security Name");
		tableModel.addColumn("Initial Price");

	//add data then add to JPanel after adding to scrollpane
		// database = new HashMap<String,StockDetails>();
		// METHOD 2(THE PROBLEM IS KEYS WON'T APPEAR IN THE ORDER ASIN CSV FILE)

		String symbol;
		String name;
		String initialPrice;
		StockDetails st;
		int size=StockDatabase.database.size();
		Iterator i=StockDatabase.database.entrySet().iterator();
		
		//while(i.hasNext()){
		for(int k=0;k<size;++k){	
			Map.Entry element=(Map.Entry)i.next();
			st=(StockDetails)element.getValue();
			symbol=st.getSymbol();
			name=st.getName();
			initialPrice=Double.toString(st.getInitialPrice());
			//add the row
			tableModel.addRow(new Object[] {symbol,name,initialPrice});
		}	
		
		//add the table to JPanel after adding to scrollpane
		 this.add(new JScrollPane(table));
		


		/* METHOD 1
		// Iterator to traverse the list
	
		Set keys=StockDatabase.database.keySet();
        Iterator<String> i = keys.iterator();

		
		String symbol;
		String name;
		String initialPrice;
		
		StockDetails st;
		//Collection stocks=StockDatabase.database.values();
		//Iterator i = stocks.iterator();

		
 		while(i.hasNext()){
			
			symbol=i.next();
			System.out.println(symbol);
			st=StockDatabase.database.get(symbol);
			
			//symbol=st.getSymbol();
			
			name=st.getName();
			//System. out. println(name);
			initialPrice=Double.toString(st.getInitialPrice());
			//System. out. println(initialPrice);
			//tableModel.addRow(new Object[] {symbol,name,initialPrice});
			//sym.next();
		}
		*/
		
		
	}

	

}