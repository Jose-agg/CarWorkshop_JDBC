package uo.ri.business.impl.admin.bonos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.BonosGateway;
import uo.ri.persistence.ClientesGateway;
import uo.ri.persistence.FacturasGateway;

public class GenerarBonosCantidadFacturas {

	private Connection connection;
	private BonosGateway bonosGateway;
	private ClientesGateway clientesGateway;
	private FacturasGateway facturasGateway;

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		connection.setAutoCommit(false);
		bonosGateway = PersistenceFactory.getBonosGateway();
		bonosGateway.setConnection(connection);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
		facturasGateway = PersistenceFactory.getFacturasGateway();
		facturasGateway.setConnection(connection);
	}

	public void execute() throws BusinessException {
		try {
			prepareDB();
			generarBonosCantidadFacturas();
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
	 * Metodo que recorre todos los clientes revisando todas sus facturas
	 * 
	 * Por cada factura con un importe superior a los 500€, que no haya sido
	 * utilizada, se crea un bono para ese cliente.
	 */
	private void generarBonosCantidadFacturas() throws BusinessException {
		List<Long> listaClientes, facturas;

		listaClientes = clientesGateway.getListaClientes();
		for (Long cliente : listaClientes) {
			facturas = facturasGateway
					.getFacturasPorClienteImporte500NoUsadas(cliente);
			comprobarCreacionBono(cliente, facturas);
		}
	}

	/**
	 * Metodo que comprueba si el cliente cumple los requisitos para crear bonos
	 * por precio de factura. Si los cumple crea el bono
	 * 
	 * @param cliente Identificador del cliente a buscar
	 * @param listaFacturasNoUsadas Lista de facturas con importe mayor que 500
	 * 			que se comprobarán si cumplen los requisitos
	 */
	private void comprobarCreacionBono(Long cliente, List<Long> facturas) {
		for (Long factura : facturas) {
			facturasGateway.actualizarFacturaUsadaBono(factura);
			int codigo = bonosGateway.getUltimoCodigo();
			String code = "B" + (codigo + 10);
			bonosGateway.insertarBonosMedioPago(code, 30, cliente,
					"Por factura superior a 500€");
		}
	}
}
