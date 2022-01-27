package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;

public class ProductService {
	
	private ProductDao dao =  DaoFactory.createProduct();
	
	public List<Product> findAll() {
		return dao.findAll();
	}
	public void remove(Integer id) {
		dao.delete(id);
	}
	
	public Product find(Integer id) {
		return dao.find(id);
	}
	
	public void insere(Product e) {
		if(e.getIdProduto() != null) {
			dao.update(e);
		}else {
			dao.insert(e);
		}
	}

}
