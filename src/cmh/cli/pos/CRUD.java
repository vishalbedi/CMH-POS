package cmh.cli.pos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * CRUD class
 * desc: This class handles the CRUD operations for the system.
 * It stores data into files for persistent storage. 
 * 
 */
public class CRUD {
	
	private ArrayList<Category> categories;
	private ArrayList<Product> products;
	private String dir = System.getProperty("user.dir") + "\\src\\cmh\\cli\\pos\\";
	private String productsFile = dir+"products.ser";
	private String categoriesFile = dir+ "categories.ser";
	
	CRUD(ArrayList<Category> categories,ArrayList<Product> products){
		this.categories = categories;
		this.products = products;
		ArrayList<Category> cat = null;
		ArrayList<Product> pro = null;
		cat = load(categoriesFile, cat);
		pro = load(productsFile, pro);
		if(null != cat){
			//Hack to make serial Indexing work
			Category.next = cat.get(cat.size() -1).getId()+1;
			this.categories.addAll(cat);
		}
		if(null != pro){
			//Hack to make serial indexing work
			Product.next = pro.get(pro.size() -1).getId()+1;
			this.products.addAll(pro);
		}
	}
	
	public boolean createCategory(String name){
		Category cat1 = new Category();
		cat1.setName(name);
		categories.add(cat1);
		return save(categoriesFile, categories);
	}

	public boolean createProduct(String name, double price, int catId){
		Product pr = new Product();
		pr.setName(name);
		pr.setPrice(price);
		Category c = getCategory(catId);
		if(null != c){
			products.add(pr);
			c.addProduct(pr);
			return save(categoriesFile, categories) && save(productsFile, products);
		}
		return false;
	}

	private Category getCategory(int id){
		for (Category category : categories) {
			if(category.id == id)
				return category;
		}
		return null;
	}

	private boolean save(String filename, ArrayList list){
		try{
			FileOutputStream fos= new FileOutputStream(filename);
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.close();
			fos.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

	private ArrayList load(String filename, ArrayList list){
		if(!(new File(filename).isFile())){
			System.out.println("File not created Yet Maybe First time Launch");
			return null;
		}
		try
		{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			list = (ArrayList) ois.readObject();
			ois.close();
			fis.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
			return null;
		}catch(ClassNotFoundException c){
			System.out.println("Class not found");
			c.printStackTrace();
			return null;
		}
		return list;
	}
}
