package uo.ri.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.conf.Conf;
import uo.ri.persistence.VehiculosGateway;

/**
 * Clase que se encarga de gestionar la persistencia de 
 * la entidad Vehiculos
 * 
 * @author José Antonio García García
 *
 */
public class VehiculosGatewayImpl implements VehiculosGateway {

	private Connection connection;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Long> getVehiculosPorCliente(Long cliente) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Long> list = new ArrayList<Long>();

		try {
			pst = connection
					.prepareStatement(Conf.get("SQL_FIND_VEHICLES_BY_CLIENT"));
			pst.setLong(1, cliente);

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
}
