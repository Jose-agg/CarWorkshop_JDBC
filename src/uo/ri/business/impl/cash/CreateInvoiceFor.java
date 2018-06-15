package uo.ri.business.impl.cash;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.date.DateUtil;
import alb.util.jdbc.Jdbc;
import alb.util.math.Round;
import uo.ri.common.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.AveriasGateway;
import uo.ri.persistence.FacturasGateway;

public class CreateInvoiceFor {

	private List<Long> idsAveria;
	private Connection connection;
	private FacturasGateway facturasGateway;
	private AveriasGateway averiasGateway;

	public CreateInvoiceFor(List<Long> list) {
		this.idsAveria = list;
	}

	private void prepareDB() throws SQLException {
		this.connection = Jdbc.getConnection();
		this.connection.setAutoCommit(false);
		facturasGateway = PersistenceFactory.getFacturasGateway();
		facturasGateway.setConnection(connection);
		averiasGateway = PersistenceFactory.getAveriasGateway();
		averiasGateway.setConnection(connection);
	}

	public Map<String, Object> execute() throws BusinessException {
		Map<String, Object> mapa = null;
		try {
			prepareDB();
			List<Map<String, Object>> averias = verificarAveriasTerminadas();

			mapa = generarFactura(averias);

			long idFactura = facturasGateway.crearFactura(mapa);

			actualizarAverias(averias, idFactura);
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException ex) {
			}

			throw new RuntimeException();
		} finally {
			Jdbc.close(connection);
		}

		return mapa;
	}

	private List<Map<String, Object>> verificarAveriasTerminadas() throws SQLException, BusinessException {
		List<Map<String, Object>> lista = new ArrayList<>();
		for (Long id : idsAveria) {
			Map<String, Object> map = averiasGateway.findByID(id);
			if (map == null) {
				throw new BusinessException("No existe la avería: " + id);
			} else {
				String status = (String) map.get("status");
				if (!"TERMINADA".equalsIgnoreCase(status)) {
					throw new BusinessException("No está terminada la avería:" + id);
				}
				lista.add(map);
			}
		}
		return lista;
	}

	private Map<String, Object> generarFactura(List<Map<String, Object>> averias)
			throws SQLException, BusinessException {
		Map<String, Object> mapa = new HashMap<String, Object>();

		long numeroFactura = generarNuevoNumeroFactura();
		Date fechaFactura = DateUtil.today();
		double totalFactura = calcularImportesAverias(averias);
		double iva = porcentajeIva(totalFactura, fechaFactura);
		double totalConIva = Round.twoCents(totalFactura * (1 + iva / 100));

		mapa.put("numeroFactura", numeroFactura);
		mapa.put("fechaFactura", fechaFactura);
		mapa.put("totalFactura", totalFactura);
		mapa.put("iva", iva);
		mapa.put("totalConIva", totalConIva);

		return mapa;
	}

	private Long generarNuevoNumeroFactura() throws SQLException {
		return facturasGateway.getLastInvoiceNumber();
	}

	private double calcularImportesAverias(List<Map<String, Object>> averias) {
		double totalFactura = 0.0;
		for (Map<String, Object> averia : averias) {
			Long idAveria = (Long) averia.get("id");

			double importeManoObra = averiasGateway.consultaImporteManoObra(idAveria);
			double importeRepuestos = averiasGateway.consultaImporteRepuestos(idAveria);
			double totalAveria = importeManoObra + importeRepuestos;

			averia.replace("importe", totalAveria);

			totalFactura += totalAveria;
		}
		return Round.twoCents(totalFactura);
	}

	private void actualizarAverias(List<Map<String, Object>> averias, long idFactura) throws SQLException {
		for (Map<String, Object> averia : averias) {
			Long idAveria = (Long) averia.get("id");
			averia.replace("factura_id", idFactura);
			averia.replace("status", "FACTURADA");
			averiasGateway.actualizarAveria(idAveria, averia);
		}
	}

	private double porcentajeIva(double totalFactura, Date fechaFactura) {
		return DateUtil.fromString("1/7/2012").before(fechaFactura) ? 21.0 : 18.0;
	}
}
