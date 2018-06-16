package uo.ri.business.impl.admin.mecanicos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.MecanicosGateway;

public class FindAllMechanics {

	private Connection connection;
	private MecanicosGateway mecanicosGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		mecanicosGateway = PersistenceFactory.getMecanicosGateway();
		mecanicosGateway.setConnection(connection);
	}

	public List<Map<String, Object>> execute() throws BusinessException {
		List<Map<String, Object>> list = null;
		try {
			prepareDB();
			list = mecanicosGateway.findAllMechanics();
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
		return list;
	}
}
