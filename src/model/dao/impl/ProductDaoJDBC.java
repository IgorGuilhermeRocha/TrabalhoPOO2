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
import model.dao.ProductDao;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao{
	
	private Connection com;
	
	public ProductDaoJDBC(Connection com) {
		this.com = com;
	}

	@Override
	public void insert(Product e) {

		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.Product (name, price, quantity)values"+
				"(?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

			st.setString(1, e.getName());
			st.setFloat(2, e.getPrice());
			st.setInt(3,e.getQuantity());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					e.setIdProduto(id);
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
	public void update(Product e) {
		PreparedStatement st = null;

		try {
			st = com.prepareStatement("UPDATE igor_guilherme_rocha.Product set name = ?, price = ?, quantity = ? where id_product = ?");

			st.setString(1, e.getName());
			st.setFloat(2, e.getPrice());
			st.setInt(3, e.getQuantity());
			st.setInt(4, e.getIdProduto());
			
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
			st = com.prepareStatement("DELETE FROM igor_guilherme_rocha.Product WHERE ID_Product = ?");
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
	public Product find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Product where id_product = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				Product e = new Product();
				e.setIdProduto(rs.getInt("id_product"));
				e.setName(rs.getString("name"));
				e.setPrice(rs.getFloat("price"));
				e.setQuantity(rs.getInt("quantity"));
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
	public List<Product> findAll() {
		List<Product> lista = new ArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Product");

			rs = st.executeQuery();

			boolean teste = false;
			while (rs.next()) {
				Product e = new Product();
				e.setIdProduto(rs.getInt("id_product"));
				e.setName(rs.getString("name"));
				e.setPrice(rs.getFloat("price"));
				e.setQuantity(rs.getInt("quantity"));
				
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
