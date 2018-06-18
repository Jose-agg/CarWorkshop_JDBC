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
import uo.ri.persistence.BonosGateway;

/**
 * Clase que se encarga de gestionar la persistencia de 
 * la entidad Bonos
 * 
 * @author José Antonio García García
 *
 */
public class BonosGatewayImpl implements BonosGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int getUltimoCodigo() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		String codigo = "";

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_FIND_ULTIMO_CODIGO_BONO"));

			rs = pst.executeQuery();
			while (rs.next()) {
				codigo = rs.getString("codigo").substring(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return Integer.parseInt(codigo);
	}

	@Override
	public void insertarBonosMedioPago(String code, int cantidad, Long cliente,
			String descripcion) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_INSERT_BONO"));
			pst.setString(1, code);
			pst.setDouble(2, cantidad);
			pst.setLong(3, cliente);
			pst.setString(4, descripcion);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<Map<String, Object>> findBonosCliente(Long idCliente) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> bonos = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapa = null;

		try {
			ps = connection.prepareStatement(Conf.get("SQL_BONOS_CLIENT"));
			ps.setLong(1, idCliente);
			rs = ps.executeQuery();

			while (rs.next()) {
				mapa = new HashMap<String, Object>();
				mapa.put("tipo", "Bono"); // Para diferenciar el mapa de la 
											// informacion agregada
				mapa.put("codigo", rs.getString("codigo"));
				mapa.put("descripcion", rs.getString("descripcion"));
				mapa.put("disponible", rs.getDouble("disponible"));
				mapa.put("acumulado", rs.getDouble("acumulado"));
				bonos.add(mapa);
			}
		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(rs, ps);
		}
		return bonos;
	}

}
