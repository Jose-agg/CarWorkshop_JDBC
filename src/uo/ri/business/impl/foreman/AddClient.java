package uo.ri.business.impl.foreman;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;
import uo.ri.persistence.MediosPagoGateway;

public class AddClient {

	private String dni;
	private String nombre;
	private String apellidos;
	private String zipcode;
	private String telefono;
	private String email;
	private String StringIdRecomendador;
	private Long idRecomendador;

	private Connection connection;
	private ClientesGateway clientesGateway;
	private MediosPagoGateway mediosPagoGateway;

	public AddClient(String dni, String nombre, String apellidos,
			String zipcode, String telefono, String email,
			String idRecomendador) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.zipcode = zipcode;
		this.telefono = telefono;
		this.email = email;
		this.StringIdRecomendador = idRecomendador;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
		mediosPagoGateway = PersistenceFactory.getMediosPagoGateway();
		mediosPagoGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			int fueRecomendado = comprobarRecomendador(StringIdRecomendador);

			// El cliente viene recomendado pero el id del recomendador no 
			// cumple los requisitos
			if (fueRecomendado == -1) {
				throw new BusinessException(
						"El recomendador no cumple los requisitos");
			}

			// El cliente no viene recomendado
			if (fueRecomendado == 0) {
				clientesGateway.addCliente(dni, nombre, apellidos, zipcode,
						telefono, email);
				Long idCliente = clientesGateway.getClienteID(dni);
				mediosPagoGateway.addMetalicoById(idCliente);
			}

			// El cliente viene recomendado y el id del recomendador cumple los
			// requisitos
			if (fueRecomendado == 1) {
				clientesGateway.addCliente(dni, nombre, apellidos, zipcode,
						telefono, email, idRecomendador);
				Long idCliente = clientesGateway.getClienteID(dni);
				mediosPagoGateway.addMetalicoById(idCliente);
			}

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
	 * 	- Si es 0 significa que no viene recomendado por nadie. Esta es la 
	 * 		segunda comprobacion porque, de no ser así, en el siguiente paso se 
	 * 		trataria como un id inexistente.
	 * 	- Si el id existe en la base de datos.
	 *  - Si el recomendador tiene al menos una factura pagada.
	 * 
	 * @param id Identificador de un cliente
	 * @return -1 si el recomendador no es valido, 0 si el cliente no viene 
	 * recomendado y 1 si el cliente viene recomendado y es un recomendador 
	 * valido
	 * 
	 * @throws BusinessException
	 */
	private int comprobarRecomendador(String id) throws BusinessException {
		try {
			idRecomendador = Long.parseLong(id);
		} catch (NumberFormatException e) {
			throw new BusinessException("No es un identificador valido");
		}
		if (idRecomendador == 0) {
			return 0;
		}
		if (clientesGateway.getDetallesCliente(idRecomendador) == null) {
			throw new BusinessException(
					"No existe un cliente con este identificador");
		}
		if (clientesGateway
				.comprobarClienteConFacturasAbonadas(idRecomendador)) {
			return 1;
		}
		return -1;
	}
}
