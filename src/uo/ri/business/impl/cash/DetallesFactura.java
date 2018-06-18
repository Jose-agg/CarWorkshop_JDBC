package uo.ri.business.impl.cash;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.FacturasGateway;

public class DetallesFactura {

	private Long numFactura;

	private Connection connection;
	private FacturasGateway facturasGateway;

	public DetallesFactura(String numFactura) {
		// No es necesario comprobarlo aquí porque ya se comprueba previamente
		this.numFactura = Long.parseLong(numFactura);
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		this.connection.setAutoCommit(false);
		facturasGateway = PersistenceFactory.getFacturasGateway();
		facturasGateway.setConnection(connection);
	}

	public Map<String, Object> execute() throws BusinessException {
		Map<String, Object> detallesFactura;
		try {
			prepareDB();
			detallesFactura = facturasGateway.getDetallesFactura(numFactura);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e2) {
			}

			throw new RuntimeException();
		} finally {
			Jdbc.close(connection);
		}

		return detallesFactura;
	}
}
