package model.dao;

import db.DB;
import model.dao.impl.CategoryDaoJDBC;
import model.dao.impl.ClientDaoJDBC;
import model.dao.impl.EmployeeDaoJDBC;
import model.dao.impl.ProductDaoJDBC;
import model.dao.impl.PurcharsesDaoJDBC;
import model.dao.impl.StateDaoJDBC;

public class DaoFactory {

	public static EmployeeDAO createEmployee() {
		return new EmployeeDaoJDBC(DB.getConnection());
	}
	
	public static ClientDao createClient() {
		return new ClientDaoJDBC(DB.getConnection());
	}
	
	public static StateDao createState() {
		return new StateDaoJDBC(DB.getConnection());
	}
	public static PurcharsesDao createPucharses() {
		return new PurcharsesDaoJDBC(DB.getConnection());
	}
	public static CategoryDao createCategory() {
		return new CategoryDaoJDBC(DB.getConnection());
	}
	
	public static ProductDao createProduct() {
		return new ProductDaoJDBC(DB.getConnection());
	}
}
