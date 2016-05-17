package models;

import java.util.ArrayList;
import java.util.List;

import entities.CategoryEntity;
import entities.CategoryProductEntity;

public class CategoryProductShow {
	List<CategoryProduct> list;

	public CategoryProductShow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryProductShow(List<CategoryProduct> list) {
		super();
		this.list = list;
	}

	public List<CategoryProduct> getList() {
		return list;
	}

	public void setList(List<CategoryProduct> list) {
		this.list = list;
	}
	
	public void CreateCategoryProductShow(List<CategoryProductEntity> cps, List<CategoryEntity> cs){
		list = new ArrayList<CategoryProduct>();
		for(CategoryProductEntity cp : cps){
			CategoryProduct cate = new CategoryProduct();
			cate.setId(cp.getId());
			cate.setName(cp.getName());
			cate.setIdCate(cp.getIdCategory());
			for(CategoryEntity c : cs){
				if(c.getId() == cp.getIdCategory())
					cate.setCate(c.getName());
			}
			list.add(cate);
		}
	}
}
