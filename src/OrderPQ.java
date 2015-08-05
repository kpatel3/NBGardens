
public class OrderPQ {

	private Product product;
	private int quantity;

	public OrderPQ(Product newProduct, int newQuantity){
		this.product = newProduct;
		this.quantity = newQuantity;
	}
	
	public OrderPQ(Product newProduct){
		this.product = newProduct;
		this.quantity = 1;
	}
	
	public void set_Product(Product newProduct){
		this.product = newProduct;
	}
	
	public void set_Quantity(int newQuantity){
		this.quantity = newQuantity;
	}
	

	public Product get_Product(){
		return this.product;
	}
	
	public int get_Quantity(){
		return this.quantity;
	}
	
	public void print_OrderPQ(){
		product.print_Product();
		print_Quantity();
	}
	public void print_Quantity(){
		System.out.println("Product Quantity: " + quantity);
	}
	
	public void print_Basic_OrderPQ(){
		product.print_Name();
		print_Quantity();
	}
}
