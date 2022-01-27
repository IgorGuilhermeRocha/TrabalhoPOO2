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
import model.dao.ClientDao;
import model.entities.Client;

public class ClientDaoJDBC implements ClientDao {
	
	private Connection com;

	public ClientDaoJDBC(Connection com) {
		this.com = com;
	}

	@Override
	public void insert(Client e) {

		PreparedStatement st = null;
		try {
			st = com.prepareStatement("INSERT INTO igor_guilherme_rocha.Client (name, last_name, cpf, phone, state, balance) values"+
				"(?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

			st.setString(1, e.getName());
			st.setString(2, e.getLastName());
			st.setString(3, e.getCpf());
			st.setString(4, e.getPhone());
			st.setInt(5, e.getState().getIdState());
			st.setFloat(6, e.getBalance());
			
			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					e.setId_client(id);
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
	public void update(Client e) {
		PreparedStatement st = null;

		try {
			st = com.prepareStatement("UPDATE igor_guilherme_rocha.Client set name = ?, last_name= ?, cpf = ?, phone = ?, state = ? , balance = ? where id_client = ?");

			st.setString(1, e.getName());
			st.setString(2, e.getLastName());
			st.setString(3, e.getCpf());
			st.setString(4, e.getPhone());
			st.setInt(5, e.getState().getIdState());
			st.setFloat(6, e.getBalance());
			st.setInt(7, e.getId_client());
			
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
			st = com.prepareStatement("DELETE FROM igor_guilherme_rocha.Client WHERE ID_CLIENT = ?");
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

	/*@Override
	public Client find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Client where id_client = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				Client e = new Client();
				e.setId_client(rs.getInt("id_client"));
				e.setName(rs.getString("name"));
				e.setLastName(rs.getString("last_name"));
				e.setCpf(rs.getString("cpf"));
				e.setPhone(rs.getString("phone"));
				e.setState(null);
				return e;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

		return null;
	}*/

	@Override
	public List<Client> findAll() {
		List<Client> lista = new ArrayList();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Client");

			rs = st.executeQuery();
			StateDaoJDBC sdj = new StateDaoJDBC(this.com);
			boolean teste = false;
			while (rs.next()) {
				Client e = new Client();
				e.setId_client(rs.getInt("id_client"));
				e.setName(rs.getString("name"));
				e.setLastName(rs.getString("last_name"));
				e.setCpf(rs.getString("cpf"));
				e.setPhone(rs.getString("phone"));
				e.setBalance(rs.getFloat("balance"));
				e.setState(sdj.find(rs.getInt("state")));
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
	public Client find(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = com.prepareStatement("SELECT * FROM igor_guilherme_rocha.Client where id_client = ?");
			st.setInt(1, id);
			
			rs = st.executeQuery();
			if (rs.next()) {
				Client e = new Client();
				e.setId_client(rs.getInt("id_client"));
				e.setName(rs.getString("name"));
				e.setLastName(rs.getString("last_name"));
				e.setCpf(rs.getString("cpf"));
				e.setPhone(rs.getString("phone"));
				e.setBalance(rs.getFloat("balance"));
				StateDaoJDBC s =  new StateDaoJDBC(this.com);
				e.setState(s.find(rs.getInt("state")));
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

}
