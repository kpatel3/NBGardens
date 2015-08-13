//funtions that call the information for a specific purchase order
public class PurchaseOrder {

	public static String[] get_PurchaseOrder_Product_ID(int order_ID){	//get the product Ids for this purchase order
		MySQL.accessBD("Search", "SELECT PurchaseProduct_ID FROM PurchaseOrderList WHERE PurchaseOrder_ID = " + order_ID,"PurchaseProduct_ID");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_PuchaseOrder_Product_Name(int order_ID){ //get the product Names for the purchase order
		MySQL.accessBD("Refine", "SELECT product_Name From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"product_Name");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_PurchaseOrder_Product_Location(int order_ID){	//get the product location for the purchase order
		MySQL.accessBD("Refine", "SELECT product_Location From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"product_Location");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_PurchaseOrder_Product_porousware(int order_ID){//Return whether the item requires porousware (YES or NO)
		MySQL.accessBD("Refine", "SELECT porousware From Products INNER JOIN PurchaseOrderList ON PurchaseOrderlist.PurchaseProduct_ID = Products.product_ID WHERE PurchaseOrderList.PurchaseOrder_ID=" + order_ID,"porousware");
		String[] Readable = MySQL.Transfer_Data();//pull Data from SQL
		for (int i=0; i<MySQL.get_DataSize(); i++){
			if (Integer.parseInt(Readable[i]) == 1){
				Readable [i] = "YES";
			}
			else{
				Readable [i] = "NO";
			}
		}
		return Readable;// return readable data 
	}
	
	
	public static String[] get_PurchaseOrder_Product_Quantity(int order_ID){ //get the quantity of the products in the purchase order
		MySQL.accessBD("Search", "SELECT PurchaseQuantity FROM PurchaseOrderList WHERE PurchaseOrder_ID = " + order_ID,"PurchaseQuantity");
		return MySQL.Transfer_Data();//return Data from SQL
	}	
}
