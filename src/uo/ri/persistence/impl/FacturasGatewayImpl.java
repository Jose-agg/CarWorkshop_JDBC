package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.FacturasGateway;

public class FacturasGatewayImpl implements FacturasGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection con) {
		this.connection = con;
	}

	@Override
	public Long getLastInvoiceNumber() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_ULTIMO_NUMERO_FACTURA"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, el siguiente
			} else { // todavía no hay ninguna
				return 1L;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public long crearFactura(Map<String, Object> mapa) {
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(Conf.get("SQL_INSERTAR_FACTURA"));
			long numeroFactura = (long) mapa.get("numeroFactura");
			pst.setLong(1, numeroFactura);
			pst.setDate(2, new java.sql.Date(((java.util.Date) mapa.get("fechaFactura")).getTime()));
			pst.setDouble(3, (double) mapa.get("iva"));
			pst.setDouble(4, (double) mapa.get("totalConIva"));
			pst.setString(5, "SIN_ABONAR");

			pst.executeUpdate();

			return getGeneratedKey(numeroFactura); // Id de la nueva factura generada

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	private long getGeneratedKey(long numeroFactura) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_RECUPERAR_CLAVE_GENERADA"));
			pst.setLong(1, numeroFactura);
			rs = pst.executeQuery();
			rs.next();

			return rs.getLong(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
