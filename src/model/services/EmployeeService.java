package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.EmployeeDAO;
import model.entities.Employee;

public class EmployeeService {
	
	private EmployeeDAO dao =  DaoFactory.createEmployee();
	
	public boolean login(String email, String password) {
		Employee e = dao.find(email, password);
		 return e == null;
	}
	
	public List<Employee> findAll(){
		return dao.findAll();
	}
	
	public void insere(Employee e) {
		if(e.getId() == null) {
			dao.insert(e);
		}else {
			dao.update(e);
		}

	}
	public void delete(Integer id) {
		dao.delete(id);
	}
	
	
	
	

}
