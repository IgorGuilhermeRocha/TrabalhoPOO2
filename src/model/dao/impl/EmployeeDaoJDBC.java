package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.EmployeeDAO;
import model.entities.Employee;



public class EmployeeDaoJDBC implements EmployeeDAO {
	
	private Connection com;

	public EmployeeDaoJDBC(Connection com) {
		this.com = com;
	}

	@Override
	public void insert(Employee e) {

		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.EMPLOYEE (EMAIL, PASSWORD) VALUES" + "( ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, e.getEmail());
			st.setString(2, e.getPassword());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					e.setId(id);
				}
				DB.closeResultSet(rs);

			} else {
				throw new DbException("Unexpected error! No rows affected");
			}
		} catch (SQLException f) {
			throw new DbException(f.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Employee e) {
		
		PreparedStatement st = null;

		try {
			st = com.prepareStatement("UPDATE igor_guilherme_rocha.EMPLOYEE set email = ?, password = ? where id_employee = ?");

			st.setString(1, e.getEmail());
			st.setString(2, e.getPassword());
			st.setInt(3, e.getId());
			
			st.executeUpdate();

		} catch (SQLException f) {
			throw new DbException(f.getMessage());

		} finally {
			DB.closeStatement(st);

		}

		
	}

	@Override
	public void delete(Integer id) {
		PreparedStatement st =  null;
		try {
			st = com.prepareStatement("DELETE FROM igor_guilherme_rocha.EMPLOYEE WHERE ID_EMPLOYEE = ?");
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	
		
	}

	@Override
	public Employee find(String email, String password) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.EMPLOYEE where email = ? and password = ?");
			st.setString(1, email);
			st.setString(2, password);
			
			rs = st.executeQuery();
			if (rs.next()) {
				Employee e =  new Employee();
				e.setId(rs.getInt("id_employee"));
				e.setEmail(rs.getString("email"));
				e.setPassword(rs.getString("password"));
				return e;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}
		
	

	@Override
	public List<Employee> findAll() {
		List<Employee> lista = new ArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.EMPLOYEE");

			rs = st.executeQuery();

			boolean teste = false;
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("id_employee"));
				e.setEmail(rs.getString("email"));
				e.setPassword(rs.getString("password"));
				lista.add(e);
				teste = true;
			}
			if (teste == false) {
				return null;
			} else {
				return lista;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	
	
	

}
