package model.entities;

public class Product {
	
	private Integer idProduto;
	private String name;
	private Float price;
	private Integer quantity;
	
	
	public Product() {
		
	}
	
	
	public Product(Integer idProduto, String name, Float price, Integer quantity) {
		this.idProduto = idProduto;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		
	}


	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	
	
	

}
