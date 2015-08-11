public class Product {

	public static String[] get_Product_ID(){
		MySQL.accessBD("READ", "SELECT `product_ID` FROM Products", "product_ID");
		return MySQL.Transfer_Data();

	}

	public static String[] get_Product_Name(){
		MySQL.accessBD("READ", "SELECT `product_Name` FROM Products", "product_Name");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_Product_Location(){
		MySQL.accessBD("READ", "SELECT `product_Location` FROM Products", "product_Location");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_Product_Stock(){
		MySQL.accessBD("READ", "SELECT `inv_Quantity` FROM Products", "inv_Quantity");
		return MySQL.Transfer_Data();
	}
	
	public static String[] get_Product_Porous(){
		MySQL.accessBD("READ", "SELECT `inv_Quantity` FROM Products", "inv_Quantity");
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
	
	public static void Update_Stock(int addStock, int addQuantity){
		MySQL.accessBD("Add", "UPDATE products SET inv_Quantity=inv_Quantity+"+ addQuantity + " WHERE product_ID = " + addStock, "");
	}
}
