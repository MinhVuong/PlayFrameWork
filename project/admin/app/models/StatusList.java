package models;

import java.util.List;

public class StatusList {
	private List<StatusAndId> status;

	public StatusList() {
		super();
	}

	public List<StatusAndId> getStatus() {
		return status;
	}

	public void setStatus(List<StatusAndId> status) {
		this.status = status;
	}
	
}
