package model.entities;

public class Purcharses {
	
	private Integer id;
	private Client client;
	private Product product;
	private Float totalPrice;
	
	
	public Purcharses(Client client, Product product, Float totalPrice, Integer id) {
		this.client = client;
		this.product = product;
		this.totalPrice = totalPrice;
		this.id = id;
	}

	public Purcharses() {
		
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
