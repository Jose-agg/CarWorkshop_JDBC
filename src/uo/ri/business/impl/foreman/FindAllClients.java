package uo.ri.business.impl.foreman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;

public class FindAllClients {

	private Connection connection;
	private ClientesGateway clientesGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
	}

	public List<Map<String, Object>> execute() throws BusinessException {
		try {
			prepareDB();

			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(connection);
		}
	}
}
