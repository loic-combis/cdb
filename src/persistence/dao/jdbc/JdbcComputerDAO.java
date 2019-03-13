package persistence.dao.jdbc;

import java.sql.*;
import java.util.LinkedList;

import model.Computer;
import persistence.dao.ComputerDAO;
import persistence.mapper.ComputerMapper;

public class JdbcComputerDAO implements ComputerDAO {

	private Connection conn;
	private ComputerMapper mapper = new ComputerMapper();
	
	private static JdbcComputerDAO instance;
	
	private static final String LIST_REQUEST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id %s";
	private static final String FIND_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = %d";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id = %d";
	private static final String CREATE_ONE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES('%s', ?, ?, ?)";
	private static final String UPDATE_ONE = "UPDATE computer SET name='%s', introduced = ?, discontinued = ?, company_id = ? WHERE id = %d";
	
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
	public Computer get(long id) {
		// TODO Auto-generated method stub
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(String.format(FIND_BY_ID,id));		
			Computer computer = result.next() ? mapper.queryResultToObject(result) : null;
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
	public Computer create(Computer c) {
		// TODO Auto-generated method stub
		Long companyId = (c.getCompany() != null ? c.getCompany().getId() : null);
		Timestamp introductionDate = mapper.getSqlTimestampValue(c.getIntroductionDate());
		Timestamp discontinuationDate = mapper.getSqlTimestampValue(c.getDiscontinuationDate());
		
		try {
			PreparedStatement state = conn.prepareStatement(String.format(CREATE_ONE, c.getName()), Statement.RETURN_GENERATED_KEYS);
			state.setTimestamp(1, introductionDate);
			state.setTimestamp(2, discontinuationDate);
			state.setObject(3, companyId, java.sql.Types.INTEGER);
			int affectedRows = state.executeUpdate();
			if(affectedRows == 0) {
				return null;
			}
			ResultSet generatedKeys = state.getGeneratedKeys();
			generatedKeys.next();
			c.setId(generatedKeys.getLong(1));
			
			return c;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Boolean delete(long id) {
		// TODO Auto-generated method stub
		try {
			Statement state = conn.createStatement();
			int result = state.executeUpdate(String.format(DELETE_ONE,id));
			state.close();
			return result == 1;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean update(Computer c) {
		// TODO Auto-generated method stub
		Long companyId = (c.getCompany() != null ? c.getCompany().getId() : null );
		Timestamp introductionDate = mapper.getSqlTimestampValue(c.getIntroductionDate());
		Timestamp discontinuationDate = mapper.getSqlTimestampValue(c.getDiscontinuationDate());
		
		try {
			PreparedStatement state = conn.prepareStatement(String.format(UPDATE_ONE, c.getName(), c.getId()));
			state.setTimestamp(1, introductionDate);
			state.setTimestamp(2, discontinuationDate);
			state.setObject(3, companyId, java.sql.Types.INTEGER);
			int affectedRows = state.executeUpdate();
			return affectedRows == 1;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Computer> list(int page) {
		// TODO Auto-generated method stub
		LinkedList<Computer> computers = new LinkedList<Computer>();
		try {
			Statement state = conn.createStatement();
			int itemPerPage = 5;
			String offsetClause = "LIMIT " + itemPerPage;
			offsetClause += (page > 0) ? " OFFSET " + ((page -1) * itemPerPage) : "";
			
			ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
			while(result.next()) {
				Computer c = mapper.queryResultToObject(result);
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
