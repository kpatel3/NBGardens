public class OrderList {

	public static String[] get_Order_id(){
		return MySQL.Get_Database("READ","`order`","order_ID");
	}
	
	public static String[] get_Status(){
		return MySQL.Get_Database("READ", "`order`", "status");
	}
	
	
	
	
	
	
}
