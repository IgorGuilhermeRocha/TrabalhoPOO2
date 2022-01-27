package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.PurcharsesDao;
import model.entities.Purcharses;

public class PurcharsesDaoJDBC implements PurcharsesDao{
	
	private Connection com;
	
	public PurcharsesDaoJDBC (Connection com) {
		this.com = com;
	}

	@Override
	public void insert(Purcharses e) {
		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.Purcharses (idproduct, idclient, totalprice)values"+
				"(?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, e.getProduct().getIdProduto());
			st.setInt(2, e.getClient().getId_client());
			st.setFloat(3, e.getTotalPrice());

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
	public void update(Purcharses e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Purcharses find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Purcharses> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
