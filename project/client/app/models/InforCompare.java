package models;

import java.util.ArrayList;
import java.util.List;

import entities.ProductInforEntity;

public class InforCompare {
	private List<ProductInforEntity> infor;

	public InforCompare() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InforCompare(List<ProductInforEntity> infor) {
		super();
		this.infor = infor;
	}
	public InforCompare(InforCompare a) {
		super();
		this.infor = a.getInfor();
		// TODO Auto-generated constructor stub
	}

	public List<ProductInforEntity> getInfor() {
		return infor;
	}

	public void setInfor(List<ProductInforEntity> infor) {
		this.infor = infor;
	}
	
	public List<InforConpareShow> convertToListFromInforList(List<ProductInforEntity> list){
		List<InforConpareShow> result = new ArrayList<InforConpareShow>();
		for(ProductInforEntity p : list){
			InforConpareShow in = new InforConpareShow(p.getName(), p.getValue(), "");
			result.add(in);
		}
		return result;
	}
	
	public List<InforConpareShow> convertToListFrom2InforList(List<ProductInforEntity> list, List<ProductInforEntity> list2){
		List<InforConpareShow> result = new ArrayList<InforConpareShow>();
		for(int i = 0; i<list.size(); i++){
			InforConpareShow in = new InforConpareShow(list.get(i).getName(), list.get(i).getValue(), list2.get(i).getValue());
			result.add(in);
		}
		return result;
	}
}
