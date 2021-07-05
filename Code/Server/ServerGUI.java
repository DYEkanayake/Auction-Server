import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.text.*;
//import java.util.timer;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.Timer; //for timer

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerGUI extends JPanel implements ActionListener{

	
	private TableModel statusTable; //curent prices of stocks. JPanel 1(NORTH)
	Timer timer;
	
	//private TableModel historyTable; //history of the stock. 

	private JPanel centerPanel;
	private JPanel bottomPanel;

//In center panel
	private JLabel center; //says what to do to append
	private JButton appendRow;  //append a row (security) to bid status table.
	private JComboBox<String> newEntry;

	private JButton showHistory; //shows the hostory of selected stock 

	public ServerGUI(){
		//super(new BorderLayout());
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(600,600));
		
		timer=new Timer(500,this);
		timer.start();

		
		TableModel.keys=new ArrayList<String>();
		
		TableModel.keys.add("FB");
		TableModel.keys.add("VRTU");
		TableModel.keys.add("MSFT");
		TableModel.keys.add("GOOGL");
		TableModel.keys.add("YHOO");
		TableModel.keys.add("XLNX");
		TableModel.keys.add("TSLA");
		TableModel.keys.add("TXN");
		
		
		statusTable=new TableModel("bidStatus");
		this.add(statusTable, BorderLayout.NORTH);

	//center panel
		centerPanel=new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(600,50));
		
		//center label
		center=new JLabel("Select the symbol of the security you want to append to this.");
		center.setFont(new Font("Serif", Font.BOLD, 12));
		centerPanel.add(center, BorderLayout.NORTH);
		
		//combo box and buttons
		
		String [] symbols = new String[StockDatabase.symbolList.size()];              
		for(int j =0;j<StockDatabase.symbolList.size();++j){
		  symbols[j] = StockDatabase.symbolList.get(j);
		}
		newEntry=new JComboBox<String>(symbols);
		newEntry.setEditable(true);
		
		appendRow=new JButton("append");  
		appendRow.addActionListener(this);
		appendRow.setPreferredSize(new Dimension(100,50));

		
		showHistory=new JButton("Show History");  
		showHistory.addActionListener(this);
		showHistory.setPreferredSize(new Dimension(150,50));

		
		//centerPanel.add(newEntry,BorderLayout.CENTER);
		//centerPanel.add(appendRow,BorderLayout.WEST);
		centerPanel.add(newEntry,BorderLayout.WEST);
		centerPanel.add(appendRow,BorderLayout.EAST);
		centerPanel.add(showHistory);
		

		this.add(centerPanel,BorderLayout.CENTER);
		
	//bottom panel
		bottomPanel=new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setPreferredSize(new Dimension(200,200));
		this.add(bottomPanel,BorderLayout.SOUTH);
		
	}

    private boolean isMyEvent(ActionEvent e) { 
	return e.getSource() == this.timer;
    }

    public void actionPerformed(ActionEvent e) {
	
		if(isMyEvent(e)) { 
			statusTable.setVisible(false); //to rid the previous values a new table is made
			statusTable=new TableModel("bidStatus");
			
			this.add(statusTable, BorderLayout.NORTH);
			
		}
		
		if(e.getSource()==this.appendRow){
			//if not correct give error message dialog box
		    if(StockDatabase.database.containsKey(newEntry.getSelectedItem().toString())){
				TableModel.keys.add(newEntry.getSelectedItem().toString());
			
				statusTable.setVisible(false); //to rid the previous values a new table is made
				statusTable=new TableModel("bidStatus");
				
				this.add(statusTable, BorderLayout.NORTH);
			}

			else{
				JOptionPane.showMessageDialog(null, "Symbol you entered is not available in the database");
			}
		}
		
		
		if(e.getSource()==this.showHistory){
			//if not correct give error message dialog box
			
		    if(StockDatabase.database.containsKey(newEntry.getSelectedItem().toString())){
				
				JFrame history = new JFrame("Bid History");
				history .setSize(600, 600);
				history.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				//Create and set up the content pane.
				TableModel.historyKey=newEntry.getSelectedItem().toString();
				
				JComponent newcontentPane = new TableModel("bidHistory");
				newcontentPane.setOpaque(true); //content panes must be opaque
				history.setContentPane(newcontentPane);

				//Display the window.
				history.pack();
				history.setVisible(true);
			}

			else{
				JOptionPane.showMessageDialog(null, "Symbol you entered is not available in the database");
			}
		}
		
		
		

	}
	
}

