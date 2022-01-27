package model.dao;

import java.util.List;

import model.entities.State;

public interface StateDao {
	
	public void insert(State e);
	public void update(State e);
	public void delete(Integer id);
	public State find(Integer id);
	public State findN(String nome);
	public List<State> findAll();

}
