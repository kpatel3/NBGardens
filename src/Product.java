//Product class, shows the product ID, Name and location within the warehouse
public class Product {

	private int id, location;
	private String name;
	
	public Product(int newID, String newName, int newLocation){		//input details together
		this.id = newID;
		this.name = newName;
		this.location = newLocation;
	}
	
	public Product(int newID, String newName){						//details for the location are default -1(not known)
		this.id = newID;
		this.name = newName;
		this.location = -1;
	}
	
	public void set_ID(int newID){									//setting or re-setting the ID individually
		this.id = newID;
	}
	
	public void set_Name(String newName){							//setting or re-setting the Name individually
		this.name = newName;
	}
	
	public void set_Location(int newLocation){						//setting or re-setting the Location individually
		this.location = newLocation;
	}
	
	public int get_ID(){											//reclaiming information
		return this.id;
	}
	
	public String get_Name(){										//"" 					""
		return this.name;
	}
	
	public int get_Location(){										//"" 					""
		return this.location;
	}
	
	public void print_Product(){
		print_ID();
		print_Name();
		print_Location();
	}
	
	
	public void print_ID(){
		System.out.println("Product ID: " + id);
	}
	
	public void print_Name(){
		System.out.println("Product Name: " + name);
	}
	
	public void print_Location(){
		if (location != -1)
		{
			System.out.println("Product Location: " + location);
		}
		else
		{
			System.out.println("Product Location: Unknown");
		}
	}
}
