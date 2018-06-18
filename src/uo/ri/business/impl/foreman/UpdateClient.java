package uo.ri.business.impl.foreman;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;

public class UpdateClient {

	private String nombre;
	private String apellidos;
	private String zipcode;
	private String telefono;
	private String email;
	private String StringIdCliente;
	private Long idCliente;

	private Connection connection;
	private ClientesGateway clientesGateway;

	public UpdateClient(String idCliente, String nombre, String apellidos,
			String zipcode, String telefono, String email) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.zipcode = zipcode;
		this.telefono = telefono;
		this.email = email;
		this.StringIdCliente = idCliente;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			comprobarIdCliente(StringIdCliente);
			clientesGateway.updateClient(idCliente, nombre, apellidos, zipcode,
					telefono, email);
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
