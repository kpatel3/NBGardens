import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;


public class UI{ //extends JFrame{

	
	private JFrame mainFrame;
	public static String watch = "NULL";
	public static int watch_int = 0;
	private JTable orderTable;
	
	
	public UI(){prepareGUI();}
	
	public static void main(String[] args) {
	UI sD = new UI();
	sD.showEvent();

	}

	private void prepareGUI(){
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(651, 513);
		mainFrame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel ViewOrderList = new JPanel();
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
		

		
		//Capture information from database to list orders
		
		JList<String> ViewOrders = new JList<String>(OrderList.get_Order_id()); //OrderList.Get_Order_ID()
		ViewOrders.setBounds(42, 75, 340, 349);
		ViewOrderList.add(ViewOrders);
		ViewOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ViewOrders.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent event){
						watch = ViewOrders.getSelectedValue();
						if (watch != "NULL"){
							watch_int = Integer.parseInt(watch);
						}
					}
				}
		);
		
		JPanel ViewOrder = new JPanel();
		mainFrame.getContentPane().add(ViewOrder, "name_579464994199815");
		ViewOrder.setLayout(null);
		
		JLabel OrderLabel2 = new JLabel("Order:");
		OrderLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OrderLabel2.setBounds(63, 42, 52, 22);
		ViewOrder.add(OrderLabel2);
		
		JLabel WatchLabel = new JLabel("");
		WatchLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		WatchLabel.setBounds(117, 42, 81, 22);
		
		ViewOrder.add(WatchLabel);
		
		
		//Table settings to display order details

		orderTable = new JTable();
		orderTable.setBounds(42, 75, 341, 349);
		ViewOrder.add(orderTable);
		
		//update the order table for specific order
		updateOrderTable(watch_int);
		
		
		
		JButton BackButton = new JButton("Back");
		BackButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BackButton.setBounds(411, 366, 190, 58);
		ViewOrder.add(BackButton);
		
		ViewOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewOrderList.setVisible(false);
				ViewOrder.setVisible(true);
				if (watch != "NULL"){
					WatchLabel.setText(watch);
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
		
		String[] columnNames= {"Product", "Location", "Status"};
		String[][] info = {
				{"yep", "anywhere", "WAITING"},
				{"cool", "here", "WAITING"},
				
			};
		DefaultTableModel model = new DefaultTableModel(info, columnNames);
		orderTable.setModel(model);
	}
	
	private void showEvent() {
		mainFrame.setVisible(true);
	}
	
	static String[] Get_Database(String task, String table, String field){
		MySQL.accessBD(task,table,field);
		String[] transfer = new String[MySQL.read_Database().size()];
		
		for (int i = 0; i < MySQL.read_Database().size(); i++){
			transfer[i] = MySQL.read_Database().get(i);	
		}
		return transfer;
	}
}
