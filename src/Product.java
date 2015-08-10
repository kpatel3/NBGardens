public class Product {

	public static String[] get_Product_ID(){
		return MySQL.Get_Database("READ", "Products", "product_ID");	
	}

	public static String[] get_Product_Name(){
		return MySQL.Get_Database("READ", "Products", "product_Name");	
	}
	
	public static String[] get_Product_Location(){
		return MySQL.Get_Database("READ", "Products", "product_Location");
	}
	
	public static String[] get_Product_Stock(){
		return MySQL.Get_Database("READ", "Products", "inv_Quantity");
	}
	
	public static String[] get_Product_Porous(){
		String[] Readable = MySQL.Get_Database("READ", "Products", "porousware");
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
	
	public static void Update_Stock(String addStock, int addQuantity){
		MySQL.accessBD("Add", "products", "inv_Quantity", "product_ID", addStock, "", "", addQuantity);
		
	}
}
