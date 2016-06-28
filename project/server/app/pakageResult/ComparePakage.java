package pakageResult;
import java.util.List;

import entities.ProductEntity;
import entities.ProductImageEntity;
import entities.ProductInforEntity;
public class ComparePakage extends AbstractPakage{
	private ProductEntity product;
	private List<ProductInforEntity> infor;
	private List<ProductImageEntity> image;
	public ComparePakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComparePakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public ComparePakage(ProductEntity product, List<ProductInforEntity> infor,
			List<ProductImageEntity> image) {
		super();
		this.product = product;
		this.infor = infor;
		this.image = image;
	}
	public ProductEntity getProduct() {
		return product;
	}
	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	public List<ProductInforEntity> getInfor() {
		return infor;
	}
	public void setInfor(List<ProductInforEntity> infor) {
		this.infor = infor;
	}
	public List<ProductImageEntity> getImage() {
		return image;
	}
	public void setImage(List<ProductImageEntity> image) {
		this.image = image;
	}
	
}
