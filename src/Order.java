public class Order {

	public static String[] get_Order_ProductID(int order_ID){
		MySQL.accessBD("Search", "orderlist", "productID", "orderID","","","", order_ID);
		String[] transfer = new String[MySQL.data.size()];
		
		for (int i = 0; i < MySQL.data.size(); i++){
			transfer[i] = MySQL.data.get(i);
		}
		return transfer;
	}
		
	public static String[] get_Order_ProductName(int order_ID){
		MySQL.accessBD("Refine", "orderlist", "productID", "orderID", "product_Name","product_ID", "products", order_ID);
		String[] transfer = new String[MySQL.data2.size()];
		
		for (int i = 0; i < MySQL.data2.size(); i++){
			transfer[i] = MySQL.data2.get(i);
		}
		return transfer;
	}

	public static String[] get_Order_ProductLocation(int order_ID){
		MySQL.accessBD("Refine", "orderlist", "productID", "orderID", "product_Location", "product_ID", "products", order_ID);
		String[] transfer = new String[MySQL.data2.size()];
		
		for (int i = 0; i < MySQL.data2.size(); i++){
			transfer[i] = MySQL.data2.get(i);
		}
		return transfer;
	}
	
}
