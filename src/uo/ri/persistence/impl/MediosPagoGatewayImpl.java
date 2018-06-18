package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.MediosPagoGateway;

public class MediosPagoGatewayImpl implements MediosPagoGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void addMetalicoById(Long idCliente) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_ADD_CASH_CLIENT"));
			pst.setLong(1, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void deleteMetalicoById(Long idCliente) {
		PreparedStatement pst = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_DELETE_CASH_CLIENT"));
			pst.setLong(1, idCliente);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public List<Map<String, Object>> listMediosPagoByIiCliente(Long idCliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapa = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_FIND_MEDIOS_PAGO_CLIENTE"));
			pst.setLong(1, idCliente);
			rs = pst.executeQuery();

			while (rs.next()) {
				mapa = new HashMap<String, Object>();
				mapa.put("dtype", rs.getString("dtype"));
				mapa.put("id", rs.getLong("id"));
				mapa.put("acumulado", rs.getDouble("acumulado"));
				mapa.put("codigo", rs.getString("codigo"));
				mapa.put("disponible", rs.getDouble("disponible"));
				mapa.put("numero", rs.getLong("numero"));
				mapa.put("tipo", rs.getString("tipo"));
				mapa.put("validez", rs.getTimestamp("validez"));
				mapa.put("cliente_id", rs.getLong("cliente_id"));
				mapa.put("descripcion", rs.getString("descripcion"));
				lista.add(mapa);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return lista;
	}

	@Override
	public void updateEstadoMedioPago(Long idMedioPago, double nuevoAcumulado,
			double disponible) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_UPDATE_GASTO_MEDIOPAGO_BONO"));
			pst.setDouble(1, nuevoAcumulado);
			pst.setDouble(2, disponible);
			pst.setLong(3, idMedioPago);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}

	@Override
	public void updateEstadoMedioPago(Long idMedioPago, double nuevoAcumulado) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					Conf.get("SQL_UPDATE_GASTO_MEDIOPAGO_OTROS"));
			pst.setDouble(1, nuevoAcumulado);
			pst.setLong(2, idMedioPago);
			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(pst);
		}
	}
}
