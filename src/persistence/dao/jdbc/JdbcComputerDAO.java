package persistence.dao.jdbc;

import java.sql.*;
import java.util.LinkedList;

import model.Company;
import model.Computer;
import persistence.dao.ComputerDAO;

public class JdbcComputerDAO implements ComputerDAO {

	private Connection conn;
	private static JdbcComputerDAO instance;
	
	private static final String LIST_ALL_REQUEST = "SELECT * FROM computer JOIN company ON computer.company_id = company.id";
	private static final String FIND_BY_ID = "SELECT * FROM computer JOIN company ON computer.company_id = company.id WHERE computer.id = %d";
	private JdbcComputerDAO(Connection conn) {
		this.conn = conn;
	}
	
	public static JdbcComputerDAO getInstance(Connection conn) {
		if(instance == null) {
			instance = new JdbcComputerDAO(conn);
		}
		return instance;
	}

	@Override
	public Computer get(int id) {
		// TODO Auto-generated method stub
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(String.format(FIND_BY_ID,id));
			result.next();			
			Computer computer = queryResultToObject(result);
			result.close();
			state.close();
			return computer;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer update(Computer c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Computer> list() {
		// TODO Auto-generated method stub
		LinkedList<Computer> computers = new LinkedList<Computer>();
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(LIST_ALL_REQUEST);
			while(result.next()) {
				Computer c = this.queryResultToObject(result);
				computers.add(c);
			}
			result.close();
			state.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computers;
	}
	
	/**
	 * Format a query result to a Computer object
	 * @param result ResultSet
	 * @return Computer
	 * @throws SQLException
	 */
	private Computer queryResultToObject(ResultSet result) throws SQLException {
		int computerId = result.getInt(1);
		String computerName = result.getString(2);
		java.util.Date introductionDate = result.getDate(3) != null ? new java.util.Date(result.getDate(3).getTime()) : null;
		java.util.Date discontinuationDate = result.getDate(4) != null ? new java.util.Date(result.getDate(4).getTime()) : null;
		int companyId = result.getInt(5);
		String companyName = result.getString(7);
		Company company = new Company(companyId, companyName);
		return new Computer(computerId,computerName, introductionDate,discontinuationDate, company);
	}

}
