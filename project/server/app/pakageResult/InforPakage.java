package pakageResult;

import models.Address;
import models.Infor;

public class InforPakage extends AbstractPakage{
	private Infor infor;
	private Address address;

	

	public InforPakage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InforPakage(int type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public InforPakage(Infor infor, Address address) {
		super();
		this.infor = infor;
		this.address = address;
	}

	public Infor getInfor() {
		return infor;
	}

	public void setInfor(Infor infor) {
		this.infor = infor;
	}
	
	

}
