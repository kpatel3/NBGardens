public class Order {

	public static String[] get_Order_ProductID(int order_ID){
		MySQL.accessBD("Search", "SELECT productID FROM orderlist WHERE orderID = " + order_ID,"productID");
		return MySQL.Transfer_Data();
	}
		
	public static String[] get_Order_ProductName(int order_ID){
		MySQL.accessBD("Refine", "SELECT product_Name From Products INNER JOIN Orderlist ON Orderlist.productID = Products.product_ID WHERE Orderlist.orderID=" + order_ID,"product_Name");
		return MySQL.Transfer_Data();
	}

	public static String[] get_Order_ProductLocation(int order_ID){
		MySQL.accessBD("Refine", "SELECT product_Location From Products INNER JOIN Orderlist ON Orderlist.productID = Products.product_ID WHERE Orderlist.orderID=" + order_ID,"product_Location");
		return MySQL.Transfer_Data();
	}

	public static String[] get_Order_Product_quantity(int order_ID){
		MySQL.accessBD("Search", "SELECT quantity FROM orderlist WHERE orderID = " + order_ID,"quantity");
		return MySQL.Transfer_Data();
	}
	
}
