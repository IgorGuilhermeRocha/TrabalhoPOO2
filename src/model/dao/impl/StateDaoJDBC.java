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
import model.dao.StateDao;
import model.entities.Employee;
import model.entities.State;

public class StateDaoJDBC implements StateDao {
	
	private Connection com;
	
	
	public StateDaoJDBC(Connection com) {
		this.com  = com;
	}

	@Override
	public void insert(State e) {

		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.State (initials) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, e.getInitials());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					e.setIdState(id);
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
	public void update(State e) {
		PreparedStatement st = null;

		try {
			st = com.prepareStatement("UPDATE igor_guilherme_rocha.State set initials = ? where id_state = ?");

			st.setString(1, e.getInitials());
			st.setInt(2, e.getIdState());
			
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
			st = com.prepareStatement("DELETE FROM igor_guilherme_rocha.State WHERE id_state = ?");
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
	public State find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.State where id_state = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				State s =  new State();
				s.setIdState(rs.getInt("id_state"));
				s.setInitials(rs.getString("initials"));
				return s;
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
	public State findN(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.State where initials = ?");
			st.setString(1, nome);
			
			rs = st.executeQuery();
			
			if (rs.next()) {
				State s =  new State();
				s.setIdState(rs.getInt("id_state"));
				s.setInitials(rs.getString("initials"));
				return s;
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
	public List<State> findAll() {
		List<State> lista = new ArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.State");

			rs = st.executeQuery();

			boolean teste = false;
			while (rs.next()) {
				State e = new State();
				e.setIdState(rs.getInt("id_state"));
				e.setInitials(rs.getString("initials"));
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
