import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.CardLayout;


public class UI extends JFrame{

	
	private JFrame mainFrame;				//Creates the JFrame
	
	public static int addStock = 1; 				//Global variables
	public static int addQuantity = 0;
	public static String orderStatus = "NULL";
	public static String watchLabel = " ";
	public static int watch_int = -1;
	public static int watch_int2 = -1;
	
	private JTable orderTable;						//Creates tables used to store the data
	private JTable ordersTable;
	private JTable inventoryTable;
	private JTable purchaseOrderListTable;
	private JTable purchaseOrderTable;
	
	private JTextField invQuantityInput;			//Text box used for the quantity in Adding and Removing items
	
	public UI(){prepareGUI();}						//Constructor
	
	public static void main(String[] args) {		//Main method
	UI sD = new UI();								//Creates an instance of UI
	sD.showEvent();									//Displays the GUI

	}

	private void prepareGUI(){
		//MAINFRAME SETUP
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		mainFrame = new JFrame("Java SWING Examples");						//Creating a mainframe that everything will be attached to
		mainFrame.setSize(651, 513);
		mainFrame.getContentPane().setLayout(new CardLayout(0, 0));
		
		mainFrame.addWindowListener(new WindowAdapter(){					//Ends process on close
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);											//Show the mainframe
		
		
		
		
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		
		//ALL JPANEL SETUPS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JPanel ViewOrderList = new JPanel();
		mainFrame.getContentPane().add(ViewOrderList, "name_579464982833263");
		ViewOrderList.setLayout(null);
		
		JPanel ViewOrder = new JPanel();
		mainFrame.getContentPane().add(ViewOrder, "name_579464994199815");
		ViewOrder.setLayout(null);
		
		JPanel Inventory = new JPanel();
		mainFrame.getContentPane().add(Inventory, "name_11057225177614");
		Inventory.setLayout(null);
		
		JPanel AddStock = new JPanel();
		mainFrame.getContentPane().add(AddStock, "name_18323162741461");
		AddStock.setLayout(null);
		
		JPanel PurchaseOrdersList = new JPanel();
		mainFrame.getContentPane().add(PurchaseOrdersList, "name_95342554495242");
		PurchaseOrdersList.setLayout(null);
		
		JPanel PurchaseOrder = new JPanel();
		mainFrame.getContentPane().add(PurchaseOrder, "name_105395952879572");
		PurchaseOrder.setLayout(null);
		
		//VIEWORDERLIST COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		JButton InventoryButton = new JButton("Inventory");							//Make a button, When clicked it will go to Inventory
		InventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(true);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
				updateInventoryTable();
			}
		});
		
		JButton AddInventoryButton = new JButton("Add/Remove Stock");				//Make a button, When clicked it will go to Add/Remove Stock
		AddInventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(true);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
			}
		});
		
		JButton PurchaseOrderButton = new JButton("Purchase Orders");				//Make a button, When clicked it will go to Purchase Orders
		PurchaseOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(true);
				PurchaseOrder.setVisible(false);
				updatePurchaseOrderListTable();
			}
		});
		
		JButton UpdateStatusButton = new JButton("Update Status");					//Make a button, When clicked it will update the order status
		UpdateStatusButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		UpdateStatusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (orderStatus != "NULL"){
					switch (orderStatus){
					case "WAITING":
						MySQL.Update_Status(watch_int, "'PICKING'");
						break;
					case "PICKING":
						MySQL.Update_Status(watch_int, "'DONE'");
						RemovePurchaseOrdertoProducts(watch_int2);
						break;
					case "DONE":
						System.out.println("Error you cannot update any further");
						break;
					default:
						System.out.println("Error in Update Status... Invalid Input");
					}
				updateOrdersTable();
				}
			}
		});
		//Button Designs
		UpdateStatusButton.setBounds(411, 144, 190, 58);
		ViewOrderList.add(UpdateStatusButton);
		PurchaseOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrderButton.setBounds(411, 228, 190, 58);
		ViewOrderList.add(PurchaseOrderButton);
		AddInventoryButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AddInventoryButton.setBounds(411, 297, 190, 58);
		ViewOrderList.add(AddInventoryButton);
		InventoryButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InventoryButton.setBounds(411, 366, 190, 58);
		ViewOrderList.add(InventoryButton);

	
		
																								// J Table Setup to display list of orders
		JLabel OrderLabel = new JLabel("Orders");												//--------------------------------------------------------------------
		//design
		OrderLabel.setBounds(63, 42, 52, 22);
		ViewOrderList.add(OrderLabel);
		OrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JScrollPane scrollPaneOrders = new JScrollPane();		//Scroll Plane gives a slider on the right
		scrollPaneOrders.setBounds(42, 75, 340, 349);
		ViewOrderList.add(scrollPaneOrders);
		
		ordersTable = new JTable();
		scrollPaneOrders.setViewportView(ordersTable);
		
		ordersTable.addMouseListener(new MouseAdapter() {										//When a row is clicked it will record the id of the order
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = ordersTable.getSelectedRow();
				watch_int = Integer.parseInt((String) ordersTable.getModel().getValueAt(row, 0));
				watch_int2 = watch_int;
				orderStatus= (String) ordersTable.getModel().getValueAt(row,1);
				
				if (orderStatus.equals("DONE")){												//Also hides the update button if the order status is done
					UpdateStatusButton.setVisible(false);
				}
				else{
					UpdateStatusButton.setVisible(true);
				}
			}
		});																						//--------------------------------------------------------------------
		
		//Button created that when clicked it Views the order
		JButton ViewOrderButton = new JButton("View Order");
		ViewOrderButton.setBounds(411, 75, 190, 58);
		ViewOrderList.add(ViewOrderButton);
		ViewOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ViewOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(true);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
				if (watch_int != -1){
					watchLabel = (Integer.toString(watch_int));		//creates a label with the order number
					updateOrderTable(watch_int);					//updates the order table 
				}
			}
		});
		
		updateOrdersTable();	//updates list of orders table	
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		
		//VIEWORDER COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel OrderLabel2 = new JLabel("Order:");											//A label that says "order"
		OrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OrderLabel2.setBounds(63, 42, 52, 22);
		ViewOrder.add(OrderLabel2);
		
		JLabel WatchLabel = new JLabel(" ");												//A label that says the order id
		WatchLabel.setText(watchLabel);
		WatchLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel.setBounds(117, 42, 81, 22);
		ViewOrder.add(WatchLabel);
		
		JButton BackButton = new JButton("Back");											//Creates a Button that goes back to the main page
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
			}
		});
		BackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BackButton.setBounds(411, 366, 190, 58);
		ViewOrder.add(BackButton);
		
		JScrollPane scrollPaneOrder = new JScrollPane();						//Allows for table scrolling
		scrollPaneOrder.setBounds(42, 75, 341, 349);
		ViewOrder.add(scrollPaneOrder);

		orderTable = new JTable();						//Creates a Table for the order
		scrollPaneOrder.setViewportView(orderTable);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		//INVENTORY COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel InventoryLabel = new JLabel("Inventory");								//Creates a Labels that says "Inventory"
		InventoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InventoryLabel.setBounds(63, 42, 124, 22);
		Inventory.add(InventoryLabel);
		
		JScrollPane InventoryScroll = new JScrollPane();								//Allows for table scrolling
		InventoryScroll.setBounds(35, 82, 564, 349);
		Inventory.add(InventoryScroll);
		
		inventoryTable = new JTable();													//New table created for the inventory
		InventoryScroll.setViewportView(inventoryTable);
		
		JButton InventoryBack = new JButton("Back");									//Button that goes to the homepage	
		InventoryBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
			}
		});
		InventoryBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InventoryBack.setBounds(409, 11, 190, 58);
		Inventory.add(InventoryBack);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

		
		//ADDSTOCK COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel invIDLabel = new JLabel("ID");													//Label that says "ID"
		invIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		invIDLabel.setBounds(63, 143, 46, 25);
		AddStock.add(invIDLabel);
		
		JLabel invNameLabel = new JLabel("Name");												//Label that says Name
		invNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		invNameLabel.setBounds(63, 194, 46, 25);
		AddStock.add(invNameLabel);
		
		JComboBox<?> invNameCombo = new JComboBox<Object>(Product.get_Product_Name());			//combo box for the names of products
		invNameCombo.setBounds(206, 196, 150, 25);
		
		JComboBox<?> invIDCombo = new JComboBox<Object>(Product.get_Product_ID());				//combo box for the ids of products
		invIDCombo.setBounds(206, 145, 150, 25);
		
		invNameCombo.addActionListener(new ActionListener() {									//When a selection is made in the combobox it automatically changes to matching
			public void actionPerformed(ActionEvent e) {										//product in the other combobox
				int friendly = invNameCombo.getSelectedIndex();
				invIDCombo.setSelectedIndex(friendly);
				addStock = Integer.parseInt((String) invIDCombo.getSelectedItem());			
			}
		});
		
		AddStock.add(invNameCombo);
		invIDCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int friendly = invIDCombo.getSelectedIndex();
				invNameCombo.setSelectedIndex(friendly);
				addStock = Integer.parseInt((String) invIDCombo.getSelectedItem());	

			}
		});
		AddStock.add(invIDCombo);
		
		
		JLabel invQuantityAddedLabel = new JLabel("Quantity Added/Removed");					
		invQuantityAddedLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		invQuantityAddedLabel.setBounds(63, 250, 197, 32);
		AddStock.add(invQuantityAddedLabel);
		
		
		
		invQuantityInput = new JTextField();													//Text field for the input of the quantity
		invQuantityInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		invQuantityInput.setBounds(270, 254, 86, 25);
		AddStock.add(invQuantityInput);
		invQuantityInput.setColumns(10);
		
		JLabel lblAddInventory = new JLabel("Add Inventory");
		lblAddInventory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAddInventory.setBounds(63, 42, 166, 20);
		AddStock.add(lblAddInventory);
		
		JButton invAddButton = new JButton("Add");									//button clicked to add items from inventory (if valid input, otherwise a popup with error message
		invAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addQuantity = Integer.parseInt(invQuantityInput.getText());
					Product.Update_Stock(addStock, addQuantity);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please Input Valid Number \n (1-1000)", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		invAddButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		invAddButton.setBounds(411, 125, 190, 58);
		AddStock.add(invAddButton);
		
		JButton invBackButton = new JButton("Back");
		invBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(true);
				PurchaseOrder.setVisible(false);
			}
		});
		invBackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		invBackButton.setBounds(411, 366, 190, 58);
		AddStock.add(invBackButton);
		
		JButton invRemoveButton = new JButton("Remove");							//button clicked to remove items from inventory (if valid input, otherwise a popup with error message
		invRemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addQuantity = Integer.parseInt("-"+invQuantityInput.getText());
					Product.Update_Stock(addStock, addQuantity);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please Input Valid Number \n (1-1000)", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		invRemoveButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		invRemoveButton.setBounds(411, 197, 190, 58);
		AddStock.add(invRemoveButton);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		
		
		//PURCHASEORDER COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JScrollPane scrollPanePurchaseOrderList = new JScrollPane();						//Allows for scrolling
		scrollPanePurchaseOrderList.setBounds(42, 75, 340, 349);
		PurchaseOrdersList.add(scrollPanePurchaseOrderList);
		
		purchaseOrderListTable = new JTable();												//JTable to list purchase orders that records value when clicked
		purchaseOrderListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {									
				int row = purchaseOrderListTable.getSelectedRow();
				watch_int2 = Integer.parseInt((String) purchaseOrderListTable.getModel().getValueAt(row, 0));
			}
		});
		scrollPanePurchaseOrderList.setViewportView(purchaseOrderListTable);
		
		JButton PurchaseOrdersBack = new JButton("Back");									//Button that when clicked, it will go back to the main page
		PurchaseOrdersBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(false);
			}
		});
		PurchaseOrdersBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrdersBack.setBounds(414, 372, 190, 58);
		PurchaseOrdersList.add(PurchaseOrdersBack);
		
		JLabel PurchaseOrderLabel = new JLabel("Purchase Orders");							//Label that says "Purchase Orders"
		PurchaseOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrderLabel.setBounds(63, 42, 135, 22);
		PurchaseOrdersList.add(PurchaseOrderLabel);
		
		JButton ViewPurchaseOrder = new JButton("View Order");								//Button, when clicked will display a specific purchase order
		ViewPurchaseOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
				PurchaseOrder.setVisible(true);
				updatePurchaseOrderTable(watch_int2);
			}
		});
		ViewPurchaseOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ViewPurchaseOrder.setBounds(411, 75, 190, 58);
		PurchaseOrdersList.add(ViewPurchaseOrder);
		
		JButton AddPurchaseOrder = new JButton("Add to Stock");								//Button, when clicked it will add the purchase order items to the stock
		AddPurchaseOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (watch_int2 != -1){
					AddPurchaseOrdertoProducts(watch_int2);
				}
				
			}
		});
		AddPurchaseOrder.setFont(new Font("Tahoma", Font.PLAIN, 18));
		AddPurchaseOrder.setBounds(414, 144, 190, 58);
		PurchaseOrdersList.add(AddPurchaseOrder);

		
		JScrollPane scrollPane = new JScrollPane();					//Scrollpane allows for table scrolling
		scrollPane.setBounds(42, 75, 340, 349);
		PurchaseOrder.add(scrollPane);
		
		purchaseOrderTable = new JTable();							//Creates JTable for purchase order details 
		scrollPane.setViewportView(purchaseOrderTable);
		
		JButton PurchaseOrderBack = new JButton("Back");				//Button, When clicked goes back to main page
		PurchaseOrderBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(true);
				PurchaseOrder.setVisible(false);
			}
		});
		PurchaseOrderBack.setFont(new Font("Tahoma", Font.PLAIN, 18));		
		PurchaseOrderBack.setBounds(411, 366, 190, 58);
		PurchaseOrder.add(PurchaseOrderBack);
		
		JLabel PurchaseOrderLabel2 = new JLabel("Purchase Order:");		//Label that says "Purchase orders:"
		PurchaseOrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrderLabel2.setBounds(63, 42, 146, 22);
		PurchaseOrder.add(PurchaseOrderLabel2);
		
		JLabel WatchLabel2 = new JLabel(" ");							//Label that says the current Purchase order
		WatchLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel2.setBounds(194, 42, 81, 22);
		PurchaseOrder.add(WatchLabel2);
		updateInventoryTable();
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		mainFrame.repaint();						//repaints everything to fix a bug in swing
	}
	
	
	
	private void showEvent() {
		mainFrame.setVisible(true);					//shows the mainframe
	}

	
	
	private void updateOrderTable(int order){											// Updates the Order table for given order
		if (order != -1){
			String[] columnNames= {"Product ID", "Name", "Location", "Quantity"};		//column names for the table
	
			String[] product_ID = Order.get_Order_ProductID(order);						//Table Data
			String[] product_Name = Order.get_Order_ProductName(order);
			String[] product_Location = Order.get_Order_ProductLocation(order);
			String[] product_Quantity = Order.get_Order_Product_quantity(order);
			
			String[][] info = new String[MySQL.get_DataSize()][4];						//matrix for model
			
			for (int i=0; i < MySQL.get_DataSize(); i++){								//putting the data into the matrix
				info[i][0] = product_ID[i];
				info[i][1] = product_Name[i];
				info[i][2] = product_Location[i];
				info[i][3] = product_Quantity[i];
			}
			
			
			//Travelling Salesman
			boolean flag = true;
			String temp;
			
			while(flag){																//Bubblesort algorithm to put this in order
				flag = false;
				for(int i=0; i< MySQL.get_DataSize() - 1; i++){
					if (Integer.parseInt(info[i][2])>Integer.parseInt(info[i+1][2])){
						for (int j = 0; j < 4; j++){
							temp = info[i][j];
							info[i][j] = info[i+1][j];
							info[i+1][j] = temp;
						}
						flag = true;
					}
				}
			}
			
			
			DefaultTableModel model = new DefaultTableModel(info, columnNames);		//Putting the data and headers into a model
			orderTable.setModel(model);												//Putting the model into the table
		}
	}
	
	
	
	private void updateOrdersTable(){												//Updates the Order List Table
		String[] columnNames = {"Order ID", "Status"};								//create column names
		
		String[] order_ID = OrderList.get_Order_id();								//get data for the table
		String[] status = OrderList.get_Status();
		
		String[][] info = new String[MySQL.get_DataSize()][2];						//create matrix
		
		for (int i=0; i < MySQL.get_DataSize(); i++){								//put data into the matrix
			info[i][0] = order_ID[i];
			info[i][1] = status[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(info, columnNames);			//create a model with data and column names
		
		ordersTable.setModel(model);												//put the model into the table
	}
	
	
	
	private void updateInventoryTable(){														//Update the Inventory Table
		String[] columnNames = {"Product ID", "Name", "Location", "Stock", "Porousware" };		//Column names
		
		String[] product_ID = Product.get_Product_ID();											//Data for the table
		String[] product_Name = Product.get_Product_Name();
		String[] product_Location = Product.get_Product_Location();
		String[] product_Stock = Product.get_Product_Stock();
		String[] product_Porous = Product.get_Product_Porous();		
		
		String[][] info = new String[MySQL.get_DataSize()][5];									//matrix for the model
		
		for (int i=0; i < MySQL.get_DataSize(); i++){											//Putting the information into the matrix
			info[i][0] = product_ID[i];
			info[i][1] = product_Name[i];
			info[i][2] = product_Location[i];
			info[i][3] = product_Stock[i];
			info[i][4] = product_Porous[i];
		}
			
		
		DefaultTableModel model = new DefaultTableModel(info, columnNames);						//Putting the matrix and column names into the model
		
		inventoryTable.setModel(model);															//Putting the model into the table
	}
	
	
	private void updatePurchaseOrderListTable(){												//Update the list of purchase orders table
		String[] columnNames = {"Purchase Order Number"};										//columns and data
		String[] purchaseOrder = PurchaseOrderList.get_PurchaseOrder_id();						//
		
		String[][] info = new String[MySQL.get_DataSize()][1];
		
		for (int i=0; i < MySQL.get_DataSize(); i++){											//reformat data
			info[i][0] = purchaseOrder[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(info,columnNames);						//put data and column name into model
		purchaseOrderListTable.setModel(model);													//put model into table
	}
	
	
	private void updatePurchaseOrderTable(int order){												//Update the Purchase Order table
		String[] columnNames = {"Product ID", "Name", "Location", "Poruos", "Quantity"};			//Column names
		
		String[] product_ID = PurchaseOrder.get_PurchaseOrder_Product_ID(order);					//data
		String[] product_Name = PurchaseOrder.get_PuchaseOrder_Product_Name(order);
		String[] product_Location = PurchaseOrder.get_PurchaseOrder_Product_Location(order);
		String[] product_Porousware = PurchaseOrder.get_PurchaseOrder_Product_porousware(order);
		String[] product_Quantity = PurchaseOrder.get_PurchaseOrder_Product_Quantity(order);
			
		
		String[][] info = new String[MySQL.get_DataSize()][5];										//Matrix
		
		for (int i=0; i < MySQL.get_DataSize(); i++){												//Put data into matrix
			info[i][0] = product_ID[i];
			info[i][1] = product_Name[i];
			info[i][2] = product_Location[i];
			info[i][3] = product_Porousware[i];
			info[i][4] = product_Quantity[i];
		}

		DefaultTableModel model = new DefaultTableModel(info,columnNames);							//Put matrix and column names into model
		purchaseOrderTable.setModel(model);															//Put model into table
	}
	
	
	private void AddPurchaseOrdertoProducts(int order){															//Add purchase order products to the inventory
		String[] product_ID = PurchaseOrder.get_PurchaseOrder_Product_ID(order);								//Data for product order and quantity
		String[] product_Quantity = PurchaseOrder.get_PurchaseOrder_Product_Quantity(order);
		int all = MySQL.get_DataSize();
		
		for(int i = 0; i<all; i++){
			Product.Update_Stock(Integer.parseInt(product_ID[i]), Integer.parseInt(product_Quantity[i]));		//Update the stock on the database
		}
	}
	
	private void RemovePurchaseOrdertoProducts(int order){														//Remove purchase order products to the inventory
		String[] product_ID = Order.get_Order_ProductID(order);													//Data for product order and quantity
		String[] product_Quantity = Order.get_Order_Product_quantity(order);									
		int all = MySQL.get_DataSize();
		
		for(int i = 0; i<all; i++){
			Product.Update_Stock(Integer.parseInt(product_ID[i]),  Integer.parseInt("-" + product_Quantity[i]));	//Remove that stock from the database
		}
	}
}


