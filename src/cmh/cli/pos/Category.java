package cmh.cli.pos;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable{
	static int next= 0;
	int id;
	String name;
	ArrayList<Product> products;
	Category(){
		// A simple Id Generator 
		// Will be affected by multi threaded code
		// Needs thread safe mechanism
		// We can use UUID class also
		this.id = next++;
		this.products = new ArrayList<Product>();
	}
	
	Category(ArrayList<Product> p){
		this.id = next++;
		this.products = p;
	}
	public int getId(){
		return this.id;
	}
	public boolean addProduct(Product p){
		p.setCategoryId(this.id);
		return products.add(p);
	}
	
	public ArrayList<Product> getProducts(){
		return this.products;
	}
	public void setName(String name){
		this.name = name;
	}
	
}
