package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category_product")
public class CategoryProductEntity {
	@Id
	@Column(name = "id_category_product")
	private int id;
	@Column(name = "name_category_product")
	private String name;
	@Column(name = "category_id")
	private int idCategory;
	

	public CategoryProductEntity(int id, String name, int idCategory) {
		super();
		this.id = id;
		this.name = name;
		this.idCategory = idCategory;
	}
	public CategoryProductEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	
	
}
