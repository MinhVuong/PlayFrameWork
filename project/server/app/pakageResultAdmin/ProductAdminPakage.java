package pakageResultAdmin;

import java.util.List;

import entities.CategoryProductEntity;
import entities.ProductEntity;
import pakageResult.AbstractPakage;

public class ProductAdminPakage extends AbstractPakage{
	private List<ProductEntity> products;
	private List<CategoryProductEntity> categories;
	public ProductAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public ProductAdminPakage(List<ProductEntity> products,
			List<CategoryProductEntity> categories) {
		super();
		this.products = products;
		this.categories = categories;
	}
	public List<ProductEntity> getProducts() {
		return products;
	}
	public void setProducts(List<ProductEntity> products) {
		this.products = products;
	}
	public List<CategoryProductEntity> getCategories() {
		return categories;
	}
	public void setCategories(List<CategoryProductEntity> categories) {
		this.categories = categories;
	}
	
	
}
