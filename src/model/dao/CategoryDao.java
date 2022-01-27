package model.dao;

import java.util.List;

import model.entities.Category;

public interface CategoryDao {

	public void insert(Category e);
	public void update(Category e);
	public void delete(Integer id);
	public Category find(Integer id);
	public List<Category> findAll();

}
