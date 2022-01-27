package model.entities;

import java.io.Serializable;

public class State implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idState;
	private String initials;
	
	
	public State() {
		
	}
	
	public State(Integer idState, String initials) {
		
		this.idState = idState;
		this.initials = initials;
	}
	
	public Integer getIdState() {
		return idState;
	}
	public void setIdState(Integer idState) {
		this.idState = idState;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}

	@Override
	public String toString() {
		return this.initials;
	}
	
	
	

}
