package persistence.dao.jdbc;

import java.sql.*;
import java.util.ArrayList;

import model.Computer;
import persistence.dao.ComputerDAO;

public class JdbcComputerDAO implements ComputerDAO {

	private Connection conn;
	private static JdbcComputerDAO instance;
	
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
		return null;
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
	public ArrayList<Computer> list() {
		// TODO Auto-generated method stub
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery("SELECT * FROM computer JOIN company");
			while(result.next()) {
				Computer c = new Computer(result.getString(2));
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

}
