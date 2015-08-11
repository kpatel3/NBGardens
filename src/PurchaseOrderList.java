public class PurchaseOrderList {
	
	public static String[] get_PurchaseOrder_id(){
		MySQL.accessBD("READ", "SELECT `PurchaseOrderID` FROM `purchaseorder`", "PurchaseOrderID" );
		return MySQL.Transfer_Data();
	}
}
