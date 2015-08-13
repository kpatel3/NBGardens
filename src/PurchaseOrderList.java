//Functions get the purchase order ids

public class PurchaseOrderList {			//Get purchase order ID
	
	public static String[] get_PurchaseOrder_id(){
		MySQL.accessBD("READ", "SELECT `PurchaseOrderID` FROM `purchaseorder`", "PurchaseOrderID" );
		return MySQL.Transfer_Data();		//return Data from SQL
	}
}
