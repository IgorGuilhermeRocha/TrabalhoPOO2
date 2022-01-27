package model.services;

import java.util.List;

import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.entities.Category;

public class CategoryService {
	
	private CategoryDao dao =  DaoFactory.createCategory();
	
	public List<Category> findAll(){
		return dao.findAll();
	}
	
	public void remove(Integer id) {
		dao.delete(id);
	}
	
	public void insert(Category e) {
		dao.insert(e);
	}

	
}
