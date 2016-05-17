package models;
import java.util.ArrayList;
import java.util.List;

import entities.CategoryProductEntity;
import entities.ProductEntity;

public class ProductShow {
	List<ProductEntityShow> products;

	public ProductShow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductShow(List<ProductEntityShow> products) {
		super();
		this.products = products;
	}

	public List<ProductEntityShow> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntityShow> products) {
		this.products = products;
	}
	
	public void CreateProductShow(List<ProductEntity> products, List<CategoryProductEntity> categories){
		this.products = new ArrayList<ProductEntityShow>();
		for(ProductEntity p : products){
			ProductEntityShow pro = new ProductEntityShow();
			pro.setId(p.getId());
			pro.setName(p.getName());
			pro.setImage(p.getImage());
			pro.setIsNew(p.getIsNew());
			pro.setIsWishList(p.getIsWishlist());
			pro.setIsActive(p.getIsActive());
			pro.setSalePrice(Float.toString(p.getPriceSale()));
			for(CategoryProductEntity c : categories){
				if(p.getCategoryId() == c.getId())
					pro.setCategoryP(c.getName());
			}
			pro.setIdCate(p.getCategoryId());
			this.products.add(pro);
		}
	}
	
}
