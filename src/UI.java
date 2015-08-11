import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;


public class UI{ // extends JFrame{

	
	private JFrame mainFrame;
	public static int addStock = 1; 
	public static int addQuantity = 0;
	public static String orderStatus = "NULL";
	public static String watchLabel = " ";
	public static int watch_int = -1;
	public static int watch_int2 = -1;
	private JTable orderTable;
	private JTable ordersTable;
	private JTable inventoryTable;
	private JTextField invQuantityInput;
	private JTable purchaseOrderListTable;
	private JTable purchaseOrderTable;
	
	
	public UI(){prepareGUI();}
	
	public static void main(String[] args) {
	UI sD = new UI();
	sD.showEvent();

	}

	private void prepareGUI(){
		//MAINFRAME SETUP
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(651, 513);
		mainFrame.getContentPane().setLayout(new CardLayout(0, 0));
		
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
		
		
		
		
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
		
		JButton InventoryButton = new JButton("Inventory");
		InventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(true);
				updateInventoryTable();
			}
		});
		
		JButton AddInventoryButton = new JButton("Add/Remove Stock");
		AddInventoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(true);
			}
		});
		
		JButton PurchaseOrderButton = new JButton("Purchase Orders");
		PurchaseOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(false);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(true);
				updatePurchaseOrderListTable();
			}
		});
		
		JButton UpdateStatusButton = new JButton("Update Status");
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

	
		JButton ViewOrderButton = new JButton("View Order");
		ViewOrderButton.setBounds(411, 75, 190, 58);
		ViewOrderList.add(ViewOrderButton);
		ViewOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel OrderLabel = new JLabel("Orders");
		OrderLabel.setBounds(63, 42, 52, 22);
		ViewOrderList.add(OrderLabel);
		OrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JScrollPane scrollPaneOrders = new JScrollPane();
		scrollPaneOrders.setBounds(42, 75, 340, 349);
		ViewOrderList.add(scrollPaneOrders);
		
		ordersTable = new JTable();
		scrollPaneOrders.setViewportView(ordersTable);
		
		ordersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = ordersTable.getSelectedRow();
				watch_int = Integer.parseInt((String) ordersTable.getModel().getValueAt(row, 0));
				orderStatus= (String) ordersTable.getModel().getValueAt(row,1);
				if (orderStatus.equals("DONE")){
					UpdateStatusButton.setVisible(false);
				}
				else{
					UpdateStatusButton.setVisible(true);
				}
			}
		});
		
		updateOrdersTable();	
		updateOrderTable(watch_int);
		
		ViewOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrderList.setVisible(false);
				ViewOrder.setVisible(true);
				if (watch_int != -1){
					watchLabel = (Integer.toString(watch_int));
					updateOrderTable(watch_int);
				}
			}
		});
			
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------		
		
		
		//VIEWORDER COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel OrderLabel2 = new JLabel("Order:");
		OrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OrderLabel2.setBounds(63, 42, 52, 22);
		ViewOrder.add(OrderLabel2);
		
		JLabel WatchLabel = new JLabel(" ");
		WatchLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel.setBounds(117, 42, 81, 22);
		ViewOrder.add(WatchLabel);
		
		JButton BackButton = new JButton("Back");
		BackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
			}
		});
		BackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BackButton.setBounds(411, 366, 190, 58);
		ViewOrder.add(BackButton);
		
		JScrollPane scrollPaneOrder = new JScrollPane();
		scrollPaneOrder.setBounds(42, 75, 341, 349);
		ViewOrder.add(scrollPaneOrder);

		orderTable = new JTable();
		scrollPaneOrder.setViewportView(orderTable);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		//INVENTORY COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel InventoryLabel = new JLabel("Inventory");
		InventoryLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InventoryLabel.setBounds(63, 42, 124, 22);
		Inventory.add(InventoryLabel);
		
		JScrollPane InventoryScroll = new JScrollPane();
		InventoryScroll.setBounds(35, 82, 564, 349);
		Inventory.add(InventoryScroll);
		
		inventoryTable = new JTable();
		InventoryScroll.setViewportView(inventoryTable);
		
		JButton InventoryBack = new JButton("Back");
		InventoryBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
			}
		});
		InventoryBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		InventoryBack.setBounds(409, 11, 190, 58);
		Inventory.add(InventoryBack);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

		
		//ADDSTOCK COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		JLabel invIDLabel = new JLabel("ID");
		invIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		invIDLabel.setBounds(63, 143, 46, 25);
		AddStock.add(invIDLabel);
		
		JLabel invNameLabel = new JLabel("Name");
		invNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		invNameLabel.setBounds(63, 194, 46, 25);
		AddStock.add(invNameLabel);
		
		JComboBox<?> invNameCombo = new JComboBox<Object>(Product.get_Product_Name());
		invNameCombo.setBounds(206, 196, 150, 25);
		
		JComboBox<?> invIDCombo = new JComboBox<Object>(Product.get_Product_ID());
		invIDCombo.setBounds(206, 145, 150, 25);
		
		invNameCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		
		
		invQuantityInput = new JTextField();
		invQuantityInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		invQuantityInput.setBounds(270, 254, 86, 25);
		AddStock.add(invQuantityInput);
		invQuantityInput.setColumns(10);
		
		JLabel lblAddInventory = new JLabel("Add Inventory");
		lblAddInventory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAddInventory.setBounds(63, 42, 166, 20);
		AddStock.add(lblAddInventory);
		
		JButton invAddButton = new JButton("Add");
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
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
			}
		});
		invBackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		invBackButton.setBounds(411, 366, 190, 58);
		AddStock.add(invBackButton);
		
		JButton invRemoveButton = new JButton("Remove");
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
		
		JScrollPane scrollPanePurchaseOrderList = new JScrollPane();
		scrollPanePurchaseOrderList.setBounds(42, 75, 340, 349);
		PurchaseOrdersList.add(scrollPanePurchaseOrderList);
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		
		
		//PURCHASEORDER COMPONENTS
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		purchaseOrderListTable = new JTable();
		purchaseOrderListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = purchaseOrderListTable.getSelectedRow();
				watch_int2 = Integer.parseInt((String) purchaseOrderListTable.getModel().getValueAt(row, 0));
			}
		});
		scrollPanePurchaseOrderList.setViewportView(purchaseOrderListTable);
		
		JButton PurchaseOrdersBack = new JButton("Back");
		PurchaseOrdersBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewOrder.setVisible(false);
				ViewOrderList.setVisible(true);
				Inventory.setVisible(false);
				AddStock.setVisible(false);
				PurchaseOrdersList.setVisible(false);
			}
		});
		PurchaseOrdersBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrdersBack.setBounds(414, 372, 190, 58);
		PurchaseOrdersList.add(PurchaseOrdersBack);
		
		JLabel PurchaseOrderLabel = new JLabel("Purchase Orders");
		PurchaseOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrderLabel.setBounds(63, 42, 135, 22);
		PurchaseOrdersList.add(PurchaseOrderLabel);
		
		JButton ViewPurchaseOrder = new JButton("View Order");
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
		
		JButton AddPurchaseOrder = new JButton("Add to Stock");
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

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 75, 340, 349);
		PurchaseOrder.add(scrollPane);
		
		purchaseOrderTable = new JTable();
		scrollPane.setViewportView(purchaseOrderTable);
		
		JButton PurchaseOrderBack = new JButton("Back");
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
		
		JLabel PurchaseOrderLabel2 = new JLabel("Purchase Order:");
		PurchaseOrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PurchaseOrderLabel2.setBounds(63, 42, 146, 22);
		PurchaseOrder.add(PurchaseOrderLabel2);
		
		JLabel WatchLabel2 = new JLabel(" ");
		WatchLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel2.setBounds(194, 42, 81, 22);
		PurchaseOrder.add(WatchLabel2);
		updateInventoryTable();
		//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		mainFrame.repaint();
	}
	
	
	
	private void showEvent() {
		mainFrame.setVisible(true);
	}

	
	
	private void updateOrderTable(int order){
		if (order != -1){
			String[] columnNames= {"Product ID", "Name", "Location", "Quantity"};
	
			String[] product_ID = Order.get_Order_ProductID(order);
			String[] product_Name = Order.get_Order_ProductName(order);
			String[] product_Location = Order.get_Order_ProductLocation(order);
			String[] product_Quantity = Order.get_Order_Product_quantity(order);
			
			String[][] info = new String[MySQL.get_DataSize()][4];
			
			for (int i=0; i < MySQL.get_DataSize(); i++){
				info[i][0] = product_ID[i];
				info[i][1] = product_Name[i];
				info[i][2] = product_Location[i];
				info[i][3] = product_Quantity[i];
			}
			
			
			//Travelling Salesman
			boolean flag = true;
			String temp;
			
			while(flag){
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
			
			
			DefaultTableModel model = new DefaultTableModel(info, columnNames);
			orderTable.setModel(model);
		}
	}
	
	
	
	private void updateOrdersTable(){
		String[] columnNames = {"Order ID", "Status"};
		
		String[] order_ID = OrderList.get_Order_id();
		String[] status = OrderList.get_Status();
		
		String[][] info = new String[MySQL.get_DataSize()][2];
		
		for (int i=0; i < MySQL.get_DataSize(); i++){
			info[i][0] = order_ID[i];
			info[i][1] = status[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(info, columnNames);
		
		ordersTable.setModel(model);
	}
	
	
	
	private void updateInventoryTable(){
		String[] columnNames = {"Product ID", "Name", "Location", "Stock", "Porousware" };
		
		String[] product_ID = Product.get_Product_ID();
		String[] product_Name = Product.get_Product_Name();
		String[] product_Location = Product.get_Product_Location();
		String[] product_Stock = Product.get_Product_Stock();
		String[] product_Porous = Product.get_Product_Porous();		
		
		String[][] info = new String[MySQL.get_DataSize()][5];
		
		for (int i=0; i < MySQL.get_DataSize(); i++){
			info[i][0] = product_ID[i];
			info[i][1] = product_Name[i];
			info[i][2] = product_Location[i];
			info[i][3] = product_Stock[i];
			info[i][4] = product_Porous[i];
		}
			
		
		DefaultTableModel model = new DefaultTableModel(info, columnNames);
		
		inventoryTable.setModel(model);
	}
	
	private void updatePurchaseOrderListTable(){
		String[] columnNames = {"Purchase Order Number"};
		String[] purchaseOrder = PurchaseOrderList.get_PurchaseOrder_id();
		
		String[][] info = new String[MySQL.get_DataSize()][1];
		
		for (int i=0; i < MySQL.get_DataSize(); i++){
			info[i][0] = purchaseOrder[i];
		}
		
		DefaultTableModel model = new DefaultTableModel(info,columnNames);
		purchaseOrderListTable.setModel(model);
	}
	
	private void updatePurchaseOrderTable(int order){
		String[] columnNames = {"Product ID", "Name", "Location", "Poruos", "Quantity"};
		
		String[] product_ID = PurchaseOrder.get_PurchaseOrder_Product_ID(order);
		String[] product_Name = PurchaseOrder.get_PuchaseOrder_Product_Name(order);
		String[] product_Location = PurchaseOrder.get_PurchaseOrder_Product_Location(order);
		String[] product_Porousware = PurchaseOrder.get_PurchaseOrder_Product_porousware(order);
		String[] product_Quantity = PurchaseOrder.get_PurchaseOrder_Product_Quantity(order);
			
		
		String[][] info = new String[MySQL.get_DataSize()][5];
		
		for (int i=0; i < MySQL.get_DataSize(); i++){
			info[i][0] = product_ID[i];
			info[i][1] = product_Name[i];
			info[i][2] = product_Location[i];
			info[i][3] = product_Porousware[i];
			info[i][4] = product_Quantity[i];
		}

		DefaultTableModel model = new DefaultTableModel(info,columnNames);
		purchaseOrderTable.setModel(model);
	}
	
	private void AddPurchaseOrdertoProducts(int order){
		String[] product_ID = PurchaseOrder.get_PurchaseOrder_Product_ID(order);
		String[] product_Quantity = PurchaseOrder.get_PurchaseOrder_Product_Quantity(order);
		
		for(int i = 0; i<MySQL.get_DataSize(); i++){
			Product.Update_Stock(Integer.parseInt(product_ID[i]), Integer.parseInt(product_Quantity[i]));
		}
	}
}

