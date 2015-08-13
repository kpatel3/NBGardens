//Functions to pull data for the list of order

public class OrderList {

	public static String[] get_Order_id(){	//Pull order ID
		MySQL.accessBD("READ", "SELECT `order_ID` FROM `order`", "order_ID");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_Status(){	//Pull order status
		MySQL.accessBD("READ", "SELECT `status` FROM `order`", "status");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	
	
	
	
	
}
