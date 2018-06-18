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
import uo.ri.persistence.MecanicosGateway;

/**
 * Clase que se encarga de gestionar la persistencia de 
 * la entidad Mecanicos
 * 
 * @author José Antonio García García
 *
 */
public class MecanicosGatewayImpl implements MecanicosGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void addMechanic(String nombre, String apellidos) {
		// Procesar
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_INSERT_MECHANIC"));
			pst.setString(1, nombre);
			pst.setString(2, apellidos);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException();
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void deleteMechanic(Long idMecanico) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_DELETE_MECHANIC"));
			pst.setLong(1, idMecanico);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void updateMechanic(Long idMecanico, String nombre,
			String apellidos) {
		// Procesar
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(Conf.get("SQL_UPDATE_MECHANIC"));
			pst.setString(1, nombre);
			pst.setString(2, apellidos);
			pst.setLong(3, idMecanico);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<Map<String, Object>> findAllMechanics() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_FIND_ALL_MECHANIC"));

			rs = pst.executeQuery();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				map.put("idMecanico", rs.getLong(1));
				map.put("nombre", rs.getString(2));
				map.put("apellidos", rs.getString(3));
				list.add(map);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return list;
	}

}
