package model.entities;

public class Category {
	
	private Integer id_category;
	private String name;
	
	public Category(int id_category, String name) {
		this.id_category = id_category;
		this.name = name;
	}
	
	public Category() {
		
	}
	
	public Integer getId_category() {
		return id_category;
	}
	public void setId_category(Integer id_category) {
		this.id_category = id_category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
