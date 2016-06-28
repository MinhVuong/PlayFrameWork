package models;

import java.util.ArrayList;
import java.util.List;

import entities.ProductInforEntity;

public class InforConpareShow {
	private String name;
	private String value1;
	private String value2;
	public InforConpareShow() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InforConpareShow(String name, String value1, String value2) {
		super();
		this.name = name;
		this.value1 = value1;
		this.value2 = value2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	
}
