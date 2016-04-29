package cmh.cli.pos;

import java.util.ArrayList;

public class POS {
	ArrayList<Product> products;
	ArrayList<Category> categories;
	ArrayList<Product> cart;
	
	POS(ArrayList<Product> products, ArrayList<Category> categories){
		this.products = products;
		this.categories = categories;
		this.cart = new ArrayList<>();
	}
	
	public ArrayList<Product> byCategory(int categoryId){
		for(Category category : categories){
			if(category.id == categoryId){
				return category.getProducts();
			}
		}
		return null;
	}
	
	public ArrayList<Product> byPriceRange(double min, double max){
		ArrayList<Product> results = new ArrayList<Product>();
		for (Product product : products) {
			if(product.getPrice() >= min && product.getPrice() <= max){
				results.add(product);
			}
		}
		return results;
	}
	
	public ArrayList<Product> byName(String name){
		ArrayList<Product> results = new ArrayList<Product>();
		name = name.toLowerCase();
		for (Product product : products) {
			if(product.getName().toLowerCase().contains(name)){
				results.add(product);
			}
		}
		return results;
	}
	
	public boolean addToCart(int productId){
		for (Product product : products) {
			if(product.getId() == productId){
				return cart.add(product);
			}
		}
		return false;
	}
	
	public boolean buy(){
		if(cart.isEmpty()){
			return false;
		}
		for (Product product : cart) {
			products.remove(product);
		}
		cart.clear();
		return true;
		
	}
}
