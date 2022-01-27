package model.dao;

import java.util.List;

import model.entities.Client;

public interface ClientDao {

	public void insert(Client e);
	public void update(Client e);
	public void delete(Integer id);
	public Client find(Integer id);
	public List<Client> findAll();

}
