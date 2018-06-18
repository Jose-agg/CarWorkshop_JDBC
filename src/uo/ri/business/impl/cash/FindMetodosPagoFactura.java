package uo.ri.business.impl.cash;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ClientesGateway;
import uo.ri.persistence.FacturasGateway;
import uo.ri.persistence.MediosPagoGateway;

public class FindMetodosPagoFactura {

	private String StringNumFactura;
	private Long numFactura;

	private Connection connection;

	private FacturasGateway facturasGateway;
	private ClientesGateway clientesGateway;
	private MediosPagoGateway mediosPagoGateway;

	public FindMetodosPagoFactura(String numFactura) {
		this.StringNumFactura = numFactura;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		this.connection.setAutoCommit(false);
		facturasGateway = PersistenceFactory.getFacturasGateway();
		facturasGateway.setConnection(connection);
		clientesGateway = PersistenceFactory.getClientesGateway();
		clientesGateway.setConnection(connection);
		mediosPagoGateway = PersistenceFactory.getMediosPagoGateway();
		mediosPagoGateway.setConnection(connection);
	}

	public List<Map<String, Object>> execute() throws BusinessException {
		List<Map<String, Object>> listaMediosPago = null;
		try {
			prepareDB();
			Map<String, Object> detallesFactura = comprobarNumFactura(
					StringNumFactura);
			listaMediosPago = findMediosPago(detallesFactura);
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

		return listaMediosPago;
	}

	/**
	 * Metodo que comprueba que el numero de la factura cumple una serie de 
	 * caracteristicas:
	 * 	- Se puede parsear a Long.
	 * 	- Si el numero existe en la base de datos.
	 * 
	 * @param num Numero de la factura
	 * @return los detalles de la factura
	 * @throws BusinessException
	 */
	private Map<String, Object> comprobarNumFactura(String num)
			throws BusinessException {
		try {
			numFactura = Long.parseLong(num);
		} catch (NumberFormatException e) {
			throw new BusinessException("No es un numero valido");
		}
		Map<String, Object> detallesFactura = facturasGateway
				.getDetallesFactura(numFactura);
		if (detallesFactura == null) {
			throw new BusinessException(
					"No existe una factura con este numero");
		}
		if (detallesFactura.get("status").equals("ABONADA")) {
			throw new BusinessException("Esta factura ya esta abonada");
		}
		return detallesFactura;
	}

	/**
	 * Metodo que busca la lista de medios pago que tiene disponible el dueño 
	 * del vehiculo de un a determinada factura
	 * 
	 * @param detallesFactura Datos de la factura a buscar
	 * @return lista de medios de pago
	 * @throws BusinessException 
	 */
	private List<Map<String, Object>> findMediosPago(
			Map<String, Object> detallesFactura) throws BusinessException {
		Long idCliente = clientesGateway
				.findIdClienteByIdFactura((Long) detallesFactura.get("id"));
		if (idCliente == 0) {
			throw new BusinessException(
					"El cliente no se encuentra en el sistema");
		}
		return mediosPagoGateway.listMediosPagoByIiCliente(idCliente);
	}
}
