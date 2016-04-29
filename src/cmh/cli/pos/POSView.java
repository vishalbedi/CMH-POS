package cmh.cli.pos;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * POSView Class 
 * 
 * desc: This class is the view of the application 
 * All the code related to IO is handled in this class
 * 
 */
public class POSView {
	private int MODE;
	private static final int ADMIN = 0;
	private static final int CONSUMER = 1;
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Category> categories = new ArrayList<>();
	private Scanner sc = new Scanner(System.in);
	private CRUD crud;
	private POS pos;
	public POSView(int mode) {
		this.MODE = mode;
		crud = new CRUD(categories, products);
		pos = new POS(products, categories);
	}

	private void createCategory(){
		System.out.println("::Create Category::");
		System.out.println("Category Name ?");
		String name = sc.nextLine();
		if(crud.createCategory(name))
			System.out.println("Category Created Sucessfully");
	}

	private void createProduct(){
		System.out.println("::Create Product::");
		System.out.println("Product Name?");
		String name = sc.nextLine();
		System.out.println("Price");
		double price = sc.nextDouble();
		System.out.println("::Choose a Category ::");
		displayCategories(categories);
		System.out.println("Enter Category Id ?");
		int catId = 0;
		if(sc.hasNextInt())
			catId = sc.nextInt(10);
		else{
			sc.nextLine();
			System.out.println("Error!! That is not a valid choice");
		}
		if(crud.createProduct(name, price, catId))
			System.out.println("Product created Sucessfully");
	}

	private void displayProducts(ArrayList<Product> pr){
		System.out.format("%10s%40s%10s%n","Id", "Name", "Price");
		System.out.format("%10s%40s%10s%n","-----", "------", "------");
		for (Product product : pr) {
			System.out.format("%10d%40s%10s%n",product.getId(), product.getName(), product.getPrice());
		}
	}

	private void displayCategories(ArrayList<Category> categories){
		System.out.format("%10s%32s%n", "Id","Name");
		System.out.format("%10s%32s%n", "------","------");
		for (Category category : categories) {
			System.out.format("%10d%32s%n", category.id,category.name);	
		}
	}
	public void init(){
		if(MODE == ADMIN){
			this.adminMode();
		}else if (MODE == CONSUMER)
			this.consumerMode();
		else
			System.out.println("Unrecognized Mode...");
	}

	private void adminMode(){
		int cmd = -100;
		System.out.println("::ADMIN MODE::");
		final int EXIT = 0;
		final int CREATE_CATEGORY = 1;
		final int CREATE_PRODUCT = 2;
		final int VIEW_CATEGORY = 3;
		final int VIEW_PRODUCT = 4;
		while(cmd != EXIT){
			System.out.println("0. EXIT");
			System.out.println("1. Create Category");
			System.out.println("2. Create Product");
			System.out.println("3. View Categories");
			System.out.println("4. View Products");
			if(sc.hasNextInt()){
				cmd = sc.nextInt(10);
				sc.nextLine();
			}
			else{
				sc.nextLine();
				continue;
			}
			switch (cmd) {
			case EXIT:
				System.out.println("Closing application");
				return;
			case CREATE_CATEGORY: 
				this.createCategory();
				break;
			case CREATE_PRODUCT:
				this.createProduct();
				break;
			case VIEW_CATEGORY:
				this.displayCategories(categories);
				break;
			case VIEW_PRODUCT: 
				this.displayProducts(products);
				break;
			default:
				System.out.println("Wrong command Entered. Try again");
				break;
			}

		}
	}

	private void consumerMode(){
		this.displayProducts(products);
		int cmd = -100;
		final int EXIT = 0;
		final int VIEW_PRODUCTS = 1;
		final int ADD_TO_CART = 2;
		final int SEARCH_BY = 3;
		final int VIEW_CART = 4;
		while(cmd != EXIT){
			System.out.println("0. EXIT");
			System.out.println("1. View Products");
			System.out.println("2. Add To Cart");
			System.out.println("3. Search By");
			System.out.println("4. View Cart");
			if(sc.hasNextInt()){
				cmd = sc.nextInt(); 
				sc.nextLine();
			}else{
				sc.nextLine();
				continue;
			}
			switch (cmd) {
			case EXIT:
				System.out.println("Closing Application... ");
				break;
			case VIEW_PRODUCTS:
				System.out.println("::Products::");
				this.displayProducts(products);
				break;
			case ADD_TO_CART:
				System.out.println("Enter the Id you want to add to cart");
				int id = 0;
				if(sc.hasNextInt()){
					id = sc.nextInt(); sc.nextLine();
					sc.nextLine();
				}else{
					sc.nextLine();
					continue;
				}
				if(pos.addToCart(id))
					System.out.println("Added Sucessfully");
				break;
			case SEARCH_BY:
				int NAME = 0;
				int CATEGORY = 1;
				int PRICE_RANGE = 2;
				System.out.println("0. NAME");
				System.out.println("1. Category");
				System.out.println("2. Price Range");
				int ans = 0;
				if(sc.hasNextInt()){
					// nextInt takes in the int but leaves /n in buffer
					ans = sc.nextInt(); 
					sc.nextLine();
				}else{
					sc.nextLine();
					continue;
				}
				if(ans == NAME){
					System.out.println("Enter Product name");
					String name = sc.nextLine();
					this.displayProducts(pos.byName(name));
				}else if( ans == CATEGORY){
					this.displayCategories(categories);
					System.out.println("Select any Category..");
					System.out.println("Enter Category Id");
					int catId = 0;
					if(sc.hasNextInt())
					{catId = sc.nextInt(); sc.nextLine();}
					else{
						sc.nextLine();
						continue;
					}
					this.displayProducts(pos.byCategory(catId));
				}else if(ans == PRICE_RANGE){
					System.out.println("Enter MIN and MAX Price range");
					System.out.println("MIN?");
					int min = 0;
					if(sc.hasNextInt())
					{min = sc.nextInt(); sc.nextLine();}
					else{
						sc.nextLine();
						continue;
					}
					System.out.println("MAX?");
					int max = 0;
					if(sc.hasNextInt())
					{max = sc.nextInt(); sc.nextLine();}
					else{
						sc.nextLine();
						continue;
					}
					this.displayProducts(pos.byPriceRange(min, max));
				}
				break;
			case VIEW_CART:
				final int BUY = 1;
				this.displayProducts(pos.cart);
				System.out.println("Do you want to buy?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				int choice =0;
				if(sc.hasNextInt()){
					choice = sc.nextInt(); 
					sc.nextLine();
				}
				else{
					sc.nextLine();
					continue;
				}
				if(choice == BUY){
					if(pos.buy())
						System.out.println("Order placed Sucessfully");
				}
				break;
			default:
				System.out.println("Wrong command Entered. Try again");
				break;
			}
		}
	}
	// TO Run the program pass mode as a Command Line argument. 
	public static void main(String args[]){
		int mode = Integer.parseInt(args[0]);
		POSView view = new POSView(mode);
		view.init();

	}
}
