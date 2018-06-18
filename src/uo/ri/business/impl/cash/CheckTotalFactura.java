package uo.ri.business.impl.cash;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import alb.util.jdbc.Jdbc;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.FacturasGateway;
import uo.ri.persistence.MediosPagoGateway;

public class CheckTotalFactura {

	private List<Map<String, Object>> mediosPago;
	private Map<String, Object> factura;
	private Map<String, String> formatoPagosSrings;
	private Map<Long, Double> formatoPagos;

	private Connection connection;
	private FacturasGateway facturasGateway;
	private MediosPagoGateway mediosPagoGateway;

	public CheckTotalFactura(Map<String, Object> factura,
			Map<String, String> formatoPagos,
			List<Map<String, Object>> mediosPago) {
		this.factura = factura;
		this.formatoPagosSrings = formatoPagos;
		this.mediosPago = mediosPago;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		this.connection.setAutoCommit(false);
		facturasGateway = PersistenceFactory.getFacturasGateway();
		facturasGateway.setConnection(connection);
		mediosPagoGateway = PersistenceFactory.getMediosPagoGateway();
		mediosPagoGateway.setConnection(connection);
	}

	public Double execute() throws BusinessException {
		double faltaPorPagar = 0D;
		try {
			prepareDB();
			faltaPorPagar = formatearPagosSeleccionados();
			if (faltaPorPagar <= 0) {
				liquidarFactura();
				faltaPorPagar = 0;
			}

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
		return faltaPorPagar;
	}

	/**
	 * Metodo que formatea los campos de los pagos seleccionados y comprueba que
	 *  los valores de los campos sean validos
	 * @return la cantidad que falta por pagar
	 * @throws BusinessException 
	 */
	private double formatearPagosSeleccionados() throws BusinessException {
		formatoPagos = new HashMap<Long, Double>();
		try {
			Long medioSeleccionado;
			Double cantidadSeleccionada;
			for (Entry<String, String> entrada : formatoPagosSrings
					.entrySet()) {
				medioSeleccionado = Long.parseLong(entrada.getKey());
				if (medioSeleccionado <= 0
						|| medioSeleccionado > mediosPago.size()) {
					throw new BusinessException(
							"Introduzca un medio de pago de la lista");
				}
				cantidadSeleccionada = Double.parseDouble(entrada.getValue());
				formatoPagos.put(medioSeleccionado, cantidadSeleccionada);
			}
		} catch (NumberFormatException e) {
			throw new BusinessException("El medio de pago o la cantidad"
					+ " seleccionada no son valores validos");
		}

		long numPago;
		double cantidad;
		double total = (double) factura.get("importe");

		for (Entry<Long, Double> entrada : formatoPagos.entrySet()) {
			numPago = entrada.getKey();
			cantidad = entrada.getValue();
			Map<String, Object> datosPago = mediosPago.get((int) numPago - 1);

			if (cantidad <= 0) {
				throw new BusinessException(
						"La cantidad tiene que ser mayor que 0€");
			}

			if (datosPago.get("dtype").equals("TBonos")
					&& cantidad > (double) datosPago.get("disponible")) {
				throw new BusinessException(
						"La cantidad introducida para este bono supera la"
								+ " disponible");

			}
			total -= cantidad;
		}

		return total;

	}

	/**
	 * Metodo que liquida la factura, es decir, cambia su estado a ABONADA y
	 * modifica los valores de medios de pago que hayan sido empleados en el 
	 * pago  
	 */
	private void liquidarFactura() {
		long numPago;
		double cantidad, nuevoAcumulado;
		for (Entry<Long, Double> entrada : formatoPagos.entrySet()) {
			numPago = entrada.getKey();
			cantidad = entrada.getValue();
			Map<String, Object> datosPago = mediosPago.get((int) numPago - 1);

			nuevoAcumulado = (Double) datosPago.get("acumulado");
			nuevoAcumulado += cantidad;

			if (datosPago.get("dtype").equals("TBonos")) {
				double disponible = (Double) datosPago.get("disponible");
				disponible -= cantidad;
				mediosPagoGateway.updateEstadoMedioPago(
						(Long) datosPago.get("id"), nuevoAcumulado, disponible);
			} else {
				mediosPagoGateway.updateEstadoMedioPago(
						(Long) datosPago.get("id"), nuevoAcumulado);
			}
		}

		facturasGateway.updateFacturaAbonada((Long) factura.get("id"));
	}

}
