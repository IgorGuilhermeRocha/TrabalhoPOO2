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
import model.dao.CategoryDao;
import model.entities.Category;

public class CategoryDaoJDBC implements CategoryDao {
	

	private Connection com;

	public CategoryDaoJDBC(Connection com) {
		this.com = com;
	}

	@Override
	public void insert(Category e) {

		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.Category (name) values"+
				"(?)",Statement.RETURN_GENERATED_KEYS);

			st.setString(1, e.getName());
			
			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					e.setId_category(id);
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
	public void update(Category e) {
		PreparedStatement st = null;

		try {
			st = com.prepareStatement("UPDATE igor_guilherme_rocha.Category set name = ? where id_category = ?");

			st.setString(1, e.getName());
			st.setInt(2, e.getId_category());
			
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
			st = com.prepareStatement("DELETE FROM igor_guilherme_rocha.Category WHERE ID_CATEGORY = ?");
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
	public List<Category> findAll() {
		List<Category> lista = new ArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Category");
			rs = st.executeQuery();
		
			boolean teste = false;
			while (rs.next()) {
				Category e = new Category();
				e.setId_category(rs.getInt("id_category"));
				e.setName(rs.getString("name"));
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

	@Override
	public Category find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


}
