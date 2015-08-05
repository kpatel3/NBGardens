import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class OrdersDB {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	
	public OrdersDB(){prepareGUI();}
	
	public static void main(String[] args) {
	OrdersDB sD = new OrdersDB();	
	sD.showEvents();
	}

	private void prepareGUI(){
		mainFrame = new JFrame("Warehouse Database");
		mainFrame.setSize(415,425);
		mainFrame.setLayout(new GridLayout(7,5));
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}
		});
		
		controlPanel = new JPanel();
		controlPanel.setLayout(null);
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true); 
	}
	
	private void showEvents() {
		headerLabel.setText("Select Order");
		JButton okButton = new JButton("OK");
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		okButton.setActionCommand("OK");
		submitButton.setActionCommand("Submit");
		cancelButton.setActionCommand("Cancel");
		okButton.addActionListener(new BCL());
		submitButton.addActionListener(new BCL());
		cancelButton.addActionListener(new BCL());
		
		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);
		
		Insets insets = controlPanel.getInsets();
		okButton.setBounds(25 + insets.left, 2 + insets.top, 100, 40);
		submitButton.setBounds(150 + insets.left, 2 + insets.top, 100, 40);
		cancelButton.setBounds(275 + insets.left, 2 + insets.top, 100, 40);
		mainFrame.setVisible(true);
	}


	private class BCL implements ActionListener{
		@Override
		public void actionPerformed (ActionEvent ae){
			String command = ae.getActionCommand();
			switch(command){
			case "OK": statusLabel.setText("OK!");
			headerLabel.setText("fsdfj");
			break;
			case "Submit": statusLabel.setText("Submitted");
			headerLabel.setText("  dgfg");
			break;
			case "Cancel": statusLabel.setText("Cancel not possible");
			System.exit(0);
			break;
			}
		}
	}

}




//		for (OrderPQ str: ProductDB.get_Order()){
//			str.print_OrderPQ();
//			System.out.println("");
//		}
