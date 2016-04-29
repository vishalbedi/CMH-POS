package cmh.cli.pos;

import java.io.Serializable;

public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	static int next= 0;
	int id;
	int categoryId;
	double price;
	String name;
	float defaultTaxPercentage = 0.10f;
	
	Product(){
		// A simple Id Generator 
		// Will be affected by multi threaded code
		// Needs thread safe mechanism
		// We can use UUID class also
		this.id = next++;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return this.price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}
	public void addTax(){
		this.addTax(defaultTaxPercentage);
	}
	
	public void setCategoryId(int id){
		this.categoryId = id;
	}
	
	public void addTax(float taxPercent){
		this.price = price + taxPercent * price;
	}
	
	public void setDefaultTaxPercent(float newPercent){
		this.defaultTaxPercentage = newPercent;
	}
	
}
