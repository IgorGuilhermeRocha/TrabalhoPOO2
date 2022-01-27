package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.StateDao;
import model.entities.State;

public class StateService {
	
	private StateDao dao = DaoFactory.createState();
	
	public List<State> findAll(){
		return dao.findAll();
	}
	
	public State find(String nome){
		return dao.findN(nome);
	}
	
	public void insere(State s) {
		if(s.getIdState() != null) {
			dao.update(s);
		}else {
			dao.insert(s);
		}
		
	}
	public void delete(Integer id) {
		dao.delete(id);
	}

}
