package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
