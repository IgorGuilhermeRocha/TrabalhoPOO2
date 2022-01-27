package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection com;
	
	public static Connection getConnection() {
		if(com == null) {
			try {
			Properties p = loadProperties();
			String url = p.getProperty("dburl");
			com = DriverManager.getConnection(url,p);
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return com;
	}
	private static Properties loadProperties() {
		try(FileInputStream fs =  new FileInputStream("db.properties")){
			Properties p =  new Properties();
			p.load(fs);
			return p;
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	public static void closeConnetion() {
		if(com != null) {
			try {
			com.close();
			}catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
