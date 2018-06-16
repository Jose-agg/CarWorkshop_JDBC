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
import uo.ri.persistence.AveriasGateway;

/**
 * Clase que se encarga de gestionar la persistencia de 
 * la entidad Averias
 * 
 * @author José Antonio García García
 *
 */
public class AveriasGatewayImpl implements AveriasGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Map<String, Object> findByID(Long idAveria) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(Conf.get("SQL_FIND_AVERIA"));
			ps.setLong(1, idAveria);
			rs = ps.executeQuery();

			if (rs.next()) {
				Map<String, Object> mapa = new HashMap<>();
				mapa.put("id", rs.getLong(1));
				mapa.put("descripcion", rs.getString(2));
				mapa.put("fecha", rs.getDate(3));
				mapa.put("importe", rs.getDouble(4));
				mapa.put("status", rs.getString(5));
				mapa.put("factura_id", rs.getLong(6));
				mapa.put("mecanico_id", rs.getLong(7));
				mapa.put("vehiculo_id", rs.getLong(8));
				return mapa;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, ps);
		}
	}

	@Override
	public double consultaImporteManoObra(Long idAveria) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_IMPORTE_MANO_OBRA"));
			pst.setLong(1, idAveria);

			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			} else {
				return -1;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public double consultaImporteRepuestos(Long idAveria) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_IMPORTE_REPUESTOS"));
			pst.setLong(1, idAveria);

			rs = pst.executeQuery();
			if (!rs.next()) {
				return 0.0; // La averia puede no tener repuestos
			}

			return rs.getDouble(1);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void actualizarAveria(Long idAveria, Map<String, Object> averia) {
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(Conf.get("SQL_UPDATE_AVERIA"));
			ps.setDouble(1, (double) averia.get("importe"));
			ps.setString(2, (String) averia.get("status"));
			ps.setLong(3, (long) averia.get("factura_id"));
			ps.setLong(4, idAveria);

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(ps);
		}
	}

	@Override
	public List<Long> getAveriasNoUsadas(Long vehiculo) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Long> list = new ArrayList<Long>();

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_FIND_AVERIAS_NO_USADAS"));
			pst.setLong(1, vehiculo);

			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(rs.getLong(1));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
		return list;
	}

	@Override
	public void actualizarAveriaUsadaBono(Long idAveria) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_UPDATE_AVERIA_USADA_BONO"));
			pst.setLong(1, idAveria);

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
