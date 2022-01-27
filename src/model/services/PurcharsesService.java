package model.services;

import model.dao.DaoFactory;
import model.dao.PurcharsesDao;
import model.entities.Purcharses;

public class PurcharsesService {
	
	private PurcharsesDao dao =  DaoFactory.createPucharses();
	
	public void insert(Purcharses e){
		dao.insert(e);
		
	}
}

