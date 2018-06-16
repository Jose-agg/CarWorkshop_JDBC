package uo.ri.business.impl.admin.bonos;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BonosGateway;

public class GenerarBonosCantidadFacturas {
	private Connection connection;
	private BonosGateway bonosGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		bonosGateway = PersistenceFactory.getBonosGateway();
		bonosGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			bonosGateway.generarBonosCantidadFacturas();
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
