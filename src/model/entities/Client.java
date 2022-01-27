package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idClient;
	private String name;
	private String lastName;
	private String cpf;
	private String phone;
	private Float balance;
	private State state;
	
	
	public Client() {
		
	}
	
	public Client(Integer idClient, String name, String lastName, String cpf, String phone,Float balance,
			State state) {
		this.idClient = idClient;
		this.name = name;
		this.lastName = lastName;
		this.cpf = cpf;
		this.phone = phone;
		this.balance = balance;
		this.state = state;
	}
	

	
	public Integer getId_client() {
		return idClient;
	}

	public void setId_client(Integer idClient) {
		this.idClient = idClient;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
	

}
