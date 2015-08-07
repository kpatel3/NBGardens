public class OrderList {

	public static String[] get_Order_id(){
		return UI.Get_Database("READ","`order`","order_ID");
	}
	
	public static String[] get_Status(){
		return UI.Get_Database("READ", "`order`", "status");
	}
	
	
	
	
	
	
}
