package model.dao;

import java.util.List;

import model.entities.Purcharses;

public interface PurcharsesDao {

	
	public void insert(Purcharses e);
	public void update(Purcharses e);
	public void delete(Integer id);
	public Purcharses find(Integer id);
	public List<Purcharses> findAll();
}
