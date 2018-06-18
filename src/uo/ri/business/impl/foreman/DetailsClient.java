package uo.ri.business.impl.foreman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;

public class DetailsClient {

	private String StringIdCliente;
	private Long idCliente;

	private Connection connection;
	private ClientesGateway clientesGateway;

	public DetailsClient(String idCliente) {
		this.StringIdCliente = idCliente;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
	}

	public Map<String, Object> execute() throws BusinessException {
		Map<String, Object> datosCliente = null;
		try {
			prepareDB();
			comprobarIdCliente(StringIdCliente);
			datosCliente = clientesGateway.getDetallesCliente(idCliente);
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
		return datosCliente;
	}

	/**
	 * Metodo que comprueba que el id cumple una serie de caracteristicas:
	 * 	- Se puede parsear a Long.
	 * 	- Si el id existe en la base de datos.
	 * 
	 * @param id Identificador del cliente
	 * @return true si el id cumple los requisitos
	 * @throws BusinessException
	 */
	private boolean comprobarIdCliente(String id) throws BusinessException {
		try {
			idCliente = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new BusinessException("No es un identificador valido");
		}
		if (clientesGateway.getDetallesCliente(idCliente) == null) {
			throw new BusinessException(
					"No existe un cliente con este identificador");
		}
		return true;
	}

}
