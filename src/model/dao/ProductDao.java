package model.dao;

import java.util.List;

import model.entities.Product;

public interface ProductDao {

	public void insert(Product e);
	public void update(Product e);
	public void delete(Integer id);
	public Product find(Integer id);
	public List<Product> findAll();
	
}
