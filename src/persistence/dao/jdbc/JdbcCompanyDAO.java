package persistence.dao.jdbc;

import java.sql.Connection;
import java.util.ArrayList;

import model.Company;
import persistence.dao.CompanyDAO;

public class JdbcCompanyDAO implements CompanyDAO {

	
	private static JdbcCompanyDAO instance;
	private Connection conn;
	
	private JdbcCompanyDAO(Connection conn) {
		this.conn = conn;
	}
	
	public static JdbcCompanyDAO getInstance(Connection conn) {
		if(instance == null) {
			instance = new JdbcCompanyDAO(conn);
		}
		return instance;
	}
	@Override
	public Company get(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Company> list() {
		// TODO Auto-generated method stub
		return null;
	}

}