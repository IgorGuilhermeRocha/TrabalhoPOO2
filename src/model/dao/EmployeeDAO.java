package model.dao;

import java.util.List;

import model.entities.Employee;

public interface  EmployeeDAO {
	
	public void insert(Employee e);
	public void update(Employee e);
	public void delete(Integer id);
	public Employee find(String email,String password);
	public List<Employee> findAll();

}
