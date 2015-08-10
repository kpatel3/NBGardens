
public class Product {

	public static String[] get_Product_ID(){
		return MySQL.Get_Database("READ", "Products", "product_ID");	
	}

	public static String[] get_Product_Name(){
		String[] Sharpie = new String[MySQL.get_DataSize()];
		Sharpie = MySQL.Get_Database("READ", "Products", "product_Name");
		System.out.println(Sharpie[1]);
		return Sharpie;
		//		return MySQL.Get_Database("READ", "Products", "product_Name");
	}
	
	public static String[] get_Product_Location(){
		return MySQL.Get_Database("READ", "Products", "product_Location");
	}
}
