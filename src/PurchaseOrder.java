public class PurchaseOrder {

	public static String[] get_PurchaseOrder_Product_ID(int order_ID){
		MySQL.accessBD("Search", "SELECT PurchaseProduct_ID FROM PurchaseOrderList WHERE PurchaseOrder_ID = " + order_ID,"PurchaseProduct_ID");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_PuchaseOrder_Product_Name(int order_ID){
		MySQL.accessBD("Refine", "SELECT product_Name From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"product_Name");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_PurchaseOrder_Product_Location(int order_ID){
		MySQL.accessBD("Refine", "SELECT product_Location From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"product_Location");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_PurchaseOrder_Product_porousware(int order_ID){
		MySQL.accessBD("Refine", "SELECT porousware From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"porousware");
		String[] Readable = MySQL.Transfer_Data();
		for (int i=0; i<MySQL.get_DataSize(); i++){
			if (Integer.parseInt(Readable[i]) == 1){
				Readable [i] = "YES";
			}
			else{
				Readable [i] = "NO";
			}
		}
		return Readable;
	}
	
	
	public static String[] get_PurchaseOrder_Product_Quantity(int order_ID){
		MySQL.accessBD("Search", "SELECT PurchaseQuantity FROM PurchaseOrderList WHERE PurchaseOrder_ID = " + order_ID,"PurchaseQuantity");
		return MySQL.Transfer_Data();
	}	
}
