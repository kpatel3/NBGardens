import java.util.ArrayList;
import java.util.Random;

public class Order {

	public static ArrayList<OrderPQ> get_Order(){
		ArrayList<Product> Cat = new ArrayList<Product>();
		ArrayList<OrderPQ> opq = new ArrayList<OrderPQ>();

		
		
		Cat.add(new Product(Cat.size()+1, "Happy Gnome", 20));
		Cat.add(new Product(Cat.size()+1,"John Gnome", 42));
		Cat.add(new Product(Cat.size()+1, "Cool Gnome", 32));
		Cat.add(new Product(Cat.size()+1, "Gnome Gnome", 55));
		Cat.add(new Product(Cat.size()+1, "Frodo Gnome", 10));
		Cat.add(new Product(Cat.size()+1, "Justin Gnome", 25));
		Cat.add(new Product(Cat.size()+1, "Price Gnome", 15));
		Cat.add(new Product(Cat.size()+1, "Richie Gnome", 28));
		Cat.add(new Product(Cat.size()+1, "Karl Gnome", 34));
		Cat.add(new Product(Cat.size()+1, "Sam Gnome", 44));
		Cat.add(new Product(Cat.size()+1, "Gondor Gnome", 41));
		Cat.add(new Product(Cat.size()+1, "Adam Gnome", 9));
		Cat.add(new Product(Cat.size()+1, "Lulu Gnome", 7));
		Cat.add(new Product(Cat.size()+1, "Zulu Gnome", 2));
		Cat.add(new Product(Cat.size()+1, "Blue Gnome", 1));
		Cat.add(new Product(Cat.size()+1, "Green Gnome", 11));
		Cat.add(new Product(Cat.size()+1, "Delta Gnome", 8));

		
		for (Product str: Cat){
			opq.add(new OrderPQ(str, randInt(1,10)));
		}
		
//		for (OrderPQ str: opq){
//			str.print_OrderPQ();
//			System.out.println("");
//		}
		return opq;
	}
	
	public static void main(String[] args) {
		
		
		
	}

	public static int randInt(int min, int max){
		Random rn = new Random();
		int randomNum = rn.nextInt(max) + 1;
		return randomNum;
	}
	
}



