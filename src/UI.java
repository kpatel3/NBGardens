import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class UI{ // extends JFrame{

	
	private JFrame mainFrame;
	public static String orderStatus = "NULL";
	public static int watch_int = -1;
	private JTable orderTable;
	private JTable ordersTable;
	
	
	public UI(){prepareGUI();}
	
	public static void main(String[] args) {
	UI sD = new UI();
	sD.showEvent();

	}

	private void prepareGUI(){
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(651, 513);
		mainFrame.getContentPane().setLayout(new CardLayout(0, 0));
		
		final JPanel ViewOrderList = new JPanel();
		mainFrame.getContentPane().add(ViewOrderList, "name_579464982833263");
		ViewOrderList.setLayout(null);
		
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
		//Put information about order IDs and Status' in Table
		updateOrdersTable();	
		
		final JPanel ViewOrder = new JPanel();
		mainFrame.getContentPane().add(ViewOrder, "name_579464994199815");
		ViewOrder.setLayout(null);
		
		JLabel OrderLabel2 = new JLabel("Order:");
		OrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OrderLabel2.setBounds(63, 42, 52, 22);
		ViewOrder.add(OrderLabel2);
		
		final JLabel WatchLabel = new JLabel("");
		WatchLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel.setBounds(117, 42, 81, 22);
		
		ViewOrder.add(WatchLabel);
		//update the order table for specific order
		updateOrderTable(watch_int);
		
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
		
		
		//Table settings to display order details

		orderTable = new JTable();
		scrollPaneOrder.setViewportView(orderTable);
		
		ViewOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrderList.setVisible(false);
				ViewOrder.setVisible(true);
				if (watch_int != -1){
					WatchLabel.setText(Integer.toString(watch_int));
					updateOrderTable(watch_int);
				}
			}
		});
	
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
	}
	
	private void updateOrderTable(int order){
		if (order != -1){
			String[] columnNames= {"Product ID", "Name", "Location"};
	
			String[] product_ID = Order.get_Order_ProductID(order);
			String[] product_Name = Order.get_Order_ProductName(order);
			String[] product_Location = Order.get_Order_ProductLocation(order);
	
			String[][] info = new String[MySQL.get_DataSize()][3];
			
			for (int i=0; i < MySQL.get_DataSize(); i++){
				info[i][0] = product_ID[i];
				info[i][1] = product_Name[i];
				info[i][2] = product_Location[i];
			}
			
			DefaultTableModel model = new DefaultTableModel(info, columnNames);
			orderTable.setModel(model);
		}
	}
	
	private void showEvent() {
		mainFrame.setVisible(true);
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
		
		DefaultTableModel models = new DefaultTableModel(info, columnNames);
		
		ordersTable.setModel(models);
	}
}

