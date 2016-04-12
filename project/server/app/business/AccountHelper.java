package business;

import models.Register;
import entities.CustomerEntity;


public class AccountHelper {
	
	// Create CustomerEnityt from object Register.
	public CustomerEntity generateCustomerEntityFromRegister(Register register)
	{
		CustomerEntity customer = new CustomerEntity();
		customer.setFirstName(register.getFirstName());
		customer.setLastName(register.getLastName());
		customer.setEmail(register.getEmail());
		customer.setPassword(register.getPassword());
		customer.setGender(register.getGender());
		customer.setGroupId(1);
		customer.setScore(0);
		customer.setNumberLog(0);
		customer.setIsActive(0);
		DateHelper dateHelper = new DateHelper();
		String datetime = dateHelper.getDateTimeCurrent();
		customer.setCreateAt(datetime);
		customer.setUpdateAt(datetime);
		customer.setNumberLog(0);
		return customer;
	}
}
