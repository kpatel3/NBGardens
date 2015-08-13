//functions that return the details for all the products in the inventory
public class Product {

	public static String[] get_Product_ID(){	//get all the product Ids
		MySQL.accessBD("READ", "SELECT `product_ID` FROM Products", "product_ID");
		return MySQL.Transfer_Data();//return Data from SQL

	}

	public static String[] get_Product_Name(){	//get all the product names
		MySQL.accessBD("READ", "SELECT `product_Name` FROM Products", "product_Name");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_Product_Location(){	//get all the product locations
		MySQL.accessBD("READ", "SELECT `product_Location` FROM Products", "product_Location");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_Product_Stock(){	//get all the product quantities
		MySQL.accessBD("READ", "SELECT `inv_Quantity` FROM Products", "inv_Quantity");
		return MySQL.Transfer_Data();//return Data from SQL
	}
	
	public static String[] get_Product_Porous(){		//Return whether the item requires porousware (YES or NO)
		MySQL.accessBD("READ", "SELECT `inv_Quantity` FROM Products", "inv_Quantity");
		String[] Readable = MySQL.Transfer_Data();//pull Data from SQL
		for (int i=0; i<MySQL.get_DataSize(); i++){
			if (Integer.parseInt(Readable[i]) == 1){
				Readable [i] = "YES";
			}
			else{
				Readable [i] = "NO";
			}
		}
		return Readable;//return modified data 
	}
	
	public static void Update_Stock(int addStock, int addQuantity){//Add or remove stock 
		MySQL.accessBD("Add", "UPDATE products SET inv_Quantity=inv_Quantity+"+ addQuantity + " WHERE product_ID = " + addStock, "");
	}
}
