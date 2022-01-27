package model.services;

import java.util.List;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;

public class ClientService {
	
	private ClientDao dao =  DaoFactory.createClient();
	

	public void insere(Client e) {
		if(e.getId_client() == null) {
			dao.insert(e);
		}else {
			dao.update(e);
		}

	}
	public Client find(Integer id) {
		
		return dao.find(id);
	}
	
	public List<Client> findAll(){
		return dao.findAll();
	}
	
	public void remove(Integer id) {
		dao.delete(id);
	}
	
}
