package pakageResultAdmin;

import java.util.List;

import pakageResult.AbstractPakage;
import entities.CustomerEntity;
import entities.OrderEntity;

public class OrderAdminPakage extends AbstractPakage{
	private List<OrderEntity> orders;
	private List<CustomerEntity> customers;
	public OrderAdminPakage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderAdminPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	public OrderAdminPakage(List<OrderEntity> orders,
			List<CustomerEntity> customers) {
		super();
		this.orders = orders;
		this.customers = customers;
	}
	public List<OrderEntity> getOrders() {
		return orders;
	}
	public void setOrders(List<OrderEntity> orders) {
		this.orders = orders;
	}
	public List<CustomerEntity> getCustomers() {
		return customers;
	}
	public void setCustomers(List<CustomerEntity> customers) {
		this.customers = customers;
	}
	
}
