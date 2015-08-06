import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;

public class UI{ //extends JFrame{

	private JFrame mainFrame;
	
	public UI(){prepareGUI();}
	
	public static void main(String[] args) {
	UI sD = new UI();
	sD.showEvent();

	}

	private void prepareGUI(){
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(651, 513);
		mainFrame.getContentPane().setLayout(null);
		
		
		JList<String> OrderList = new JList<String>(new String[] {"eennie", "meenie", "minee", "Sislac"});
		OrderList.setBounds(37, 57, 281, 380);
		mainFrame.getContentPane().add(OrderList);
		
		JButton ViewOrderButton = new JButton("View Order");
		ViewOrderButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ViewOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		ViewOrderButton.setBounds(354, 54, 213, 77);
		mainFrame.getContentPane().add(ViewOrderButton);
		
		JLabel OrderLabel = new JLabel("Orders");
		OrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		OrderLabel.setBounds(37, 24, 281, 35);
		mainFrame.getContentPane().add(OrderLabel);
	
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		mainFrame.setVisible(true);
	}
	
	private void showEvent() {
		mainFrame.setVisible(true);
	}
}

