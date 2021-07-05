
The overall solution consists of an Auction app for the server-side and a seperate Client app
for the client-side. 

ON THE SERVER SIDE:

1)To compile:
	javac AuctionServer.java Client.java StockDatabase.java TableModel.java ServerGUI.java

2)To run:
	java AuctionServer

3)Basic description:

-The client can connect by using nc as a client(port 2000).


-The procedure:
	
	(I)Asking the client's username(no restrictions on username format).
		In case the username is already in the system database, a caution message is
	displayed to the user informing that the username already exists and to reconnect if 
	it's not her/him.
	
	(II)Asking for the symbol of the security that the user is willing to bid on.
		Once the user inputs a valid symbol,the current highest price will be sent 
	to the user.
	
	(III)Asking for the bid value(will be checked for the correct format).

	(Iv)In case of a successful bidding by a user, the current highest prices and history
	records will be updated accordingly.
	
	(V)The username and symbol can not be changed once entered and if the user wants to
	 keep on bidding she/he will have to quit the server and reconnect.


-The server-side GUI:
	
	(I)Will constantly have on display,the current prices of securities(Symbol,Security
Name,Current Highest Bid in the form of a table). The current prices displayed will be 
refreshed every 500ms.(Only details on FB, VRTU,MSFT, GOOGL, YHOO, XLNX, TSLA and TXN will be 
displayed initially). 
	
	(II)Use the combo-box to either type in or select from the drop-down list, the symbols 
of the securities to,
		a)append to the table showing details on current prices by clicking on
button "append", 
		
		or,
	
		b)see the history records(A table of Customer,Bid,Date and Time, on a seperate
 window) by clicking on Show History button.

	(II)In either of above 2 cases, if the server-side user types in a non-existent symbol,
a dialog box will appear indicating the error.


