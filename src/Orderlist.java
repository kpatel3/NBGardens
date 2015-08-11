public class OrderList {

	public static String[] get_Order_id(){
		MySQL.accessBD("READ", "SELECT `order_ID` FROM `order`", "order_ID");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_Status(){
		MySQL.accessBD("READ", "SELECT `status` FROM `order`", "status");
		return MySQL.Transfer_Data();
	}
	
	
	
	
	
	
}
