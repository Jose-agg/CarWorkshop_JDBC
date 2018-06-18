package uo.ri.business.impl.foreman;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;
import uo.ri.persistence.MediosPagoGateway;
import uo.ri.persistence.VehiculosGateway;

public class DeleteClient {

	private String StringIdCliente;
	private Long idCliente;

	private Connection connection;
	private ClientesGateway clientesGateway;
	private VehiculosGateway vehiculosGateway;
	private MediosPagoGateway mediosPagoGateway;

	public DeleteClient(String idCliente) {
		this.StringIdCliente = idCliente;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
		vehiculosGateway = PersistenceFactory.getVehiculosGateway();
		vehiculosGateway.setConnection(connection);
		mediosPagoGateway = PersistenceFactory.getMediosPagoGateway();
		mediosPagoGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			comprobarIdCliente(StringIdCliente);
			comprobarVehiculosCliente();
			mediosPagoGateway.deleteMetalicoById(idCliente);
			clientesGateway.deleteClient(idCliente);
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

	/**
	 * Metodo que comprueba si el cliente tiene vehiculos registrados
	 * 
	 * @throws BusinessException
	 */
	private void comprobarVehiculosCliente() throws BusinessException {
		if (vehiculosGateway.getVehiculosPorCliente(idCliente).size() > 0) {
			throw new BusinessException(
					"No se puede borrar este cliente porque tiene vehiculo/s "
							+ "registrados");
		}
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
